package com.example.recolectar_app.domain.zonasRequests

import com.example.recolectar_app.data.ZonasRepository
import com.example.recolectar_app.model.zona.ZonaModel


class GetZonasUseCase {

    private val repository = ZonasRepository()
    suspend operator fun invoke():List<ZonaModel> = repository.getAllZonas()

}