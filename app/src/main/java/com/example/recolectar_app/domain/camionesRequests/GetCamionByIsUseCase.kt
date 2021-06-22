package com.example.recolectar_app.domain.camionesRequests

import com.example.recolectar_app.data.CamionRepository
import com.example.recolectar_app.model.camion.CamionModel

class GetCamionByIsUseCase {
    private val repository = CamionRepository()
    suspend operator fun invoke(id:String) : List<CamionModel> = repository.getCamionById(id)
}