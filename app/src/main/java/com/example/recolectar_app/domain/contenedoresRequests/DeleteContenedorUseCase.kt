package com.example.recolectar_app.domain.contenedoresRequests

import com.example.recolectar_app.data.ContenedoresRepository
import com.example.recolectar_app.model.contenedor.DeleteContenedorRequestModel

class DeleteContenedorUseCase {
    private val repository = ContenedoresRepository()
    suspend operator fun invoke(deleteContenedorRequestModel: DeleteContenedorRequestModel) = repository.deleteContenedor(deleteContenedorRequestModel)
}