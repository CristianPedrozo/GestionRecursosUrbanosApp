package com.example.recolectar_app.Objetos.Contenedor

import com.example.recolectar_app.Objetos.Contenedor.Metadata

data class RefVehicle(
        val metadata: Metadata,
        val type: String = "Relationship",
        val value: String

)



