package com.example.recolectar_app.data.network

import DeleteCamionRequestModel
import com.example.recolectar_app.core.RetrofitHelper
import com.example.recolectar_app.model.camion.CamionModel
import com.example.recolectar_app.model.camion.UpdateCamionRequestModel
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

    suspend fun updateContenedor(updateCamionRequestModel: UpdateCamionRequestModel) : Boolean{
        return withContext(Dispatchers.IO){
            val response = retrofit.create(FiwareApiClient::class.java).updateCamion(updateCamionRequestModel)
            response.isSuccessful
        }
    }

    suspend fun deleteContenedor(deleteCamionRequestModel: DeleteCamionRequestModel) : Boolean{
        return withContext(Dispatchers.IO){
            val response = retrofit.create(FiwareApiClient::class.java).deleteCamion(deleteCamionRequestModel)
            response.isSuccessful
        }
    }
}