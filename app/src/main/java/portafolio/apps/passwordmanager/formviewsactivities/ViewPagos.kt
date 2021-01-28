package portafolio.apps.passwordmanager.formviewsactivities


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import portafolio.apps.passwordmanager.R
import portafolio.apps.passwordmanager.datamodel.Contrasenia
import portafolio.apps.passwordmanager.datamodel.Tarjeta
import portafolio.apps.passwordmanager.formactivities.FormContrasenia
import portafolio.apps.passwordmanager.formactivities.FormPagos

class ViewPagos :
    AppCompatActivity(),
    View.OnClickListener {

    private lateinit var asunto: TextView
    private lateinit var asuntoBtn: ImageButton
    private lateinit var titular: TextView
    private lateinit var titularBtn: ImageButton
    private lateinit var ntarjeta: TextView
    private lateinit var ntarjetaBtn: ImageButton
    private lateinit var codseg: TextView
    private lateinit var codsegBtn: ImageButton
    private lateinit var fecha: TextView
    private lateinit var fechaBtn: ImageButton
    private lateinit var banco: TextView
    private lateinit var bancoBtn: ImageButton
    private lateinit var nip: TextView
    private lateinit var nipBtn: ImageButton
    private lateinit var back: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_pagos)
        initComponents()
    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {
                R.id.back -> {
                    onBackPressed()
                }
                R.id.editar -> {
                    if (intent.getSerializableExtra("tarjetaupdated") != null) {
                        val t = intent.getSerializableExtra("tarjetaupdated") as? Tarjeta
                        startActivity(Intent(this, FormPagos::class.java).apply {
                            putExtra("tarjetaupdated", t)
                            putExtra("username", t!!.getNomusuario())
                        })
                    } else {
                        val t = intent.getSerializableExtra("tarjeta") as? Tarjeta
                        startActivity(Intent(this, FormPagos::class.java).apply {
                            putExtra("tarjeta", t)
                            putExtra("username", t!!.getNomusuario())
                        })
                    }
                    finish()
                }
            }
        }
    }

    private fun setComponents(t: Tarjeta) {
        asunto.text = t.getAsunto()
        titular.text = t.getTitular()
        ntarjeta.text = t.getNtarjeta()
        codseg.text = t.getCodseg()
        fecha.text = t.getCadM() + " / " + t.getCadY()
        banco.text = t.getBanco()
        nip.text = t.getNip()
    }

    private fun initComponents() {
        asunto = findViewById(R.id.asunto)
        asuntoBtn = findViewById(R.id.asuntoBtn)
        titular = findViewById(R.id.titular)
        titularBtn = findViewById(R.id.titularBtn)
        ntarjeta = findViewById(R.id.ntarjeta)
        ntarjetaBtn = findViewById(R.id.ntarjetaBtn)
        codseg = findViewById(R.id.codseg)
        codsegBtn = findViewById(R.id.codsegBtn)
        fecha = findViewById(R.id.fecha)
        fechaBtn = findViewById(R.id.fechaBtn)
        banco = findViewById(R.id.banco)
        bancoBtn = findViewById(R.id.bancoBtn)
        nip = findViewById(R.id.nip)
        nipBtn = findViewById(R.id.nipBtn)
        back = findViewById(R.id.back)
        val editar = findViewById<ImageButton>(R.id.editar)

        val tarjeta = intent.getSerializableExtra("tarjeta") as? Tarjeta
        if (tarjeta != null) {
            asunto.text = tarjeta.getAsunto()
            titular.text = tarjeta.getTitular()
            ntarjeta.text = tarjeta.getNtarjeta()
            codseg.text = tarjeta.getCodseg()
            fecha.text = tarjeta.getCadM() + " / " + tarjeta.getCadY()
            banco.text = tarjeta.getBanco()
            nip.text = tarjeta.getNip()
        }

        val tUpdated = intent.getSerializableExtra("tarjetaupdated") as? Tarjeta
        if (tUpdated != null) {
            setComponents(tUpdated)
        }

        asuntoBtn.setOnClickListener(this)
        titularBtn.setOnClickListener(this)
        ntarjetaBtn.setOnClickListener(this)
        codsegBtn.setOnClickListener(this)
        fechaBtn.setOnClickListener(this)
        fechaBtn.setOnClickListener(this)
        bancoBtn.setOnClickListener(this)
        nipBtn.setOnClickListener(this)
        back.setOnClickListener(this)
        editar.setOnClickListener(this)
    }
}