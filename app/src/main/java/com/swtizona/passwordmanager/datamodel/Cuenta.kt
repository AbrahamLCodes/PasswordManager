package com.swtizona.passwordmanager.datamodel

import java.io.Serializable

class Cuenta(
    @JvmField private var nomusuario: String,
    @JvmField private var correo: String,
    @JvmField private var website: String,
    @JvmField private var contrasenia: String,
    @JvmField private var categoria: String,
    @JvmField private var nickname: String,
    @JvmField private var fecha: String
) : Serializable {
    fun getNomUsuario() = nomusuario
    fun getCorreo() = correo
    fun getWebsite() = website
    fun getContrasenia() = contrasenia
    fun getCategoria() = categoria
    fun getNickname() = nickname
    fun getFecha() = fecha
}