package com.example.recolectar_app.domain.contenedoresRequests

import com.example.recolectar_app.data.ContenedoresRepository
import com.example.recolectar_app.model.contenedor.UpdateContenedorRequestModel

class UpdateContenedorUseCase {
    private val repository = ContenedoresRepository()
    suspend operator fun invoke(updateContenedorRequestModel: UpdateContenedorRequestModel) : Boolean = repository.updateContenedor(updateContenedorRequestModel)
}