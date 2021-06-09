package com.example.recolectar_app

import com.example.recolectar_app.contenedores.Contenedor
import java.util.*

class PatchContenedorObject {
    var actionType : String = "append"
    var entities = ArrayList<Contenedor>()


    fun addEntitie(entitie: Contenedor){
        entities.add(entitie)
    }
}

