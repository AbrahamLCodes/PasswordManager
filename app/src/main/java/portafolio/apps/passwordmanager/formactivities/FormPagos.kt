package portafolio.apps.passwordmanager.formactivities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.textfield.TextInputLayout
import portafolio.apps.passwordmanager.R
import portafolio.apps.passwordmanager.database.DBController
import portafolio.apps.passwordmanager.datamodel.Contrasenia
import portafolio.apps.passwordmanager.datamodel.Tarjeta
import portafolio.apps.passwordmanager.formviewsactivities.ViewPagos
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class FormPagos :
    AppCompatActivity(),
    View.OnClickListener {

    private lateinit var asunto: EditText
    private lateinit var titular: EditText
    private lateinit var ntarjeta: EditText
    private lateinit var mesDrop: TextInputLayout
    private lateinit var anio: EditText
    private lateinit var codseg: EditText
    private lateinit var banco: EditText
    private lateinit var nip: EditText
    private lateinit var guardarBtn: Button
    private var insert = false
    private val meses = listOf(
        "01",
        "02",
        "03",
        "04",
        "05",
        "06",
        "07",
        "08",
        "09",
        "10",
        "11",
        "12",
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_pagos)
        initComponents()
    }

    override fun onBackPressed() {
        if (insert) {
            super.onBackPressed()
        } else {
            val ta = intent.getSerializableExtra("tarjeta") as? Tarjeta
            val taUpdated = intent.getSerializableExtra("tarjetaupdated") as? Tarjeta
            if (ta == null) {
                goToView(taUpdated!!)
            } else if (taUpdated == null) {
                goToView(ta)
            }
        }
    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {
                R.id.guardarBtn -> {
                    if (checkFields()) {
                        if (insert) {
                            save()
                        } else {
                            update()
                        }
                    }
                }
                R.id.cancelarBtn -> {
                    onBackPressed()
                }
            }
        }
    }

    private fun update() {
        val db = DBController(applicationContext)
        val ta = intent.getSerializableExtra("tarjeta") as? Tarjeta
        val taUpdated = intent.getSerializableExtra("tarjetaupdated") as? Tarjeta
        var good = true

        try {
            if (ta == null) {
                db.updateTarjeta(
                    taUpdated!!.getNomusuario(),
                    taUpdated.getAsunto(),
                    asunto.text.toString(),
                    titular.text.toString(),
                    ntarjeta.text.toString(),
                    mesDrop.editText!!.text.toString(),
                    anio.text.toString(),
                    codseg.text.toString(),
                    banco.text.toString(),
                    nip.text.toString(),
                )
            } else if (taUpdated == null) {
                db.updateTarjeta(
                    ta.getNomusuario(),
                    ta.getAsunto(),
                    asunto.text.toString(),
                    titular.text.toString(),
                    ntarjeta.text.toString(),
                    mesDrop.editText!!.text.toString(),
                    anio.text.toString(),
                    codseg.text.toString(),
                    banco.text.toString(),
                    nip.text.toString(),
                )
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            good = false
        }

        if (good) {
            Toast.makeText(
                applicationContext,
                "La tarjeta ha sido actualizada correctamente",
                Toast.LENGTH_SHORT
            ).show()
            if (ta == null) {
                goToView(taUpdated!!)
            } else if (taUpdated == null) {
                goToView(ta)
            }
        }
    }

    private fun goToView(t: Tarjeta) {
        val intent = Intent(this, ViewPagos::class.java)
        intent.apply {
            putExtra(
                "tarjetaupdated", Tarjeta(
                    t.getNomusuario(),
                    asunto.text.toString(),
                    titular.text.toString(),
                    ntarjeta.text.toString(),
                    mesDrop.editText!!.text.toString(),
                    anio.text.toString(),
                    codseg.text.toString(),
                    banco.text.toString(),
                    nip.text.toString(),
                    t.getFecha()
                )
            )
        }
        startActivity(intent)
        finish()
    }

    private fun save() {
        val db = DBController(applicationContext)
        var correcto = true
        try {
            db.insertTarjeta(
                intent.getStringExtra("username")!!,
                asunto!!.text.toString(),
                titular!!.text.toString(),
                ntarjeta!!.text.toString(),
                mesDrop!!.editText!!.text.toString(),
                anio!!.text.toString(),
                codseg!!.text.toString(),
                banco!!.text.toString(),
                nip!!.text.toString(),
                getStringDate()
            )
        } catch (ex: Exception) {
            ex.printStackTrace()
            Toast.makeText(
                applicationContext,
                "Este asunto ya ha sido registrado anteriormente",
                Toast.LENGTH_SHORT
            ).show()
            correcto = false
        }

        if (correcto) {
            Toast.makeText(
                applicationContext,
                "Tarjeta de '" + asunto!!.text.toString() + "' guardada exitosamente",
                Toast.LENGTH_LONG
            ).show()
            db.close()
            finish()
        }
    }

    private fun checkFields(): Boolean {
        if (asunto!!.text.toString().equals("")) {
            Toast.makeText(
                applicationContext,
                "Introduce un asunto",
                Toast.LENGTH_SHORT
            ).show()
            return false
        } else if (titular!!.text.toString().equals("")) {
            Toast.makeText(
                applicationContext,
                "Introduce el titular",
                Toast.LENGTH_SHORT
            ).show()
            return false
        } else if (ntarjeta!!.text.toString().equals("")) {
            Toast.makeText(
                applicationContext,
                "Introduce el número de tarjeta",
                Toast.LENGTH_SHORT
            ).show()
            return false
        } else if (codseg!!.text.toString().equals("")) {
            Toast.makeText(
                applicationContext,
                "Introduce el código de seguridad",
                Toast.LENGTH_SHORT
            ).show()
            return false
        } else if (banco!!.text.toString().equals("")) {
            Toast.makeText(
                applicationContext,
                "Introduce el banco",
                Toast.LENGTH_SHORT
            ).show()
            return false
        } else if (nip!!.text.toString().equals("")) {
            Toast.makeText(
                applicationContext,
                "Introduce el nip del Cajero ATM",
                Toast.LENGTH_SHORT
            ).show()
            return false
        } else if (mesDrop!!.editText!!.text.toString().equals("")) {
            Toast.makeText(
                applicationContext,
                "Introduce el mes de vencimiento",
                Toast.LENGTH_SHORT
            ).show()
            return false
        } else if (anio!!.text.toString().equals("")) {
            Toast.makeText(
                applicationContext,
                "Introduce el año de vencimiento",
                Toast.LENGTH_SHORT
            ).show()
            return false
        } else {
            return true
        }
    }

    private fun getStringDate(): String {
        val sdf = SimpleDateFormat("yyyy/MM/dd")
        return sdf.format(Date()).replace("/", "-")
    }

    private fun setComponents(t: Tarjeta) {
        asunto.setText(t.getAsunto())
        titular.setText(t.getTitular())
        ntarjeta.setText(t.getNtarjeta())
        mesDrop.editText!!.setText(t.getCadM())
        anio.setText(t.getCadY())
        codseg.setText(t.getCodseg())
        banco.setText(t.getBanco())
        nip.setText(t.getNip())
        guardarBtn.setText("Editar")
        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        toolbar.setTitle("Editar tarjeta")
    }

    private fun initComponents() {
        guardarBtn = findViewById(R.id.guardarBtn)
        val cancelarBtn = findViewById<Button>(R.id.cancelarBtn)

        asunto = findViewById(R.id.asunto)
        titular = findViewById(R.id.titular)
        ntarjeta = findViewById(R.id.ntarjeta)
        mesDrop = findViewById(R.id.mesDrop)
        anio = findViewById(R.id.anio)
        codseg = findViewById(R.id.codseg)
        banco = findViewById(R.id.banco)
        nip = findViewById(R.id.nip)

        val adapterMes = ArrayAdapter(
            this,
            R.layout.item_dropdown_menu,
            meses
        )
        (mesDrop!!.editText as? AutoCompleteTextView)?.setAdapter(adapterMes)

        guardarBtn.setOnClickListener(this)
        cancelarBtn.setOnClickListener(this)

        val ta = intent.getSerializableExtra("tarjeta") as? Tarjeta
        val taUpdated = intent.getSerializableExtra("tarjetaupdated") as? Tarjeta
        if (ta != null || taUpdated != null) {
            insert = false
            if (ta == null) {
                setComponents(taUpdated!!)
            } else {
                setComponents(ta)
            }
        } else {
            insert = true
        }
    }
}