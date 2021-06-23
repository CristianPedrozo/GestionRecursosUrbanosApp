package com.example.recolectar_app.data
import com.example.recolectar_app.model.zona.ZonaModel
import com.example.recolectar_app.model.zona.ZonaProvider
import com.example.recolectar_app.data.network.ZonaService
import com.example.recolectar_app.model.DeleteRequestModel
import com.example.recolectar_app.model.UpdateRequestModel

class ZonasRepository {
    private val api = ZonaService()

    suspend fun getAllZonas():List<ZonaModel>{
        val response = api.getZonas()
        ZonaProvider.zonas = response
        return response
    }

    suspend fun getZonaByName(name : String): List<ZonaModel>{
        print(name)
        return api.getZonaByName(name)
    }

    suspend fun getZonaById(id:String): List<ZonaModel>{
        return api.getZonaById(id)
    }

    suspend fun postNewZona(zona: ZonaModel) : Boolean{
        return api.postNewZona(zona)
    }

    suspend fun deleteZona(deleteRequestModel: DeleteRequestModel) : Boolean{
        return api.deleteZona(deleteRequestModel)
    }

    suspend fun updateZona(updateRequestModel: UpdateRequestModel) : Boolean {
        return api.updateZona(updateRequestModel)
    }


}