package com.yeray_yas.marvelsuperheroes.presentation.ui.superheroes.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.yeray_yas.marvelsuperheroes.databinding.FragmentCharacterListBinding
import com.yeray_yas.marvelsuperheroes.presentation.ui.epoxy.CharacterListPagingEpoxyController

class CharacterListFragment : Fragment() {

    private lateinit var binding: FragmentCharacterListBinding

    private val viewModel: CharactersViewModel by viewModels()
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

    /*val publicKey = "3de6bbd5de0a40038da2c8fe677fb23b"
     val privateKey = "214c207509b1ed4c5d6456ad38a6ff91f382ead4"
     val ts = Calendar.getInstance().timeInMillis.toString()

     val hash = hashSetting.generateHash(ts, publicKey, privateKey)
     val url = "https://gateway.marvel.com/v1/public/characters/1011334?apikey=$publicKey&hash=$hash&ts=$ts"
     println(url)*/

}