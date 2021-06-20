package com.example.recolectar_app.zonas


data class Zona(var id: String) {
    val type : String = "Zona"
    var refVehicle: RefVehicle? = null

    data class RefVehicle(var value : String){
        val type: String = "Relationship"
    }

    fun setRefVehicleValue(string: String){
        this.refVehicle = RefVehicle("vehicle:${string}")
    }

    init {
        this.id = "zona:${id}"
    }



}

