package com.example.recolectar_app.Objetos.Contenedor

<<<<<<< HEAD
import com.example.recolectar_app.Objetos.Contenedor.Metadata

data class RefVehicle(
        val type: String = "Relationship",
        val value: String
)


=======
data class RefVehicle(
    val metadata: Metadata,
    val type: String,
    val value: String
)
>>>>>>> 314e127a175ffbbb61e74fd8b6ae416743262c72
