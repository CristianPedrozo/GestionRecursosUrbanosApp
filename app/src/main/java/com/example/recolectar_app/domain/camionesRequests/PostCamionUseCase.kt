package com.example.recolectar_app.domain.camionesRequests

import com.example.recolectar_app.data.CamionRepository
import com.example.recolectar_app.model.camion.CamionModel

class PostCamionUseCase {
    private val repository = CamionRepository()
    suspend operator fun invoke(camion: CamionModel) : Boolean = repository.postNewCamion(camion)
}