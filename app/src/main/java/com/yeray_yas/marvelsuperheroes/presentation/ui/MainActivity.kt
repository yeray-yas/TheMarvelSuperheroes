package com.yeray_yas.marvelsuperheroes.presentation.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.yeray_yas.marvelsuperheroes.databinding.ActivityMainBinding
import com.yeray_yas.marvelsuperheroes.presentation.superheroes.detail.CharacterDetailsEpoxyController
import com.yeray_yas.marvelsuperheroes.presentation.superheroes.list.SharedViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: SharedViewModel by viewModels()

    private val epoxyController = CharacterDetailsEpoxyController()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        viewModel.characterByIdLiveData.observe(this) { response ->
            epoxyController.characterResponse = response
            if (response == null) {
                Toast.makeText(this@MainActivity, "Unsuccessful network call!!!", Toast.LENGTH_LONG)
                    .show()
                return@observe
            }
        }
        viewModel.refreshCharacter(1017100)

        binding.epoxyRecyclerView.setControllerAndBuildModels(epoxyController)

        /*val publicKey = "3de6bbd5de0a40038da2c8fe677fb23b"
        val privateKey = "214c207509b1ed4c5d6456ad38a6ff91f382ead4"
        val ts = Calendar.getInstance().timeInMillis.toString()

        val hash = hashSetting.generateHash(ts, publicKey, privateKey)
        val url = "https://gateway.marvel.com/v1/public/characters/1011334?apikey=$publicKey&hash=$hash&ts=$ts"
        println(url)*/
    }
}