package com.example.recolectar_app.model.contenedor

import com.example.recolectar_app.model.contenedor.ContenedorModel
import java.util.*
import kotlin.collections.ArrayList

class UpdateContenedorRequestModel {
    var actionType : String = "append"
    var entities = ArrayList<ContenedorModel>()
    lateinit var ent : ArrayList<Any>

    fun addEntitie(entitie: ContenedorModel){
        entities.add(entitie)
    }

    fun addent(entitie : Any){
        ent.add(entitie)
    }
}

