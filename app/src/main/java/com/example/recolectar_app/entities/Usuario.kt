package com.example.recolectar_app

import java.io.Serializable

data class Usuario (
    var razonSocial:String?,
    var usuario:String,
    var email:String?,
    var zona:String?,
    var horarioEntrada:String?,
    var horarioSalida:String?,
    var esAdmin:Boolean?,
    var estaActivo:Boolean?,
    var photo:String?
) : Serializable