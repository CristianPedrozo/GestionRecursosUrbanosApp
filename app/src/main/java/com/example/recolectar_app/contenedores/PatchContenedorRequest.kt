package com.example.recolectar_app

import com.example.recolectar_app.model.contenedor.ContenedorModel
import java.util.*

class PatchContenedorObject {
    var actionType : String = "append"
    var entities = ArrayList<ContenedorModel>()


    fun addEntitie(entitie: ContenedorModel){
        entities.add(entitie)
    }
}

