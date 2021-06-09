package com.example.recolectar_app.contenedores

import java.util.*

data class Contenedor(var id: String) {
    val type: String = "WasteContainer"
    lateinit var location: Location
    lateinit var nextActuationDeadline: NextActuationDeadline
    lateinit var refRuta: RefRuta
    lateinit var refVehicle: RefVehicle
    lateinit var refZona: RefZona
    lateinit var status: Status
    lateinit var temperature: Temperature
    lateinit var dateLastEmptying: DateLastEmptying
    lateinit var fillingLevel: FillingLevel

    init {
        this.id = "wastecontainer:${id}"
    }
    fun setLocation(coords : MutableList<Double>){
        this.location = Location(Location.Value(coords))
    }
    fun setNextActuationDeadLine(date: String){
        this.nextActuationDeadline = NextActuationDeadline(date)
    }
    fun setRefRuta(ref : String){
        this.refRuta = RefRuta(ref)
    }
    fun setRefVehicle(ref : String){
        this.refVehicle = RefVehicle(ref)
    }
    fun setRefZona(ref : String){
        this.refZona = RefZona(ref)
    }
    fun setStatus(status : String){
        this.status = Status(status)
    }
    fun setTemperature(temp : Double){
        this.temperature = Temperature(temp)
    }
    fun setDateLastEmptying(date : String){
        this.dateLastEmptying = DateLastEmptying(date)
    }
    fun setFillingLevel(lvl : Double){
        this.fillingLevel = FillingLevel(lvl)
    }


    data class DateLastEmptying(
        var value: String
    ){
        val type: String = "DateTime"
    }

    data class FillingLevel(
        var value: Double
    ){
        val type: String = "Number"
    }

    data class Location(
        var value: Value
    ) {
        val type: String = "geo:json"
        data class Value(
            var coordinates: MutableList<Double>,
        ){
            val type: String = "Point"
        }
    }

    data class NextActuationDeadline(
        var value: String
    ){
        val type: String = "DateTime"
    }

    data class RefRuta(
        var value: String
    ){
        val type: String = "Relationship"
    }

    data class RefVehicle(
        var value: String
    ){
        val type: String = "Relationship"
    }

    data class RefZona(
        var value: String
    ){
        val type: String = "Relationship"
    }

    data class Status(
        var value: String
    ){
        val type: String = "Text"
    }

    data class Temperature(
        var value: Double
    ){
        val type: String = "Number"
    }
}