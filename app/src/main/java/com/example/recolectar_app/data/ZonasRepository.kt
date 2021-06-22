package com.example.recolectar_app.data
import com.example.recolectar_app.model.zona.ZonaModel
import com.example.recolectar_app.model.zona.ZonaProvider
import com.example.recolectar_app.data.network.ZonaService
import com.google.gson.JsonObject
import org.json.JSONObject

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

    suspend fun crearZona(zona: ZonaModel){
        return api.crearZona(zona)
    }


}