package com.swtizona.passwordmanager.datamodel

import java.io.Serializable

class Contrasenia(
    @JvmField private var nomusuario: String,
    @JvmField private var asunto: String,
    @JvmField private var contrasenia: String,
    @JvmField private var fecha: String
) : Serializable {

    fun getNomusuario() = nomusuario
    fun getAsunto() = asunto
    fun getContrasenia() = contrasenia
    fun getFecha() = fecha
}