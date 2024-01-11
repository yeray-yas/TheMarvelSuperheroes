package com.yeray_yas.marvelsuperheroes.presentation.superheroes.list

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.yeray_yas.marvelsuperheroes.databinding.ActivityCharacterListBinding
import com.yeray_yas.marvelsuperheroes.presentation.superheroes.detail.CharacterDetailsActivity
import com.yeray_yas.marvelsuperheroes.presentation.ui.epoxy.CharacterListPagingEpoxyController
import com.yeray_yas.marvelsuperheroes.utils.Constants.INTENT_EXTRA_CHARACTER_ID

class CharacterListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCharacterListBinding

    private val epoxyController = CharacterListPagingEpoxyController (::onCharacterSelected)
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

    private fun onCharacterSelected(characterId: Int) {
        val intent = Intent(this, CharacterDetailsActivity::class.java)
        intent.putExtra(INTENT_EXTRA_CHARACTER_ID, characterId)
        startActivity(intent)
    }
}