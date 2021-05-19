package com.example.recolectar_app.entities

class Camion(patente: String?, tipo: String?) {
    var patente: String = ""

    var tipo: String = ""


    init {
        this.patente = patente!!
        this.tipo = tipo!!
    }
}
