package com.example.recolectar_app.domain.contenedoresRequests

import com.example.recolectar_app.data.ContenedoresRepository
import com.example.recolectar_app.model.contenedor.ContenedorModel

class GetContenedorByIdUseCase {
    private val repository = ContenedoresRepository()
    suspend operator fun invoke(id: String): List<ContenedorModel> = repository.getContenedorById(id)
}