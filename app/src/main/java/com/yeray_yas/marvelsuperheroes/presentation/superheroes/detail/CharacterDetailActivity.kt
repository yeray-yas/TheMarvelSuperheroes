package com.yeray_yas.marvelsuperheroes.presentation.superheroes.detail

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.yeray_yas.marvelsuperheroes.databinding.ActivityCharacterDetailBinding
import com.yeray_yas.marvelsuperheroes.presentation.ui.epoxy.CharacterDetailsEpoxyController
import com.yeray_yas.marvelsuperheroes.utils.Constants.INTENT_EXTRA_CHARACTER_ID

class CharacterDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCharacterDetailBinding

    private val viewModel: SharedViewModel by viewModels()

    private val epoxyController = CharacterDetailsEpoxyController()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCharacterDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel.characterByIdLiveData.observe(this) { character ->
            epoxyController.character = character
            if (character == null) {
                Toast.makeText(this@CharacterDetailActivity, "Unsuccessful network call!!!", Toast.LENGTH_LONG)
                    .show()
                return@observe
            }
        }

        val id = intent.getIntExtra(INTENT_EXTRA_CHARACTER_ID, 0)
        viewModel.refreshCharacter(characterId = id)

        binding.epoxyRecyclerView.setControllerAndBuildModels(epoxyController)

        /*val publicKey = "3de6bbd5de0a40038da2c8fe677fb23b"
        val privateKey = "214c207509b1ed4c5d6456ad38a6ff91f382ead4"
        val ts = Calendar.getInstance().timeInMillis.toString()

        val hash = hashSetting.generateHash(ts, publicKey, privateKey)
        val url = "https://gateway.marvel.com/v1/public/characters/1011334?apikey=$publicKey&hash=$hash&ts=$ts"
        println(url)*/
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}