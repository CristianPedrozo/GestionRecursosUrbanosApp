package com.example.recolectar_app.data


import com.example.recolectar_app.data.network.CamionService
import com.example.recolectar_app.model.DeleteRequestModel
import com.example.recolectar_app.model.UpdateRequestModel
import com.example.recolectar_app.model.camion.CamionModel
import com.example.recolectar_app.model.camion.CamionProvider

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

    suspend fun updateCamion(updateRequestModel: UpdateRequestModel) : Boolean{
        return api.updateContenedor(updateRequestModel)
    }

    suspend fun deleteCamion(deleteRequestModel: DeleteRequestModel) : Boolean{
        return api.deleteContenedor(deleteRequestModel)
    }
}