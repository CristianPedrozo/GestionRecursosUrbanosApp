package com.example.recolectar_app.data

import com.example.recolectar_app.data.network.ContenedorService
import com.example.recolectar_app.model.DeleteRequestModel
import com.example.recolectar_app.model.UpdateRequestModel
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

    suspend fun getContenedorByZona(req:String): List<ContenedorModel>{
        return api.getContenedorByZona(req)
    }

    suspend fun postNewContenedor(contenedor : ContenedorModel) : Boolean{
        return api.postNewContenedor(contenedor)
    }

    suspend fun updateContenedor(updateRequestModel: UpdateRequestModel) : Boolean{
        return api.updateContenedor(updateRequestModel)
    }

    suspend fun deleteContenedor(deleteRequestModel: DeleteRequestModel) : Boolean{
        return api.deleteContenedor(deleteRequestModel)
    }


}