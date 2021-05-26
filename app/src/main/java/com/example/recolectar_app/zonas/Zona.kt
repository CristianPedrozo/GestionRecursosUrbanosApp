package com.example.recolectar_app.zonas

data class Zona(
    val id: String,
    val refVehicle: RefVehicle,
    val type: String
) {
    data class RefVehicle(
        val type: String,
        val value: String
    )
}