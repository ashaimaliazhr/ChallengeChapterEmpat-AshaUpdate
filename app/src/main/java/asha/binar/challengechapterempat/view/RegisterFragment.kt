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
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import asha.binar.challengechapterempat.R
import asha.binar.challengechapterempat.databinding.FragmentRegisterBinding


class RegisterFragment : Fragment() {

    lateinit var binding : FragmentRegisterBinding
    lateinit var share : SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRegisterBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        share = requireActivity().getSharedPreferences("ACC", Context.MODE_PRIVATE)!!

        binding.btnRegsister.setOnClickListener {
            val username = binding.regUsername.text.toString()
            val email = binding.regEmail.text.toString()
            val pass = binding.regPassword.text.toString()
            val rePass = binding.regRepeatPass.text.toString()

            if (pass != rePass){
                Toast.makeText(context, "Password tidak sama!", Toast.LENGTH_SHORT).show()
            }else{
                addAcc(username,email,pass)
                Navigation.findNavController(binding.root).navigate(R.id.action_registerFragment_to_loginFragment3)
            }
        }
    }

    private fun addAcc(username: String, email: String, pass: String) {
        val addAkun = share.edit()
        Log.d("Reg","Full Name : $email")
        Log.d("Reg","Username : $username")
        Log.d("Reg","Password : $pass")
        addAkun.putString("username", username)
        addAkun.putString("fullname", email)
        addAkun.putString("password", pass)
        addAkun.apply()
        Toast.makeText(context, "Registrasi Berhasil, silahkan Login untuk masuk!", Toast.LENGTH_SHORT).show()
    }

}