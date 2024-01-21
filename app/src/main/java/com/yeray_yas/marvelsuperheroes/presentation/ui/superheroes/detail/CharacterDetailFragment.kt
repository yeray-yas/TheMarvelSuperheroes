package com.yeray_yas.marvelsuperheroes.presentation.ui.superheroes.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.yeray_yas.marvelsuperheroes.databinding.FragmentCharacterDetailBinding
import com.yeray_yas.marvelsuperheroes.presentation.ui.epoxy.CharacterDetailsEpoxyController


class CharacterDetailFragment : Fragment() {

    private lateinit var binding: FragmentCharacterDetailBinding

    private val viewModel: CharacterDetailViewModel by viewModels()

    private val epoxyController = CharacterDetailsEpoxyController()
    private val safeArgs: CharacterDetailFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharacterDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.characterByIdLiveData.observe(viewLifecycleOwner) { character ->
            epoxyController.character = character
            if (character == null) {
                Toast.makeText(requireActivity(), "Unsuccessful network call!!!", Toast.LENGTH_LONG)
                    .show()
                findNavController().navigateUp()
                return@observe
            }
        }

        viewModel.refreshCharacter(
            characterId = safeArgs.characterId)

        binding.epoxyRecyclerView.setControllerAndBuildModels(epoxyController)
    }
}