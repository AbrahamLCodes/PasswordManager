package com.swtizona.passwordmanager.datamodel

import java.io.Serializable

class Correo(
    @JvmField private var nomusuario: String,
    @JvmField private var nombre: String,
    @JvmField private var correo: String,
    @JvmField private var contrasenia: String,
    @JvmField private var fecha: String
) : Serializable {

    fun getNomusuario() = nomusuario
    fun getNombre() = nombre
    fun getCorreo() = correo
    fun getContrasenia() = contrasenia
    fun getFecha() = fecha
}