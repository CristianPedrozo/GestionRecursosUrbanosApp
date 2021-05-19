package com.example.recolectar_app.Objetos.Contenedor

import com.example.recolectar_app.Objetos.Contenedor.Metadata

data class SerialNumber(
        val metadata: Metadata,
        val type: String,
        val value: String
)