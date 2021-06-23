package com.example.recolectar_app.domain.contenedoresRequests

import com.example.recolectar_app.data.ContenedoresRepository
import com.example.recolectar_app.model.contenedor.ContenedorModel

class GetContenedoresByZonaUseCase {
    private val repository = ContenedoresRepository()
    suspend operator fun invoke(zona: String): List<ContenedorModel> = repository.getContenedorByZona(zona)
}