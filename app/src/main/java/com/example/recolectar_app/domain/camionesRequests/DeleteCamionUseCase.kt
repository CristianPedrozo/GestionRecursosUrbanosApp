package com.example.recolectar_app.domain.camionesRequests

import com.example.recolectar_app.data.CamionRepository
import com.example.recolectar_app.model.DeleteRequestModel

class DeleteCamionUseCase {
    private val repository = CamionRepository()
    suspend operator fun invoke(deleteRequestModel: DeleteRequestModel) : Boolean = repository.deleteCamion(deleteRequestModel)
}