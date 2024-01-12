package com.yeray_yas.marvelsuperheroes.presentation.ui.superheroes.search

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
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

    private val viewModel: CharacterSearchViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCharacterSearchBinding.bind(view)

        val epoxyController = CharacterSearchEpoxyController {characterId ->
            // todo navigate to details page with ID
        }

        binding.epoxyRecyclerView.setControllerAndBuildModels(epoxyController)

        setupSearchView()

        observeSuperheroesListChanges(epoxyController)

        // todo
    }

    private fun observeSuperheroesListChanges(epoxyController: CharacterSearchEpoxyController) {
        lifecycleScope.launch {
            viewModel.flow.collectLatest { pagingData ->
                epoxyController.submitData(pagingData)
            }
        }
    }


    private fun setupSearchView() {
        val searchView = binding.searchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {

                    Log.i("THEQUERY", "Has escrito: $query")
                       lifecycleScope.launch {
                           viewModel.submitQuery(query)
                       }
                       searchView.clearFocus()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                writeWithPause(newText)
                return true
            }
        })
    }

    private fun writeWithPause(newText: String?) {
        val handler = Handler(Looper.getMainLooper())
        val searchRunnable = Runnable {
            viewModel.submitQuery(newText)
        }
        handler.removeCallbacks(searchRunnable)
        handler.postDelayed(searchRunnable, 500L)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}