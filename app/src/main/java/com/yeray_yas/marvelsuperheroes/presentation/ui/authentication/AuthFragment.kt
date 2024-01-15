package com.yeray_yas.marvelsuperheroes.presentation.ui.authentication

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.yeray_yas.marvelsuperheroes.R
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

        setup()
    }

    private fun setup() {
        with(binding) {
            val mail = emailEditText.text
            val pass = passwordEditText.text

            signUpButton.setOnClickListener {
                if (mail.isNotEmpty() && pass.isNotEmpty()) {
                    FirebaseAuth.getInstance()
                        .createUserWithEmailAndPassword(mail.toString(), pass.toString())
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                onLoginOrSignUpSuccess()
                            } else {
                                showSingInAlert()
                            }
                        }
                } else {
                    toastMessage(getString(R.string.email_or_pass_empty_text))
                }
            }
            loginButton.setOnClickListener {
                // Toast.makeText(requireContext(), "You have pressed the sign up button", Toast.LENGTH_LONG).show()
                if (mail.isNotEmpty() && pass.isNotEmpty()) {
                    FirebaseAuth.getInstance()
                        .signInWithEmailAndPassword(mail.toString(), pass.toString())
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                onLoginOrSignUpSuccess()
                            } else {
                                showLoginAlert()

                            }
                        }
                } else {
                  toastMessage(getString(R.string.email_or_pass_empty_text))
                }
            }
        }
    }

    private fun toastMessage(message:String) {
        Toast.makeText(
            requireContext(),
            message,
            Toast.LENGTH_LONG
        ).show()
    }

    private fun showSingInAlert() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(getString(R.string.error_title_login_text))
        builder.setMessage(getString(R.string.error_signing_user_text))
        builder.setPositiveButton(getString(R.string.to_accept_text), null)
        val dialog = builder.create()
        dialog.show()
    }

    private fun showLoginAlert() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Error")
        builder.setMessage("Error in mail and/or password")
        builder.setPositiveButton("Accept", null)
        val dialog = builder.create()
        dialog.show()
    }

    private fun onLoginOrSignUpSuccess() {
        toastMessage("Successfully logged")
        // Limpia la pila de retroceso para que el usuario no pueda volver a la pantalla de inicio de sesión
        val navOptions = NavOptions.Builder()
            .setPopUpTo(R.id.authFragment, true)
            .build()

        val directions = AuthFragmentDirections.actionAuthFragmentToCharacterListFragment()
        findNavController().navigate(directions, navOptions)
    }

}