package com.yeray_yas.marvelsuperheroes.presentation.superheroes.list

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.yeray_yas.marvelsuperheroes.databinding.ActivityCharacterListBinding
import com.yeray_yas.marvelsuperheroes.ui.epoxy.CharacterListPagingEpoxyController

class CharacterListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCharacterListBinding

    private val epoxyController = CharacterListPagingEpoxyController()
    private val viewModel: CharactersViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCharacterListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.charactersPagedListLiveData.observe(this) { pagedList ->
            epoxyController.submitList(pagedList)
        }

        binding.epoxyRecyclerView.setController(epoxyController)
    }
}