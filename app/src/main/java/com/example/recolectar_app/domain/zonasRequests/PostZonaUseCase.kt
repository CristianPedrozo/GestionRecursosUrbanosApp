package com.example.recolectar_app.domain.zonasRequests

import com.example.recolectar_app.data.ZonasRepository
import com.example.recolectar_app.model.zona.ZonaModel
import com.google.gson.JsonObject
import org.json.JSONObject

class PostZonaUseCase {
    private val repository = ZonasRepository()
    suspend operator fun invoke(zona:ZonaModel) : Boolean = repository.postNewZona(zona)
}