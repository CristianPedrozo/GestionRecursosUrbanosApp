package com.example.recolectar_app.camiones

/*
class Camion(var id: String, var serviceStatus:String) {
    val type : String = "Vehicle"
    var refVehicle: RefVehicle? = null
    var refEmpleado: RefEmpleado?= null
    var refZona: RefZona?= null
    var temperature: Temperature?= null

    data class RefVehicle(var value : String){
        val type: String = "Relationship"
    }
    data class RefEmpleado(var value : String){
        val type: String = "Relationship"
    }
    data class RefZona(var value : String){
        val type: String = "Relationship"
    }
    data class Temperature(var value: Int){
        val type: String = "Number"
    }
    data class ServiceStatus(
        var value: String
    )

    fun setRefVehicleValue(string: String){
        this.refVehicle = RefVehicle("vehicle:${string}")
    }
    fun setRefEmpleadoValue(string: String){
        this.refEmpleado = RefEmpleado("empleado:${string}")
    }
    fun setRefZonaValue(string: String){
        this.refZona = RefZona("zona:${string}")
    }
/*
    fun setTemperature(number: Int){
        this.temperature = Temperature("temperature:${number}")
    }
 */
    init {
        this.id = "Vehicle:${id}"
    }

}

 */
data class Camion(
    /*
    var areaServed: AreaServed,
    */
    var cargoWeight: CargoWeight,
    /*
    var category: Category,

     */
    var id: String,
    /*
    var location: Location,
    var name: Name,
    var refVehicleModel: RefVehicleModel,
    var serviceProvided: ServiceProvided,

     */
    var serviceStatus: ServiceStatus,
    /*
    var speed: Speed,
    var type: String,

     */
    var vehiclePlateIdentifier: VehiclePlateIdentifier,
    var vehicleType: VehicleType

) {
    val type : String = "Vehicle"
    data class AreaServed(
        var value: String
    )

    data class CargoWeight(
        var value: Int
    )

    data class Category(
        var value: List<String>
    )

    data class Location(
        var metadata: Metadata,
        var type: String,
        var value: Value
    ) {
        data class Metadata(
            var timestamp: Timestamp
        ) {
            data class Timestamp(
                var type: String,
                var value: String
            )
        }

        data class Value(
            var coordinates: List<Double>,
            var type: String
        )
    }

    data class Name(
        var value: String
    )

    data class RefVehicleModel(
        var type: String,
        var value: String
    )

    data class ServiceProvided(
        var value: List<String>
    )

    data class ServiceStatus(
        var value: String
    )

    data class Speed(
        var metadata: Metadata,
        var value: Int
    ) {
        data class Metadata(
            var timestamp: Timestamp
        ) {
            data class Timestamp(
                var type: String,
                var value: String
            )
        }
    }

    data class VehiclePlateIdentifier(
        var value: String
    )

    data class VehicleType(
        var value: String
    )
    init {
        this.id = "vehicle:${id}"

    }
}