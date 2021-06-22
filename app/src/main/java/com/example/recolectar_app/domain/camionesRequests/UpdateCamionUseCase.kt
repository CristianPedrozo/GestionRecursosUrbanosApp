package com.example.recolectar_app.domain.camionesRequests

import com.example.recolectar_app.data.CamionRepository
import com.example.recolectar_app.model.camion.UpdateCamionRequestModel

class UpdateCamionUseCase {
    private val repository = CamionRepository()
    suspend operator fun invoke(updateCamionRequestModel: UpdateCamionRequestModel) : Boolean = repository.updateCamion(updateCamionRequestModel)
}