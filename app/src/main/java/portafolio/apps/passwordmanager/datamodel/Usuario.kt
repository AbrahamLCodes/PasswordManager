package portafolio.apps.passwordmanager.datamodel

import java.io.Serializable

class Usuario(
    @JvmField private var nombre: String,
    @JvmField private var contrasenia: String
) : Serializable{
    fun getNombre() = nombre
    fun getContrasenia() = contrasenia
}