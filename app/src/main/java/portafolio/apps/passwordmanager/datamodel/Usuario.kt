package portafolio.apps.passwordmanager.datamodel

import java.io.Serializable

class Usuario(
    @JvmField private var nombre: String,
    @JvmField private var contrasenia: String,
    @JvmField private var checked: Int
) : Serializable{
    fun getNombre() = nombre
    fun getContrasenia() = contrasenia
    fun getChecked() = checked
}