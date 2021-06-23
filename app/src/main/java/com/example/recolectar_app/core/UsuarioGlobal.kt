package com.example.recolectar_app.core

import java.io.Serializable

object UsuarioGlobal : Serializable {
    var razonSocial:String? = null
    var usuario:String? = null
    var email:String? = null
    var zona:String? = null
    var horarioEntrada:String? = null
    var horarioSalida:String? = null
    var esAdmin:Boolean? = null
    var estaActivo:Boolean? = null
    var photo:String? = null
}