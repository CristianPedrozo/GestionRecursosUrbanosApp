package com.example.recolectar_app.Objetos.Contenedor

import com.example.recolectar_app.Objetos.Contenedor.Metadata

data class RefWasteContainerIsle(
        val metadata: Metadata = Metadata(),
        val type: String="",
        val value: String=""
)