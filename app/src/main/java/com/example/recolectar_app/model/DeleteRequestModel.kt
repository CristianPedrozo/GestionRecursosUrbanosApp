package com.example.recolectar_app.model

import com.example.recolectar_app.model.camion.CamionModel
import com.example.recolectar_app.model.contenedor.ContenedorModel
import com.example.recolectar_app.model.zona.ZonaModel
import java.util.ArrayList

class DeleteRequestModel {
    var actionType : String = "delete"
    var entities = ArrayList<Any>()

    fun addCamion(entitie: CamionModel){
        entities.add(entitie)
    }
    fun addContenedor(entitie: ContenedorModel){
        entities.add(entitie)
    }
    fun addZona(entitie: ZonaModel){
        entities.add(entitie)
    }
}