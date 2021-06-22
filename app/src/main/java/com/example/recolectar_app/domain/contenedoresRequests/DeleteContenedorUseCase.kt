package com.example.recolectar_app.domain.contenedoresRequests

import com.example.recolectar_app.data.ContenedoresRepository
import com.example.recolectar_app.model.DeleteRequestModel

class DeleteContenedorUseCase() {
    private val repository = ContenedoresRepository()
    suspend operator fun invoke(deleteRequestModel: DeleteRequestModel) = repository.deleteContenedor(deleteRequestModel)
}