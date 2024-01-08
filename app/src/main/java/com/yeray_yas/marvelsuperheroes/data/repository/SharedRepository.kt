package com.yeray_yas.marvelsuperheroes.data.repository

import com.yeray_yas.marvelsuperheroes.data.model.GetCharacterByIdResponse
import com.yeray_yas.marvelsuperheroes.data.network.remote.NetworkLayer

class SharedRepository {

    suspend fun getCharacterById(characterId: Int): GetCharacterByIdResponse? {
        val request = NetworkLayer.apiClient.getCharacterById(characterId)

        return if (request.isSuccessful) {
            request.body()
        } else {
            // Manejar el caso en que la solicitud no sea exitosa, por ejemplo, log o mostrar un mensaje de error
            // Logging: Log.e("SharedRepository", "Error en la solicitud: ${request.code()}")
            // Mostrar mensaje de error: Toast.makeText(App.instance, "Error en la solicitud: ${request.code()}", Toast.LENGTH_SHORT).show()
            null
        }
    }
}
