package com.swtizona.passwordmanager.formactivities

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.textfield.TextInputLayout
import com.swtizona.passwordmanager.R
import com.swtizona.passwordmanager.mainactivities.HomeTabActivity
import com.swtizona.passwordmanager.database.DBController
import com.swtizona.passwordmanager.datamodel.Tarjeta
import com.swtizona.passwordmanager.datamodel.Usuario
import com.swtizona.passwordmanager.formviewsactivities.ViewPagos
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
    private var userIntent: Usuario? = null
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
        userIntent = intent.getSerializableExtra("userObject") as? Usuario
        initComponents()
    }

    override fun onStop() {
        super.onStop()
        if (userIntent!!.getChecked() == 1) {
            finish()
        }
    }

    override fun onBackPressed() {
        if (insert) {
            startActivity(Intent(this, HomeTabActivity::class.java).apply {
                putExtra("userObject", userIntent)
            })
            finish()
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
            Toast.makeText(
                applicationContext,
                "Este asunto ya ha sido registrado anteriormente",
                Toast.LENGTH_LONG
            ).show()
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
        db.close()
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
            putExtra("userObject", userIntent)
        }
        startActivity(intent)
        finish()
    }

    private fun save() {
        val db = DBController(applicationContext)
        var correcto = true
        val usuario = intent.getSerializableExtra("userObject") as? Usuario
        try {
            db.insertTarjeta(
                usuario!!.getNombre(),
                asunto.text.toString(),
                titular.text.toString(),
                ntarjeta.text.toString(),
                mesDrop.editText!!.text.toString(),
                anio.text.toString(),
                codseg.text.toString(),
                banco.text.toString(),
                nip.text.toString(),
                getStringDate()
            )
        } catch (ex: Exception) {
            ex.printStackTrace()
            makeToast("Este asunto ya ha sido registrado anteriormente")
            correcto = false
        }

        if (correcto) {
            makeToast("Tarjeta de '" + asunto.text.toString() + "' guardada exitosamente")
            db.close()
            onBackPressed()
        }
        db.close()
    }

    private fun checkFields(): Boolean {
        if (asunto.text.toString() == "") {
            makeToast("Introduce un asunto")
            return false
        } else if (titular.text.toString() == "") {
            makeToast("Introduce el titular")
            return false
        } else if (ntarjeta.text.toString() == "") {
            makeToast("Introduce el número de tarjeta")
            return false
        } else if (codseg.text.toString() == "") {
            makeToast("Introduce el código de seguridad")
            return false
        } else if (banco.text.toString() == "") {
            makeToast("Introduce el banco")
            return false
        } else if (nip.text.toString() == "") {
            makeToast("Introduce el nip del Cajero ATM")
            return false
        } else if (mesDrop.editText!!.text.toString() == "") {
            makeToast("Introduce el mes de vencimiento")
            return false
        } else if (mesDrop.editText!!.text.toString().length < 2) {
            makeToast("Introduce un mes válido")
            return false
        } else if (mesDrop.editText!!.text.toString().toInt() > 12) {
            makeToast("Introduce un mes válido")
            return false
        } else if (anio.text.toString() == "") {
            makeToast("Introduce el año de vencimiento")
            return false
        } else {
            return true
        }
    }

    private fun makeToast(mensaje: String) {
        Toast.makeText(applicationContext, mensaje, Toast.LENGTH_SHORT).show()
    }

    private fun getStringDate(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
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
        guardarBtn.text = "Editar"
        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        toolbar.title = "Editar tarjeta"
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
        (mesDrop.editText as? AutoCompleteTextView)?.setAdapter(adapterMes)
        mesDrop.setOnClickListener(this)

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

        anio.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val text = s.toString()
                var isValid = true

                if (text.length == 1) {
                    isValid = false
                }
                if (text.isNotEmpty() && text.toInt() < 20) {
                    isValid = false
                }

                if (!isValid) {
                    anio.error = "Año inválido"
                }
            }

            override fun afterTextChanged(s: Editable?) {}

        })
        ntarjeta.addTextChangedListener(object : TextWatcher {
            private var current = ""
            private val nonDigits = Regex("[^\\d]")

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                Log.wtf("", "" + s!!.length)
            }

            override fun afterTextChanged(s: Editable?) {
                if (s.toString() != current) {
                    val userInput = s.toString().replace(nonDigits, "")
                    if (userInput.length <= 16) {
                        current = userInput.chunked(4).joinToString(" ")
                        s!!.filters = arrayOfNulls<InputFilter>(0)
                    }
                    s!!.replace(0, s.length, current, 0, current.length)
                }
            }
        })
    }
}