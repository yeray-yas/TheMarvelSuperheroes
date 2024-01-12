package com.yeray_yas.marvelsuperheroes.presentation.ui.superheroes.search

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import com.yeray_yas.marvelsuperheroes.R
import com.yeray_yas.marvelsuperheroes.databinding.FragmentCharacterSearchBinding

class CharacterSearchFragment : Fragment(R.layout.fragment_character_search) {

    private var _binding: FragmentCharacterSearchBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CharacterSearchViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCharacterSearchBinding.bind(view)



        setupSearchView()

        //observeSuperheroesListChanges()
        // todo
    }

    /*    private fun observeSuperheroesListChanges() {
            lifecycleScope.launch {
                viewModel.superheroes.collectLatest { pagingData ->
                    adapter.submitData(pagingData)
                }
            }
        }*/

    private fun setupSearchView() {
        val searchView = binding.searchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {

                    Log.i("THEQUERY", "Has escrito: $query")
                    /*   lifecycleScope.launch {
                           viewModel.setSearchQuery(query)
                       }
                       searchView.clearFocus()*/
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
            println(newText)
        }
        handler.removeCallbacks(searchRunnable)
        handler.postDelayed(searchRunnable, 500L)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}