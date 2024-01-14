package com.yeray_yas.marvelsuperheroes.presentation.ui.superheroes.search

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.yeray_yas.marvelsuperheroes.R
import com.yeray_yas.marvelsuperheroes.databinding.FragmentCharacterSearchBinding
import com.yeray_yas.marvelsuperheroes.presentation.ui.epoxy.CharacterSearchEpoxyController
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@OptIn(ObsoleteCoroutinesApi::class)
class CharacterSearchFragment : Fragment(R.layout.fragment_character_search) {

    private var _binding: FragmentCharacterSearchBinding? = null
    private val binding get() = _binding!!

    private val epoxyController = CharacterSearchEpoxyController(::onCharacterSelected)

    private val viewModel: CharacterSearchViewModel by viewModels()

    private val handler = Handler(Looper.getMainLooper())
    private var searchRunnable: Runnable? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCharacterSearchBinding.bind(view)

        binding.epoxyRecyclerView.setControllerAndBuildModels(epoxyController)

        setupSearchView()

        observeSuperheroesListChanges(epoxyController)

        observeExceptions(epoxyController)

    }

    private fun observeExceptions(epoxyController: CharacterSearchEpoxyController) {
        viewModel.localExceptionEventLiveData.observe(viewLifecycleOwner) { event ->
            event.getContent()?.let { localException ->
                epoxyController.localException = localException
            }
        }
    }

    private fun observeSuperheroesListChanges(epoxyController: CharacterSearchEpoxyController) {
        lifecycleScope.launch {
            viewModel.flow.collectLatest { pagingData ->
                epoxyController.localException = null
                epoxyController.submitData(pagingData)
            }
        }
    }


    private fun setupSearchView() {
        val searchView = binding.searchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                       lifecycleScope.launch {
                           viewModel.submitQuery(query)
                       }
                       searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                writeWithPause(newText)
                return true
            }
        })
    }

    private fun writeWithPause(newText: String?) {
        searchRunnable?.let { handler.removeCallbacks(it) } // Elimina el Runnable anterior antes de programar uno nuevo.

        searchRunnable = Runnable {
            lifecycleScope.launch {
                viewModel.submitQuery(newText)
            }
        }

        handler.postDelayed(searchRunnable!!, 300L)
    }

    private fun onCharacterSelected(characterId: Int) {
        val directions =  CharacterSearchFragmentDirections.actionCharacterSearchFragmentToCharacterDetailFragment2(
            characterId = characterId
        )
        findNavController().navigate(directions)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}