package com.example.recolectar_app.entities

class User(id : String, dni : String, rol : String) {

    private var id: String = ""
    private var dni: String = ""
    private var email: String = ""
    private var estado: String = ""
    private var rol: String = ""
    private lateinit var camion: Camion

    public fun getId(): String {
        return this.id
    }

    public fun getDni(): String {
        return this.dni
    }

    public fun email(): String {
        return this.email
    }

    public fun getEstado(): String {
        return this.estado
    }

    public fun getRol(): String {
        return this.rol
    }

    public fun getCamion(): Camion {
        return this.camion
    }

    private fun setCamion(camion: Camion) {
        this.camion = camion
    }

    private fun setEstado(estado: String) {
        this.estado = estado
    }

    private fun setRol(rol: String) {
        this.rol = rol
    }

}