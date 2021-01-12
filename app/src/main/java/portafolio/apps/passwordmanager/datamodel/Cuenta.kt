package portafolio.apps.passwordmanager.datamodel

class Cuenta(
    @JvmField private var correo: String,
    @JvmField private var cuenta: String,
    @JvmField private var contrasenia: String,
    @JvmField private var nickname: String,
    @JvmField private var fecha: String
) {

    fun getCorreo() = correo
    fun getCuenta() = cuenta
    fun getContrasenia() = contrasenia
    fun getNickname() = nickname
    fun getFecha() = fecha
}