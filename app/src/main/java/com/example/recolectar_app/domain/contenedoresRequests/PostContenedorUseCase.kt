package com.example.recolectar_app.domain.contenedoresRequests

import com.example.recolectar_app.data.ContenedoresRepository
import com.example.recolectar_app.model.contenedor.ContenedorModel

class PostContenedorUseCase {
    private val repository = ContenedoresRepository()
    suspend operator fun invoke(contenedor:ContenedorModel) : Boolean = repository.postNewContenedor(contenedor)

}