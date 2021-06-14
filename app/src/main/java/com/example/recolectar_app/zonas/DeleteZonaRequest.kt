package com.example.recolectar_app.zonas

import java.util.*

class DeleteZonaRequest {
    var actionType : String = "delete"
    var entities = ArrayList<Zona>()

    fun addEntitie(entitie: Zona){
        entities.add(entitie)
    }
}

