package com.example.recolectar_app.model

import com.example.recolectar_app.model.camion.CamionModel
import com.example.recolectar_app.model.contenedor.ContenedorModel
import com.example.recolectar_app.model.zona.ZonaModel
import java.util.ArrayList

class UpdateRequestModel {
    var actionType : String = "append"
    var entities = ArrayList<Any>()

    fun addCamion(camion: CamionModel){
        entities.add(camion)
    }
    fun addZona(zona : ZonaModel){
        entities.add(zona)
    }
    fun addContenedor(contenedor : ContenedorModel){
        entities.add(contenedor)
    }

}