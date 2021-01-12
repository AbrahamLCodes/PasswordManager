package portafolio.apps.passwordmanager.datamodel

class Tarjeta(
    @JvmField private var nomusuario: String,
    @JvmField private var asunto: String,
    @JvmField private var ntarjeta: String,
    @JvmField private var caducidad: String,
    @JvmField private var codseg: String,
    @JvmField private var nip: String,
    @JvmField private var fecha: String
) {

    fun getNomusuario() = nomusuario
    fun getAsunto() = asunto
    fun getNtarjeta() = ntarjeta
    fun getCaducidad() = caducidad
    fun getCodseg() = codseg
    fun getNip() = nip
    fun getFecha() = fecha
}