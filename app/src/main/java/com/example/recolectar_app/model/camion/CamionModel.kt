package com.example.recolectar_app.model.camion

import java.io.Serializable

data class CamionModel(var id: String) : Serializable{
    val type : String = "Vehicle"
    var cargoWeight: CargoWeight? = null
    var serviceStatus: ServiceStatus? = null
    var vehiclePlateIdentifier: VehiclePlateIdentifier? = null
    var vehicleType: VehicleType? = null
    var fillingLevel: FillingLevel? = null

    init {
        this.id = "vehicle:${id}"
    }

    fun setCargoWeight(cargo: Double){
        this.cargoWeight = CargoWeight(cargo)
    }
    fun setServiceStatus(status : String){
        this.serviceStatus = ServiceStatus(status)
    }
    fun setVehiclePlateIdentifier(plate : String){
        this.vehiclePlateIdentifier = VehiclePlateIdentifier(plate)
    }
    fun setVehicleType(type : String){
        this.vehicleType = VehicleType(type)
    }
    fun setFillingLevel(level : Double){
        this.fillingLevel = FillingLevel(level)
    }

    data class CargoWeight(
        var value: Double
    ){
        val type = "Property"
    }
    data class ServiceStatus(
        var value: String
    ){
        val type = "Text"
    }

    data class VehiclePlateIdentifier(
        var value: String
    ){
        val type = "Text"
    }

    data class VehicleType(
        var value: String
    ){
        val type = "Text"
    }
    data class FillingLevel(
        var value: Double
    ){
        val type = "Property"
    }

//    data class Location(
//        var metadata: Metadata,
//        var type: String,
//        var value: Value
//    ) {
//        data class Metadata(
//            var timestamp: Timestamp
//        ) {
//            data class Timestamp(
//                var type: String,
//                var value: String
//            )
//        }

//        data class Value(
//            var coordinates: List<Double>,
//            var type: String
//        )
//    }

}