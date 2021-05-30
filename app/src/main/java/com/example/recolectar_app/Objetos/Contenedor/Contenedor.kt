package com.example.recolectar_app.Objetos.Contenedor

<<<<<<< HEAD
data class Contenedor (
        val id: String,
        val dateLastEmptying: DateLastEmptying,
        val fillingLevel: FillingLevel,
        val location: Location,
        val nextActuationDeadline: NextActuationDeadline,
        val refRuta: RefRuta,
        val refVehicle: RefVehicle,
        val refZona: RefZona,
        val status: Status,
        val temperature: Temperature,
        val type: String

)
=======
data class Contenedor(
        val category: Category = Category(),
        val dateLastEmptying: DateLastEmptying = DateLastEmptying(),
        val fillingLevel: FillingLevel = FillingLevel(),
        val id: String = "",
        val location: Location = Location(),
        val nextActuationDeadline: NextActuationDeadline = NextActuationDeadline(),
        val refDevice: RefDevice = RefDevice(),
        val refWasteContainerIsle: RefWasteContainerIsle = RefWasteContainerIsle(),
        val refWasteContainerModel: RefWasteContainerModel = RefWasteContainerModel(),
        val serialNumber: SerialNumber = SerialNumber(),
        val status: Status = Status(),
        val temperature: Temperature = Temperature(),
        val type: String = "",

        ) {
}


>>>>>>> 8de08a3420b0c49760cfb8c14bfbc0968506e9c2
