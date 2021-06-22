package com.example.recolectar_app.model.contenedor

import java.io.Serializable

class ContenedorModel(var id: String) : Serializable {
    val type: String = "WasteContainer"
    lateinit var location: Location
    var nextActuationDeadline: NextActuationDeadline? = null
    var refRuta: RefRuta? = null
    var refVehicle: RefVehicle? = null
    var refZona: RefZona? = null
    lateinit var status: Status
    var temperature: Temperature? = null
    var dateLastEmptying: DateLastEmptying? = null
    lateinit var fillingLevel: FillingLevel
    lateinit var wasteType : WasteType

    init {
        this.id = "wastecontainer:${id}"
    }
    fun setWasteType(type : String){
        this.wasteType = WasteType(type)
    }
    fun setLocation(coords : MutableList<Double>){
        this.location = Location(Location.Value(coords))
    }
    fun setNextActuationDeadLine(date: String){
        this.nextActuationDeadline = NextActuationDeadline(date)
    }
    fun setRefRuta(ref : String){
        this.refRuta = RefRuta("ruta:$ref")
    }
    fun setRefVehicle(ref : String){
        this.refVehicle = RefVehicle("vehicle:$ref")
    }
    fun setRefZona(ref : String){
        this.refZona = RefZona("zona:$ref")
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


    data class WasteType(
        var value: String
    ){
        val type : String = "Text"
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