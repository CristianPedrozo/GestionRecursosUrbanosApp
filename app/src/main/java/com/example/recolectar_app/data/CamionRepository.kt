package com.example.recolectar_app.data

import DeleteCamionRequestModel
import com.example.recolectar_app.data.network.CamionService
import com.example.recolectar_app.model.camion.CamionModel
import com.example.recolectar_app.model.camion.CamionProvider
import com.example.recolectar_app.model.camion.UpdateCamionRequestModel

class CamionRepository {
    private val api = CamionService()

    suspend fun getAllCamiones() : List<CamionModel>{
        val response = api.getAllCamiones()
        CamionProvider.camiones = response
        return response
    }

    suspend fun getCamionById(id: String) : List<CamionModel>{
        return api.getCamionById(id)
    }

    suspend fun getCamionByZona(zona:String): List<CamionModel>{
        return api.getCamionByZona(zona)
    }

    suspend fun postNewCamion(camion : CamionModel) : Boolean{
        return api.postNewCamion(camion)
    }

    suspend fun updateCamion(updateCamionRequestModel: UpdateCamionRequestModel) : Boolean{
        return api.updateContenedor(updateCamionRequestModel)
    }

    suspend fun deleteCamion(deleteCamionRequestModel: DeleteCamionRequestModel) : Boolean{
        return api.deleteContenedor(deleteCamionRequestModel)
    }
}