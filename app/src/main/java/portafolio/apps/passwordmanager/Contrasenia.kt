package portafolio.apps.passwordmanager

class Contrasenia(
        @JvmField private var asunto: String,
        @JvmField private var cuenta: String, contra: String,
        @JvmField private var nusuario: String,
        @JvmField private var link: String) {

    fun getAsunto() = asunto
    fun getCuenta() = cuenta
    fun nusuario() = nusuario
    fun link() = link
}