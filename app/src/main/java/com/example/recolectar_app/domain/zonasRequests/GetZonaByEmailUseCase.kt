package com.example.recolectar_app.domain.zonasRequests

import com.example.recolectar_app.data.ZonasRepository
import com.example.recolectar_app.model.zona.ZonaModel

class GetZonaByEmailUseCase {
    val repository = ZonasRepository()
    suspend operator fun invoke(email:String):List<ZonaModel> = repository.getZonaByEmail(email)
}