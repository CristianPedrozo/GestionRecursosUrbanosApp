package com.example.recolectar_app.model.contenedor

import com.example.recolectar_app.model.contenedor.ContenedorModel
import java.util.*

class UpdateContenedorRequestModel {
    var actionType : String = "append"
    var entities = ArrayList<ContenedorModel>()


    fun addEntitie(entitie: ContenedorModel){
        entities.add(entitie)
    }
}

