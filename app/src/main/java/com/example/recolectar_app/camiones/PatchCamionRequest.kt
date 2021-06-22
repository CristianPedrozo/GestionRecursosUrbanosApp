package com.example.recolectar_app.camiones

import com.example.recolectar_app.model.camion.CamionModel
import java.util.ArrayList

class PatchCamionObject{
    var actionType : String = "append"
    var entities = ArrayList<CamionModel>()


    fun addEntitie(entitie: CamionModel){
        entities.add(entitie)
    }
}