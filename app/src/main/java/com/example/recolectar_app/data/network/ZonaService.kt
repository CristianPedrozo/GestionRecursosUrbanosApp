package com.example.recolectar_app.data.network

import com.example.recolectar_app.core.RetrofitHelper
import com.example.recolectar_app.model.zona.ZonaModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ZonaService {
    private val retrofit = RetrofitHelper.getRetrofit()

    suspend fun getZonas(): List<ZonaModel> {
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(FiwareApiClient::class.java).getAllZonas()
            response.body() ?: emptyList()
        }
    }

    suspend fun crearZona(zona: ZonaModel) {
        return withContext(Dispatchers.IO){
           retrofit.create(FiwareApiClient::class.java).postNewZona(zona)
        }
    }

    suspend fun getZonaByName(name : String) : List<ZonaModel>{
        return withContext(Dispatchers.IO){
            val response = retrofit.create(FiwareApiClient::class.java).getZonaByName(name)
            print(response.body())
            response.body() ?: emptyList()
        }
    }

    suspend fun getZonaById(id : String) : List<ZonaModel>{
        return withContext(Dispatchers.IO){
            val response = retrofit.create(FiwareApiClient::class.java).getZonaById(id)
            response.body() ?: emptyList()
        }
    }

//    suspend fun getContenedoresPorZona() : List<Contenedor>{
//        return withContext(Dispatchers.IO){
//            val response = retrofit.create(FiwareApiClient::class.java).
//        }
//    }
}