package com.example.recolectar_app.model.zona

import com.example.recolectar_app.model.zona.ZonaModel
import java.util.*

class UpdateZonaRequestModel {
    var actionType : String = "append"
    var entities = ArrayList<ZonaModel>()


    fun addEntitie(entitie: ZonaModel){
        entities.add(entitie)
    }
}

