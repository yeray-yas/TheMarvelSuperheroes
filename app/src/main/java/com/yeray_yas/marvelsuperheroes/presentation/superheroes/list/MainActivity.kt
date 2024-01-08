package com.yeray_yas.marvelsuperheroes.presentation.superheroes.list

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import com.yeray_yas.marvelsuperheroes.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: SharedViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        viewModel.refreshCharacter(1011334)
        viewModel.characterByIdLiveData.observe(this) { response ->
            if (response == null) {
                Toast.makeText(this@MainActivity, "Unsuccessful network call!!!", Toast.LENGTH_LONG)
                    .show()
                return@observe
            }

            val body = response.data
            val name = body.results[0].name
            val thumbnail = body.results[0].thumbnail
            binding.name.text = name
            Picasso.get().load(thumbnail.path + "." + thumbnail.extension).into(binding.image)

            //Picasso.get().load("http://i.annihil.us/u/prod/marvel/i/mg/c/e0/535fecbbb9784/portrait_xlarge.jpg").into(binding.image)
            Log.i(
                "url",
                body.results[0].thumbnail.path + "/portrait_xlarge." + body.results[0].thumbnail.extension
            )

        }


        /*val publicKey = "3de6bbd5de0a40038da2c8fe677fb23b"
        val privateKey = "214c207509b1ed4c5d6456ad38a6ff91f382ead4"
        val ts = Calendar.getInstance().timeInMillis.toString()

        val hash = hashSetting.generateHash(ts, publicKey, privateKey)
        val url = "https://gateway.marvel.com/v1/public/characters/1011334?apikey=$publicKey&hash=$hash&ts=$ts"
        println(url)*/
    }
}