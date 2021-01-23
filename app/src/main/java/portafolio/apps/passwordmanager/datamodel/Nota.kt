package portafolio.apps.passwordmanager.datamodel

import java.io.Serializable

class Nota(
    @JvmField private var nomusuario: String,
    @JvmField private var asunto: String,
    @JvmField private var nota: String,
    @JvmField private var fecha: String
) : Serializable {

    fun getNomusuario() = nomusuario
    fun getAsunto() = asunto
    fun getNota() = nota
    fun getFecha() = fecha
}