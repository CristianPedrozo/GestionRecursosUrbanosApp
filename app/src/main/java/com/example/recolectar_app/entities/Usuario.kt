package com.example.recolectar_app

import java.io.Serializable

data class Usuario (
    var razonSocial:String?,
    var email:String,
    var distrito:String?,
    var jefe:String?,
    var horarioEntrada:String?,
    var horarioSalida:String?,
    var esAdmin:Boolean?,
    var photo:String?
) : Serializable