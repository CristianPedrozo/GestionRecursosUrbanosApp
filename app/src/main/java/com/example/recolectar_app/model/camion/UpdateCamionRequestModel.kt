package com.example.recolectar_app.model.camion

import java.util.ArrayList

class UpdateCamionRequestModel{
    var actionType : String = "append"
    var entities = ArrayList<CamionModel>()

    fun addEntitie(entitie: CamionModel){
        entities.add(entitie)
    }
}