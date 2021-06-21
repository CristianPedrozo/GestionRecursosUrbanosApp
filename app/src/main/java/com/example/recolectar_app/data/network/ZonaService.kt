package com.example.recolectar_app.data.network

import com.example.recolectar_app.core.RetrofitHelper
import com.example.recolectar_app.data.network.FiwareApiClient
import com.example.recolectar_app.model.zona.ZonaModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

class ZonaService {
    private val retrofit = RetrofitHelper.getRetrofit()

    suspend fun getZonas(): List<ZonaModel> {
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(FiwareApiClient::class.java).getAllZonas()
            response.body() ?: emptyList()
        }
    }

    suspend fun postZona(jsonObject: JSONObject){
        return withContext(Dispatchers.IO){
            retrofit.create(FiwareApiClient::class.java).postZona(jsonObject)
        }
    }

    suspend fun getZonaByName(name : String) : List<ZonaModel>{
        print(name)
        return withContext(Dispatchers.IO){
            val response = retrofit.create(FiwareApiClient::class.java).getZonaByName(name)
            print(response.body())
            response.body() ?: emptyList()
        }
    }
}