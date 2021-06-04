package com.example.recolectar_app.entities

data class camion2(
    var areaServed: AreaServed,
    var cargoWeight: CargoWeight,
    var category: Category,
    var id: String,
    var location: Location,
    var name: Name,
    var refVehicleModel: RefVehicleModel,
    var serviceProvided: ServiceProvided,
    var serviceStatus: ServiceStatus,
    var speed: Speed,
    var type: String,
    var vehiclePlateIdentifier: VehiclePlateIdentifier,
    var vehicleType: VehicleType
) {
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
}