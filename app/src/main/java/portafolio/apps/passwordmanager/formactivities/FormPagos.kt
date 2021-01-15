package portafolio.apps.passwordmanager.formactivities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.google.android.material.textfield.TextInputLayout
import portafolio.apps.passwordmanager.R
import portafolio.apps.passwordmanager.database.DBController
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class FormPagos :
    AppCompatActivity(),
    View.OnClickListener {

    private var asunto: EditText? = null
    private var titular: EditText? = null
    private var ntarjeta: EditText? = null
    private var mesDrop: TextInputLayout? = null
    private var anio: EditText? = null
    private var codseg: EditText? = null
    private var banco: EditText? = null
    private var nip: EditText? = null
    private val meses = listOf(
        "1",
        "2",
        "3",
        "4",
        "5",
        "6",
        "7",
        "8",
        "9",
        "10",
        "11",
        "12",
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_pagos)
        initComponents()
    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {
                R.id.guardarBtn -> {
                    if (checkFields()) {
                        save()
                    }
                }
                R.id.cancelarBtn -> {
                    finish()
                }
            }
        }
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

    private fun initComponents() {
        val guardarBtn = findViewById<Button>(R.id.guardarBtn)
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
    }
}