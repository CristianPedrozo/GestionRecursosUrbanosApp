package com.example.recolectar_app.data

import com.example.recolectar_app.data.network.ContenedorService
import com.example.recolectar_app.data.network.ZonaService
import com.example.recolectar_app.model.contenedor.ContenedorModel
import com.example.recolectar_app.model.contenedor.ContenedorProvider

class ContenedoresRepository {
    private val api = ContenedorService()

    suspend fun getAllContenedores() : List<ContenedorModel>{
        val response = api.getAllContenedores()
        ContenedorProvider.contenedores = response
        return response
    }

    suspend fun getContenedorById(id: String) : List<ContenedorModel>{
        return api.getContenedorById(id)
    }

    suspend fun getContenedorByZona(zona:String): List<ContenedorModel>{
        return api.getContenedorByZona(zona)
    }
}