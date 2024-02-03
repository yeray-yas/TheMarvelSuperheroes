package com.yeray_yas.marvelsuperheroes.presentation.ui.superheroes.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.firebase.analytics.FirebaseAnalytics
import com.yeray_yas.marvelsuperheroes.databinding.FragmentCharacterListBinding
import com.yeray_yas.marvelsuperheroes.presentation.ui.epoxy.CharacterListPagingEpoxyController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharacterListFragment : Fragment() {

    private lateinit var binding: FragmentCharacterListBinding

    private val viewModel: CharacterViewModel by viewModels()
    private val epoxyController = CharacterListPagingEpoxyController(::onCharacterSelected)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inicializar el objeto de enlace
        binding = FragmentCharacterListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        // Analytics Events
        val bundle = Bundle()
        bundle.putString("message","Firebase integration complete")
        context?.let { FirebaseAnalytics.getInstance(it) }?.logEvent("InitScreen", bundle)

        viewModel.charactersPagedListLiveData.observe(viewLifecycleOwner) { pagedList ->
            epoxyController.submitList(pagedList)
        }

        binding.epoxyRecyclerView.setController(epoxyController)
    }

    private fun onCharacterSelected(characterId: Int) {
       val directions =  CharacterListFragmentDirections.actionCharacterListFragmentToCharacterDetailFragment(
           characterId = characterId
       )
        findNavController().navigate(directions)
    }
}