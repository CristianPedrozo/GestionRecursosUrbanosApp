package com.example.recolectar_app.data

import com.example.recolectar_app.data.network.ContenedorService
import com.example.recolectar_app.data.network.ZonaService
import com.example.recolectar_app.model.contenedor.ContenedorModel
import com.example.recolectar_app.model.contenedor.ContenedorProvider
import com.example.recolectar_app.model.contenedor.DeleteContenedorRequestModel
import com.example.recolectar_app.model.contenedor.UpdateContenedorRequestModel

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

    suspend fun postNewContenedor(contenedor : ContenedorModel) : Boolean{
        return api.postNewContenedor(contenedor)
    }

    suspend fun updateContenedor(updateContenedorRequestModel: UpdateContenedorRequestModel) : Boolean{
        return api.updateContenedor(updateContenedorRequestModel)
    }

    suspend fun deleteContenedor(deleteContenedorRequestModel: DeleteContenedorRequestModel) : Boolean{
        return api.deleteContenedor(deleteContenedorRequestModel)
    }


}