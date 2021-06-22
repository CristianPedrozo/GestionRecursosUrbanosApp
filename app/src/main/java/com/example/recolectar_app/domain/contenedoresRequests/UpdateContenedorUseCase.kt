package com.example.recolectar_app.domain.contenedoresRequests

import com.example.recolectar_app.data.ContenedoresRepository
import com.example.recolectar_app.model.UpdateRequestModel

class UpdateContenedorUseCase {
    private val repository = ContenedoresRepository()
    suspend operator fun invoke(updateRequestModel: UpdateRequestModel) : Boolean = repository.updateContenedor(updateRequestModel)
}