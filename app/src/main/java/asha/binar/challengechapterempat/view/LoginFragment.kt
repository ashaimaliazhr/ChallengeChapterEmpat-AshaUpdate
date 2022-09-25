package asha.binar.challengechapterempat.view

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import asha.binar.challengechapterempat.R
import asha.binar.data.StatusDatabase
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment() {
    private var mdb : StatusDatabase? = null
    private lateinit var prefsLogin : SharedPreferences
    private lateinit var prefsRegister : SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prefsLogin = requireContext().getSharedPreferences("login", Context.MODE_PRIVATE)
        prefsRegister = requireContext().getSharedPreferences("register", Context.MODE_PRIVATE)

        val email = prefsRegister.getString("email","")
        val pass = prefsRegister.getString("pass","")
        val nama = prefsRegister.getString("nama","")

        if (prefsLogin.contains("nama")){
            Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_homeScreenFragment2)
        }

        tv_daftar.setOnClickListener {
            it.findNavController().navigate(R.id.action_loginFragment_to_registerFragment3)
        }

        btn_login.setOnClickListener{
            val getEmail =et_email.text.toString()
            val getPas = et_password.text.toString()



            if (getEmail == email && getPas == pass){
                prefsLogin.edit().putString("nama", nama).apply()
                it.findNavController().navigate(R.id.action_loginFragment_to_homeScreenFragment2)
            }else{
                Toast.makeText(requireContext(), "No Hp/Password salah", Toast.LENGTH_LONG).show()
            }

        }
    }

}