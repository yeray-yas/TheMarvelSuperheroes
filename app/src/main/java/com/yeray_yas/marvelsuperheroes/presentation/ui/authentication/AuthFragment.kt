package com.yeray_yas.marvelsuperheroes.presentation.ui.authentication

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.internal.ViewUtils.hideKeyboard
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.yeray_yas.marvelsuperheroes.R
import com.yeray_yas.marvelsuperheroes.databinding.FragmentAuthBinding
import com.yeray_yas.marvelsuperheroes.utils.firebase.ProviderType


const val GOOGLE_SIGN_IN = 100

class AuthFragment : Fragment() {

    private lateinit var binding: FragmentAuthBinding
    private var isAuthInProgress = false

    private val callbackManager = CallbackManager.Factory.create()
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

        setup(view)
        session(view)

    }

    private fun session(view: View) {
        val prefs =
            activity?.getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE)
        val email = prefs?.getString("email", null)
        val provider = prefs?.getString("provider", null)

        if (email != null && provider != null) {
            // Quiere decir que ya tenemos iniciada una sesión en nuestra app
            loggedView()
            onLoggedButtonPressed(view, email, provider)
            onLogOutButtonPressed(provider)

        } else {
            normalView()
        }
    }

    private fun onLogOutButtonPressed(provider: String) {
        binding.logOutButton.setOnClickListener {
            binding.logOutButton.isEnabled = false

            //Erase data
            val editPrefs =
                activity?.getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE)
                    ?.edit()

            editPrefs?.clear()
            editPrefs?.apply()

            if (provider == ProviderType.FACEBOOK.name) {
                LoginManager.getInstance().logOut()
            }

            FirebaseAuth.getInstance().signOut()
            toastMessage(getString(R.string.successfully_logged_out_text))
            normalView()

            binding.logOutButton.isEnabled = true

            binding.facebookButton.setCompoundDrawablesWithIntrinsicBounds(
                R.mipmap.facebook, //left
                0, //top
                0, //right
                0 //bottom
            )
            binding.googleButton.setCompoundDrawablesWithIntrinsicBounds(
                R.mipmap.google, //left
                0, //top
                0, //right
                0 //bottom
            )
        }
    }

    private fun onLoggedButtonPressed(view: View, email: String?, provider: String?) {
        binding.loggedButton.setOnClickListener {
            if (email != null) {
                provider?.let { it1 -> ProviderType.valueOf(it1) }
                    ?.let { it2 -> onLoginSuccess(view, email, it2) }
            }
        }
    }

    private fun loggedView() {
        with(binding) {
            binding.emailEditText.visibility = View.INVISIBLE
            binding.passwordEditText.visibility = View.INVISIBLE
            binding.signUpButton.visibility = View.GONE
            binding.loginButton.visibility = View.GONE
            binding.loggedButton.visibility = View.VISIBLE
            binding.logOutButton.visibility = View.VISIBLE

            // Disable Facebook & Google buttons
            facebookButton.isEnabled = false
            googleButton.isEnabled = false


            // Aplicar filtro de desaturación (escala de grises)
            facebookButton.setCompoundDrawablesWithIntrinsicBounds(
                R.mipmap.facebook_gray, //left
                0, //top
                0, //right
                0 //bottom
            )
            googleButton.setCompoundDrawablesWithIntrinsicBounds(
                R.mipmap.google_gray, //left
                0, //top
                0, //right
                0 //bottom
            )
            facebookButton.setBackgroundColor(Color.LTGRAY)
            googleButton.setBackgroundColor(Color.LTGRAY)


        }
    }

    private fun normalView() {
        with(binding) {
            emailEditText.visibility = View.VISIBLE
            passwordEditText.visibility = View.VISIBLE
            signUpButton.visibility = View.VISIBLE
            loginButton.visibility = View.VISIBLE
            loggedButton.visibility = View.GONE
            logOutButton.visibility = View.GONE

            // Activar botones de Facebook y Google
            facebookButton.isEnabled = true
            googleButton.isEnabled = true

            // Restaurar color del fondo de los botones
            val defaultColor = ContextCompat.getColor(requireContext(), R.color.white)
            facebookButton.backgroundTintList = ColorStateList.valueOf(defaultColor)
            googleButton.backgroundTintList = ColorStateList.valueOf(defaultColor)
        }
    }

    @Suppress("DEPRECATION")
    private fun setup(view: View) {
        with(binding) {
            val mail = emailEditText.text
            val pass = passwordEditText.text

            signUpButton.setOnClickListener {
                if (!isAuthInProgress && mail.isNotEmpty() && pass.isNotEmpty()) {
                    // Disable buttons during authentication
                    signUpButton.isEnabled = false
                    loginButton.isEnabled = false

                    FirebaseAuth.getInstance()
                        .createUserWithEmailAndPassword(mail.toString(), pass.toString())
                        .addOnCompleteListener {
                            // Enable buttons after authentication
                            signUpButton.isEnabled = true
                            loginButton.isEnabled = true

                            if (it.isSuccessful) {
                                onSignUpSuccess(
                                    view,
                                    it.result.user?.email ?: ""
                                )
                            } else {
                                showSingInAlert()
                            }
                        }
                } else {
                    toastMessage(getString(R.string.email_or_pass_empty_text))
                }
            }

            loginButton.setOnClickListener {
                if (!isAuthInProgress && mail.isNotEmpty() && pass.isNotEmpty()) {
                    // Desactivar botones durante la autenticación
                    signUpButton.isEnabled = false
                    loginButton.isEnabled = false

                    FirebaseAuth.getInstance()
                        .signInWithEmailAndPassword(mail.toString(), pass.toString())
                        .addOnCompleteListener {
                            // Activar botones después de la autenticación
                            signUpButton.isEnabled = true
                            loginButton.isEnabled = true

                            if (it.isSuccessful) {
                                onLoginSuccess(
                                    view,
                                    it.result.user?.email ?: "",
                                    ProviderType.BASIC
                                )
                            } else {
                                showLoginAlert()
                            }
                        }
                } else {
                    toastMessage(getString(R.string.email_or_pass_empty_text))
                }
            }

            googleButton.setOnClickListener {
                if (!isAuthInProgress) {
                    isAuthInProgress = true

                    val googleConf =
                        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                            .requestIdToken(getString(R.string.default_web_client_id))
                            .requestEmail()
                            .build()

                    val googleClient = GoogleSignIn.getClient(requireActivity(), googleConf)
                    googleClient.signOut()

                    startActivityForResult(googleClient.signInIntent, GOOGLE_SIGN_IN)
                }
            }

            facebookButton.setOnClickListener {
                if (!isAuthInProgress) {
                    isAuthInProgress = true

                    LoginManager.getInstance().logInWithReadPermissions(
                        this@AuthFragment,
                        listOf("email")
                    )

                    LoginManager.getInstance().registerCallback(callbackManager,
                        object : FacebookCallback<LoginResult> {
                            override fun onCancel() {
                                isAuthInProgress = false
                                // Nothing to do
                            }

                            override fun onError(error: FacebookException) {
                                isAuthInProgress = false
                                showLoginAlert()
                            }

                            override fun onSuccess(result: LoginResult) {
                                result.let { facebookCall ->
                                    val token = facebookCall.accessToken
                                    val credential =
                                        FacebookAuthProvider.getCredential(token.token)
                                    FirebaseAuth.getInstance().signInWithCredential(credential)
                                        .addOnCompleteListener {
                                            // Activar botones después de la autenticación
                                            signUpButton.isEnabled = true
                                            loginButton.isEnabled = true

                                            isAuthInProgress = false

                                            if (it.isSuccessful) {
                                                onFacebookOrGoogleLoginSuccess()
                                            } else {
                                                showLoginAlert()
                                            }
                                        }
                                }
                            }
                        })
                }
            }
        }
    }

    private fun toastMessage(message: String) {
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

    private fun onLoginSuccess(view: View, email: String, provider: ProviderType) {
        toastMessage("Successfully logged")
        saveData(email, provider)
        success(view)
    }

    private fun onFacebookOrGoogleLoginSuccess() {
        toastMessage("Successfully logged")
        success(requireView())
    }

    private fun onSignUpSuccess(view: View, email: String) {
        toastMessage("Successfully signed up")
        saveData(email, ProviderType.BASIC)
        success(view)
    }

    private fun saveData(
        email: String,
        provider: ProviderType
    ) {
        // Saving user data
        val prefs =
            activity?.getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE)
                ?.edit()
        prefs?.putString("email", email) ?: ""
        prefs?.putString("provider", provider.toString())
        prefs?.apply()
    }

    @SuppressLint("RestrictedApi")
    private fun success(view: View) {
        hideKeyboard(view)
        // Limpia la pila de retroceso para que el usuario no pueda volver a la pantalla de inicio de sesión
        val navOptions = NavOptions.Builder()
            .setPopUpTo(R.id.authFragment, true)
            .build()

        val directions = AuthFragmentDirections.actionAuthFragmentToCharacterListFragment()
        findNavController().navigate(directions, navOptions)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == GOOGLE_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)

                if (account != null) {
                    val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                    FirebaseAuth.getInstance().signInWithCredential(credential)
                        .addOnCompleteListener {
                            // Enable buttons after authentication
                            binding.signUpButton.isEnabled = true
                            binding.loginButton.isEnabled = true

                            isAuthInProgress = false

                            if (it.isSuccessful) {
                                onFacebookOrGoogleLoginSuccess()
                            } else {
                                showLoginAlert()
                            }
                        }
                }
            } catch (e: ApiException) {
                // Enable buttons after authentication
                binding.signUpButton.isEnabled = true
                binding.loginButton.isEnabled = true

                isAuthInProgress = false
            }
        }
    }
}