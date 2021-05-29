package com.example.recolectar_app.Objetos.Contenedor

import android.os.Parcelable

data class Contenedor (
    /*
        val category: Category,
        val dateLastEmptying: DateLastEmptying,
        val fillingLevel: FillingLevel,
        val  id: String,
       val location: Location,
       val nextActuationDeadline: NextActuationDeadline,
       val refDevice: RefDevice,
       val refWasteContainerIsle: RefWasteContainerIsle,
       val refWasteContainerModel: RefWasteContainerModel,
       val serialNumber: SerialNumber,
       val status: Status,
       val temperature: Temperature,
       val type: String

     */
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