package com.example.recolectar_app.Objetos.Contenedor

import com.example.recolectar_app.Objetos.Contenedor.Metadata

data class Temperature(
        val metadata: Metadata = Metadata(),
        val type: String="",
        val value: Int=0
)