package com.example.recolectar_app.data.network

import com.example.recolectar_app.core.RetrofitHelper
import com.example.recolectar_app.model.DeleteRequestModel
import com.example.recolectar_app.model.UpdateRequestModel
import com.example.recolectar_app.model.camion.CamionModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CamionService {
    private val retrofit = RetrofitHelper.getRetrofit()

    suspend fun getAllCamiones() : List<CamionModel>{
        return withContext(Dispatchers.IO){
            val response = retrofit.create(FiwareApiClient::class.java).getAllCamiones()
            response.body() ?: emptyList()
        }
    }

    suspend fun getCamionById(id:String) : List<CamionModel>{
        return withContext(Dispatchers.IO){
            val response = retrofit.create(FiwareApiClient::class.java).getCamionById(id)
            print(response.body())
            response.body() ?: emptyList()
        }
    }

    suspend fun getCamionByZona(zona:String) : List<CamionModel>{
        return withContext(Dispatchers.IO){
            val response = retrofit.create(FiwareApiClient::class.java).getCamionByZona(zona)
            response.body() ?: emptyList()
        }
    }

    suspend fun postNewCamion(camionModel: CamionModel) : Boolean{
        return withContext(Dispatchers.IO){
            val response = retrofit.create(FiwareApiClient::class.java).postNewCamion(camionModel)
            response.isSuccessful
        }
    }

    suspend fun updateContenedor(updateRequestModel: UpdateRequestModel) : Boolean{
        return withContext(Dispatchers.IO){
            val response = retrofit.create(FiwareApiClient::class.java).updateEntitie(updateRequestModel)
            response.isSuccessful
        }
    }

    suspend fun deleteContenedor(deleteRequestModel: DeleteRequestModel) : Boolean{
        return withContext(Dispatchers.IO){
            val response = retrofit.create(FiwareApiClient::class.java).deleteEntitie(deleteRequestModel)
            response.isSuccessful
        }
    }
}