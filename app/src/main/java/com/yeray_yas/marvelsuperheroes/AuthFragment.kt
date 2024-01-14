package com.yeray_yas.marvelsuperheroes

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.yeray_yas.marvelsuperheroes.databinding.FragmentAuthBinding


class AuthFragment : Fragment() {

    private lateinit var binding: FragmentAuthBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentAuthBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Analytics Events
        val analytics = FirebaseAnalytics.getInstance(requireContext())
        val bundle = Bundle()
        bundle.putString("message", "Firebase integration complete")
        analytics.logEvent("InitScreen", bundle)


        //Setup
        setup()
    }

    private fun setup() {
        with(binding) {
            val mail = emailEditText.text
            val pass = passwordEditText.text

            signUpButton.setOnClickListener {
                // Toast.makeText(requireContext(), "You have pressed the sign up button", Toast.LENGTH_LONG).show()
                if (mail.isNotEmpty() && pass.isNotEmpty()) {
                    FirebaseAuth.getInstance()
                        .createUserWithEmailAndPassword(mail.toString(), pass.toString())
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                Toast.makeText(requireContext(), "You have signed successfully", Toast.LENGTH_LONG).show()
                            } else {
                                showSingInAlert()

                            }
                        }
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Email and/or password fields are empty",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }



        }
    }

    private fun showSingInAlert() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Error")
        builder.setMessage("  -- Error registering user--\n\nIs written the email in the correct format?\n\nusername@domain.com")
        builder.setPositiveButton("Accept", null)
        val dialog = builder.create()
        dialog.show()
    }
}