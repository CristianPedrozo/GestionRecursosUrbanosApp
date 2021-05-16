package com.example.recolectar_app.Objetos.Contenedor

data class Location(
        val metadata: Metadata = Metadata(),
        val type: String="",
        val value: Value = Value()
)