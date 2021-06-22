package com.example.recolectar_app.domain.camionesRequests

import DeleteCamionRequestModel
import com.example.recolectar_app.data.CamionRepository

class DeleteCamionUseCase {
    private val repository = CamionRepository()
    suspend operator fun invoke(deleteCamionRequestModel: DeleteCamionRequestModel) : Boolean = repository.deleteCamion(deleteCamionRequestModel)
}