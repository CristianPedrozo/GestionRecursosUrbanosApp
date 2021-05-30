package com.example.recolectar_app.Objetos.Contenedor

import com.example.recolectar_app.Objetos.Contenedor.Metadata

<<<<<<< HEAD:app/src/main/java/com/example/recolectar_app/Objetos/Contenedor/RefVehicle.kt
data class RefVehicle(
        val metadata: Metadata,
        val type: String = "Relationship",
        val value: String

)



=======
data class RefDevice(
        val metadata: Metadata = Metadata(),
        val type: String="",
        val value: List<String> = ArrayList()
)
>>>>>>> 8de08a3420b0c49760cfb8c14bfbc0968506e9c2:app/src/main/java/com/example/recolectar_app/Objetos/Contenedor/RefDevice.kt
