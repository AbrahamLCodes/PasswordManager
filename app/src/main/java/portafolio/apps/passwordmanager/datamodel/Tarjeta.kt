package portafolio.apps.passwordmanager.datamodel

class Tarjeta(
    @JvmField private var nomusuario: String,
    @JvmField private var asunto: String,
    @JvmField private var titular: String,
    @JvmField private var ntarjeta: String,
    @JvmField private var cadm: String,
    @JvmField private var cady: String,
    @JvmField private var codseg: String,
    @JvmField private var banco: String,
    @JvmField private var nip: String,
    @JvmField private var fecha: String
) {

    fun getNomusuario() = nomusuario
    fun getAsunto() = asunto
    fun getTitular() = titular
    fun getNtarjeta() = ntarjeta
    fun getCadM() = cadm
    fun getCadY() = cady
    fun getCodseg() = codseg
    fun getBanco() = banco
    fun getNip() = nip
    fun getFecha() = fecha
}