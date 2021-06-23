package com.example.recolectar_app.domain.camionesRequests

import com.example.recolectar_app.data.CamionRepository
import com.example.recolectar_app.model.UpdateRequestModel

class UpdateCamionUseCase {
    private val repository = CamionRepository()
    suspend operator fun invoke(updateRequestModel: UpdateRequestModel) : Boolean = repository.updateCamion(updateRequestModel)
}