package com.example.recolectar_app.Objetos.Contenedor

data class FillingLevel(
        val metadata: Metadata = Metadata(),
        val type: String="",
        val value: Double=0.0
)