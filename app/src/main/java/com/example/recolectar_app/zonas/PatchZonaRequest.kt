package com.example.recolectar_app

import com.example.recolectar_app.zonas.Zona
import java.util.*

class PatchRequestObject {
    var actionType : String = "append"
    var entities = ArrayList<Zona>()


    fun addEntitie(entitie: Zona){
        entities.add(entitie)
    }
}

