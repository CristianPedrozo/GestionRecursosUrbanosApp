package com.example.recolectar_app.data.network

import com.example.recolectar_app.model.contenedor.ContenedorModel
import com.example.recolectar_app.core.RetrofitHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ContenedorService {
    private val retrofit = RetrofitHelper.getRetrofit()

    suspend fun getAllContenedores() : List<ContenedorModel>{
        return withContext(Dispatchers.IO){
            val response = retrofit.create(FiwareApiClient::class.java).getAllContenedores()
            response.body() ?: emptyList()
        }
    }

    suspend fun getContenedorById(id:String) : List<ContenedorModel>{
        return withContext(Dispatchers.IO){
            val response = retrofit.create(FiwareApiClient::class.java).getContenedorById(id)
            print(response.body())
            response.body() ?: emptyList()
        }
    }

    suspend fun getContenedorByZona(zona:String) : List<ContenedorModel>{
        return withContext(Dispatchers.IO){
            val response = retrofit.create(FiwareApiClient::class.java).getContenedorByZona(zona)
            response.body() ?: emptyList()
        }
    }
}