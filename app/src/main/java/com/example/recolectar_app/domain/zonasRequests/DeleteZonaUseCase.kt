package com.example.recolectar_app.domain.zonasRequests

import com.example.recolectar_app.data.ZonasRepository
import com.example.recolectar_app.model.zona.DeleteZonaRequestModel

class DeleteZonaUseCase {
    private val repository = ZonasRepository()
    suspend operator fun invoke(deleteZonaRequestModel: DeleteZonaRequestModel) : Boolean = repository.deleteZona(deleteZonaRequestModel)
}