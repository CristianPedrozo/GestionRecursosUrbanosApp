package com.example.recolectar_app.domain

import com.example.recolectar_app.data.ZonasRepository
import com.google.gson.JsonObject
import org.json.JSONObject

class PostZonaUseCase(jsonObject: JSONObject) {
    val json = jsonObject
    private val repository = ZonasRepository()
    suspend operator fun invoke() {
        repository.postZona(json)
    }

}