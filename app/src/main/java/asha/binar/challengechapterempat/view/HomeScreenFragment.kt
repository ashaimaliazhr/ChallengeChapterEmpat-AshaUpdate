package asha.binar.challengechapterempat.view

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import asha.binar.challengechapterempat.R
import asha.binar.challengechapterempat.adapter.AdapterStatus
import asha.binar.data.Status
import asha.binar.data.StatusDatabase
import kotlinx.android.synthetic.main.edit_data.view.*
import kotlinx.android.synthetic.main.fragment_home_screen.*
import kotlinx.android.synthetic.main.input_data.view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


class HomeScreenFragment : Fragment() {

    private lateinit var prefs: SharedPreferences
    private var mdbNew: StatusDatabase? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        mdbNew = StatusDatabase.getInstance(requireContext())

        prefs = requireContext().getSharedPreferences("login", Context.MODE_PRIVATE)

        val tvname = "Welcome, ${prefs.getString("nama", "")}"
        tv_welcome.text = tvname

        getDataStatus()

        //Custom Ikon Tambah
        tv_logout.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle("Keluar")
                .setMessage("Yakin keluar?")
                .setIcon(R.drawable.note_transparant)
                .setPositiveButton("Ya") { p0, p1 ->
                    prefs.edit().clear().apply()
                    it.findNavController().navigate(R.id.action_homeScreenFragment_to_loginFragment)
                }.setNegativeButton("Tidak") { p0, p1 ->
                }.show()
        }

        //Ikon tambah data pada custom dialog
        fab_add.setOnClickListener {
            val customDialog =
                LayoutInflater.from(requireContext()).inflate(R.layout.input_data, null, false)
            val alertDialog = AlertDialog.Builder(requireContext())
                .setView(customDialog)
                .create()

            //Eksekusi data di input data
            customDialog.btn_input.setOnClickListener {
                GlobalScope.async {
                    val nama = customDialog.input_judul.text.toString()
                    val status = customDialog.input_note.text.toString()

                    val hasil = mdbNew?.statusDao()?.insertStatus(Status(null, nama, status))

                    activity?.runOnUiThread {
                        if (hasil != 0.toLong()) {
                            Toast.makeText(requireContext(), "Succes", Toast.LENGTH_LONG).show()
                            Log.i("Success", hasil.toString())
                            alertDialog.dismiss()
                        } else {
                            Toast.makeText(requireContext(), "Failed", Toast.LENGTH_LONG).show()
                            Log.i("Failed", hasil.toString())
                        }
                    }
                }

            }
            alertDialog.show()
        }
    }

    //Menampilkan data
    fun getDataStatus (){
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        GlobalScope.launch {
            val listdata = mdbNew?.statusDao()?.getAllStatus()

            activity?.runOnUiThread {
                listdata.let {
                    val adapt = AdapterStatus(it!!)
                    recyclerView.adapter = adapt
                }
            }
        }

    }

    //======================
    override fun onResume() {
        super.onResume()
        getDataStatus()
    }

    override fun onDestroy() {
        super.onDestroy()
        StatusDatabase.destroyInstance()
    }

}