package com.example.recolectar_app.Objetos.Contenedor

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


