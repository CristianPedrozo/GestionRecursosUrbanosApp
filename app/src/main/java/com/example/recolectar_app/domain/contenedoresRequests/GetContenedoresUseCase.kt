package com.example.recolectar_app.domain.contenedoresRequests

import com.example.recolectar_app.data.ContenedoresRepository
import com.example.recolectar_app.model.contenedor.ContenedorModel
import com.example.recolectar_app.data.ZonasRepository

class GetContenedoresUseCase {
    private val repository = ContenedoresRepository()
    suspend operator fun invoke(): List<ContenedorModel> = repository.getAllContenedores()

}