package com.example.recolectar_app.data.network

import com.example.recolectar_app.core.RetrofitHelper
import com.example.recolectar_app.model.DeleteRequestModel
import com.example.recolectar_app.model.UpdateRequestModel
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
            val response = retrofit.create(FiwareApiClient::class.java).postNewZona(zona)
            response.isSuccessful
        }
    }

    suspend fun getZonaByName(name : String) : List<ZonaModel>{
        return withContext(Dispatchers.IO){
            val response = retrofit.create(FiwareApiClient::class.java).getZonaByName(name)
            response.body() ?: emptyList()
        }
    }

    suspend fun getZonaById(id : String) : List<ZonaModel>{
        return withContext(Dispatchers.IO){
            val response = retrofit.create(FiwareApiClient::class.java).getZonaById(id)
            response.body() ?: emptyList()
        }
    }

    suspend fun deleteZona(deleteRequestModel: DeleteRequestModel) : Boolean{
        return withContext(Dispatchers.IO){
            val response = retrofit.create(FiwareApiClient::class.java).deleteEntitie(deleteRequestModel)
            response.isSuccessful
        }
    }

    suspend fun updateZona(updateRequestModel: UpdateRequestModel) : Boolean {
        return withContext(Dispatchers.IO){
            val response = retrofit.create(FiwareApiClient::class.java).updateEntitie(updateRequestModel)
            response.isSuccessful
        }
    }


}