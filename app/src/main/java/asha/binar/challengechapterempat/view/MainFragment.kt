package asha.binar.challengechapterempat.view

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import asha.binar.challengechapterempat.R
import asha.binar.challengechapterempat.adapter.AdapterStatus
import asha.binar.challengechapterempat.databinding.FragmentMainBinding
import asha.binar.data.Status
import asha.binar.data.StatusDatabase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private lateinit var viewModel: MainViewModel
    lateinit var share : SharedPreferences
    lateinit var adapter: AdapterStatus
    var DBNote : StatusDatabase? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        share = requireActivity().getSharedPreferences("ACC", Context.MODE_PRIVATE)!!
        val fullname = share.getString("username","username")
        binding.welcomeBar.text = "Welcome, $fullname!"
        Log.d("Homescreen", "Username : $fullname")

        binding.btnLogout.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_mainFragment_to_loginFragment)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        //ViewModel
        adapter = AdapterStatus(ArrayList())
        binding.rvMain.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.rvMain.adapter = adapter
        //LiveData
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.getNoteObservers().observe(viewLifecycleOwner, Observer {
            adapter.setNotes(it as ArrayList<Status>)
        })
        //Room
        DBNote = StatusDatabase.getInstance(requireContext())
        //Add Note
        binding.floatingActionButton.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_mainFragment_to_addNoteFragment)
        }
    }


    override fun onStart() {
        super.onStart()
        GlobalScope.launch {
            var data = DBNote?.statusDao()?.getDataNote()
            activity?.runOnUiThread {
                adapter = AdapterStatus(data!!)
                binding.rvMain.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                binding.rvMain.adapter = adapter
            }
        }
    }

}