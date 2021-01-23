package portafolio.apps.passwordmanager.datamodel

import java.io.Serializable

class Cuenta(
    @JvmField private var nomusuario: String,
    @JvmField private var correo: String,
    @JvmField private var cuenta: String,
    @JvmField private var contrasenia: String,
    @JvmField private var categoria: String,
    @JvmField private var nickname: String,
    @JvmField private var website: String,
    @JvmField private var fecha: String
) : Serializable{
    fun getNomUsuario() = nomusuario
    fun getCorreo() = correo
    fun getCuenta() = cuenta
    fun getContrasenia() = contrasenia
    fun getCategoria() = categoria
    fun getNickname() = nickname
    fun getWebsite() = website
    fun getFecha() = fecha
}