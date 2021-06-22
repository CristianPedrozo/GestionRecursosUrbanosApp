package com.example.recolectar_app.model.zona
import com.example.recolectar_app.model.zona.ZonaModel
import java.util.ArrayList

class DeleteZonaRequestModel {
    var actionType : String = "delete"
    var entities = ArrayList<ZonaModel>()

    fun addEntitie(entitie: ZonaModel){
        entities.add(entitie)
    }
}