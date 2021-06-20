package com.example.recolectar_app.camiones

import com.example.recolectar_app.contenedores.Contenedor
import java.util.ArrayList

class PatchCamionObject{
    var actionType : String = "append"
    var entities = ArrayList<Camion>()


    fun addEntitie(entitie: Camion){
        entities.add(entitie)
    }
}