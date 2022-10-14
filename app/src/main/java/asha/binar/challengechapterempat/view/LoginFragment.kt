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
import asha.binar.challengechapterempat.databinding.FragmentLoginBinding
import asha.binar.data.StatusDatabase


class LoginFragment : Fragment() {
    lateinit var binding : FragmentLoginBinding
    lateinit var share : SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        share = requireActivity().getSharedPreferences("ACC", Context.MODE_PRIVATE)!!

        binding.btnLogin.setOnClickListener {
            val username = binding.logUsername.text.toString()
            val pass = binding.logPassword.text.toString()
            if (validateUser(username)) {
                if (validatePass(pass)) {
                    Toast.makeText(context, "Login Sukses", Toast.LENGTH_SHORT).show()
                    Navigation.findNavController(binding.root)
                        .navigate(R.id.action_loginFragment_to_mainFragment)
                } else {
                    Toast.makeText(context,
                        "Username atau Password Anda Salah!",
                        Toast.LENGTH_LONG).show()
                }
            }else{
                Toast.makeText(context, "Akun tidak terdaftar", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnReg.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_loginFragment_to_registerFragment3)
        }
    }


    //Login Validation
    private fun validatePass(password: String): Boolean {
        val passwordShare = share.getString("password", "")
        if (passwordShare != null){
            if (passwordShare.isBlank()){
                return false
            }else{
                if (passwordShare == password){
                    return true
                }
            }
        }
        return false
    }

    private fun validateUser(username : String): Boolean {
        val userShare = share.getString("username","")

        if (userShare != null){
            if (userShare.isBlank()){
                return false
            }else{
                if (userShare == username){
                    return true
                }
            }
        }
        return false
    }
}