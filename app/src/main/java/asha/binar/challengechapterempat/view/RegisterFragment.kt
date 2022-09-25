package asha.binar.challengechapterempat.view

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import asha.binar.challengechapterempat.R
import kotlinx.android.synthetic.main.fragment_register.*

class RegisterFragment : Fragment() {
    private lateinit var prefs: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prefs = requireContext().getSharedPreferences("register", Context.MODE_PRIVATE)

        btn_regis.setOnClickListener {

            val username = input_username.text.toString()
            val email = input_email.text.toString()
            val pass = input_pass.text.toString()
            val verifPass = input_pass_ulang.text.toString()

            prefs.edit().putString("username", username).putString("email", email).putString("pass", pass).putString("verifPass", verifPass).apply()
            it.findNavController().navigate((R.id.action_registerFragment_to_loginFragment3))

        }
    }

}