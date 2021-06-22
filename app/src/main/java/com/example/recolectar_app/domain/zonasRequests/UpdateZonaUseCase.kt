package com.example.recolectar_app.domain.zonasRequests

import com.example.recolectar_app.data.ZonasRepository
import com.example.recolectar_app.model.UpdateRequestModel
import com.example.recolectar_app.model.zona.UpdateZonaRequestModel

class UpdateZonaUseCase {
    private val repository = ZonasRepository()
    suspend operator fun invoke(updateRequestModel: UpdateRequestModel) : Boolean = repository.updateZona(updateRequestModel)
}