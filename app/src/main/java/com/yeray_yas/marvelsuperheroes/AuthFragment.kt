package com.yeray_yas.marvelsuperheroes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.analytics.FirebaseAnalytics
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
        //setup()
    }

    private fun setup() {
        TODO("Not yet implemented")
    }
}