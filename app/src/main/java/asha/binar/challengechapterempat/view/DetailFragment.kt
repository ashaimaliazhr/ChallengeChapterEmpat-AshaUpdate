package asha.binar.challengechapterempat.view

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import asha.binar.challengechapterempat.R
import asha.binar.challengechapterempat.databinding.FragmentDetailBinding
import asha.binar.data.Status
import asha.binar.data.StatusDatabase

class DetailFragment : Fragment() {
    lateinit var binding : FragmentDetailBinding
    lateinit var share : SharedPreferences
    lateinit var viewModel: MainViewModel
    var DBNote : StatusDatabase? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        share = requireActivity().getSharedPreferences("ACC", Context.MODE_PRIVATE)!!
        val fullname = share.getString("username","username")
        binding.welcomeBar.text = "Welcome, $fullname!"
        Log.d("Detailscreen", "Username : $fullname")

        binding.btnLogout.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_detailFragment_to_loginFragment)
        }

        //Get Data from Adapter
        DBNote = StatusDatabase.getInstance(requireContext())
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        var getNote = arguments?.getSerializable("datanotes") as Status
        binding.vDetail.setText(getNote.title)
        binding.vContent.setText(getNote.content)
    }
}