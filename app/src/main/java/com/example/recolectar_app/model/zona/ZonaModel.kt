package com.example.recolectar_app.model.zona

import com.example.recolectar_app.model.contenedor.ContenedorModel
import java.io.Serializable


data class ZonaModel(var id: String) : Serializable {
    val type : String = "Zona"
    lateinit var refVehicle: RefVehicle
    lateinit var nombre : Nombre
    lateinit var contenedores : Contenedores
    var empleado : Empleado? = null

    init {
        this.id = "zona:${id}"
    }

    fun setNombre(nombre : String){
        this.nombre = Nombre(nombre)
    }

    fun setContenedores(arr : ArrayList<ContenedorModel>){
        this.contenedores = Contenedores(arr)
    }


    fun setRefVehicleValue(string: String){
        this.refVehicle = RefVehicle("vehicle:${string}")
    }

    fun setEmpleado(email : String){
        this.empleado = Empleado(email)
    }

    data class Empleado(var value : String){
        val type : String = "Relationship"
    }

    data class Nombre(var value : String){
        val type : String = "Text"
    }

    data class RefVehicle(var value : String){
        val type: String = "Relationship"
    }

    data class Contenedores(val value : ArrayList<ContenedorModel>){
        val type: String = "List"

        fun addContenedor(contenedorModel : ContenedorModel){
            value.add(contenedorModel)
        }
    }

    data class ZonaResponse(val code: Int?, val message: String?)



}

