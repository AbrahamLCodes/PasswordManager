package portafolio.apps.passwordmanager

class Usuario(@JvmField private var nombre: String, @JvmField private var contrasenia: String) {
    fun getNombre() = nombre
    fun getContrasenia() = contrasenia
}