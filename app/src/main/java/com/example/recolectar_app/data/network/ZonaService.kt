package com.example.recolectar_app.data.network

import com.example.recolectar_app.core.RetrofitHelper
import com.example.recolectar_app.model.zona.DeleteZonaRequestModel
import com.example.recolectar_app.model.zona.UpdateZonaRequestModel
import com.example.recolectar_app.model.zona.ZonaModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ZonaService {
    private val retrofit = RetrofitHelper.getRetrofit()

    suspend fun getZonas(): List<ZonaModel> {
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(FiwareApiClient::class.java).getAllZonas()
            response.body() ?: emptyList()
        }
    }

    suspend fun postNewZona(zona: ZonaModel) : Boolean {
        return withContext(Dispatchers.IO){
            val unit = retrofit.create(FiwareApiClient::class.java).postNewZona(zona)
            unit.isSuccessful
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

    suspend fun deleteZona(deleteZonaRequestModel: DeleteZonaRequestModel) : Boolean{
        return withContext(Dispatchers.IO){
            val unit = retrofit.create(FiwareApiClient::class.java).deleteZona(deleteZonaRequestModel)
            unit.isSuccessful
        }
    }

    suspend fun updateZona(updateZonaRequestModel: UpdateZonaRequestModel) : Boolean {
        return withContext(Dispatchers.IO){
            val unit = retrofit.create(FiwareApiClient::class.java).updateZona(updateZonaRequestModel)
            unit.isSuccessful
        }
    }


}