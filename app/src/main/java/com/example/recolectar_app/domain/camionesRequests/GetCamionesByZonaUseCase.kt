package com.example.recolectar_app.domain.camionesRequests

import com.example.recolectar_app.data.CamionRepository
import com.example.recolectar_app.model.camion.CamionModel

class GetCamionesByZonaUseCase {
    private val repository = CamionRepository()
    suspend operator fun invoke(zona: String) : List<CamionModel> = repository.getCamionByZona(zona)
}