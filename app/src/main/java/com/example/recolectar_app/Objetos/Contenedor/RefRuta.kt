package com.example.recolectar_app.Objetos.Contenedor

import com.example.recolectar_app.Objetos.Contenedor.Metadata

data class RefRuta(
        val metadata: Metadata,
        val type: String,
        val value: String
)