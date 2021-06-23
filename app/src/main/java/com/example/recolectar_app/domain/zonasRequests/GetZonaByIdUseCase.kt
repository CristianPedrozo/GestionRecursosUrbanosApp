package com.example.recolectar_app.domain.zonasRequests

import com.example.recolectar_app.data.ZonasRepository
import com.example.recolectar_app.model.zona.ZonaModel

class GetZonaByIdUseCase {
    val repository = ZonasRepository()
    suspend operator fun invoke(id:String):List<ZonaModel> = repository.getZonaById(id)
}