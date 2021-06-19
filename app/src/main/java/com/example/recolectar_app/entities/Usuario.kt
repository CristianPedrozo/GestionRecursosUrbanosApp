package com.example.recolectar_app

data class Usuario (
    var razonSocial:String?,
    var usuario:String,
    var email:String?,
    var zona:String?,
    var horarioEntrada:String?,
    var horarioSalida:String?,
    var esAdmin:Boolean?,
    var photo:String?
)