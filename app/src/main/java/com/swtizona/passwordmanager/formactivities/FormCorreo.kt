package com.swtizona.passwordmanager.formactivities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.appbar.MaterialToolbar
import com.swtizona.passwordmanager.R
import com.swtizona.passwordmanager.mainactivities.HomeTabActivity
import com.swtizona.passwordmanager.database.DBController
import com.swtizona.passwordmanager.datamodel.Correo
import com.swtizona.passwordmanager.datamodel.Usuario
import com.swtizona.passwordmanager.formviewsactivities.ViewCorreo
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class FormCorreo : AppCompatActivity(), View.OnClickListener {

    private lateinit var asunto: EditText
    private lateinit var correo: EditText
    private lateinit var contrasenia: EditText
    private lateinit var contrasenia1: EditText
    private lateinit var guardar: Button
    private lateinit var cancelar: Button
    private var insert = false
    private var userIntent: Usuario? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_correo)
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
            val co = intent.getSerializableExtra("correo") as? Correo
            val co2 = intent.getSerializableExtra("correoupdated") as? Correo
            if (co == null) {
                goToView(co2!!)
            } else if (co2 == null) {
                goToView(co)
            }
        }
    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {
                R.id.guardarBtn -> {
                    if (checkFields() && checkPasswords()) {
                        if (insert) {
                            insert()
                        } else {
                            update()
                        }
                    } else {
                        makeToast("Las contraseñas no coinciden")
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
        val co = intent.getSerializableExtra("correo") as? Correo
        val co2 = intent.getSerializableExtra("correoupdated") as? Correo
        var good = true
        try {
            if (co == null) {
                db.updateCorreo(
                    co2!!.getNomusuario(),
                    asunto.text.toString(),
                    correo.text.toString(),
                    co2.getCorreo(),
                    contrasenia.text.toString()
                )
            } else if (co2 == null) {
                db.updateCorreo(
                    co.getNomusuario(),
                    asunto.text.toString(),
                    correo.text.toString(),
                    co.getCorreo(),
                    contrasenia.text.toString()
                )
            }

        } catch (ex: Exception) {
            ex.printStackTrace()
            Toast.makeText(
                applicationContext,
                "Este correo ya ha sido registrado anteriormente",
                Toast.LENGTH_LONG
            ).show()
            good = false
        }

        if (good) {
            Toast.makeText(
                applicationContext,
                "El correo '" + correo.text.toString() + "' ha sido actualizado correctamente",
                Toast.LENGTH_LONG
            ).show()
            if (co == null) {
                goToView(co2!!)
            } else if (co2 == null) {
                goToView(co)
            }
        }
        db.close()
    }

    private fun goToView(co: Correo) {
        val intent = Intent(this, ViewCorreo::class.java)
        intent.apply {
            putExtra(
                "correoupdated", Correo(
                    co.getNomusuario(),
                    asunto.text.toString(),
                    correo.text.toString(),
                    contrasenia.text.toString(),
                    co.getFecha()
                )
            )
            putExtra("userObject", userIntent)
        }

        startActivity(intent)
        finish()
    }

    private fun insert() {
        val db = DBController(applicationContext)
        var good = true
        val userObject = intent.getSerializableExtra("userObject") as? Usuario
        try {
            db.insertCorreo(
                userObject!!.getNombre(),
                asunto.text.toString(),
                correo.text.toString(),
                contrasenia.text.toString(),
                getStringDate()
            )
        } catch (ex: Exception) {
            ex.printStackTrace()
            Toast.makeText(
                applicationContext,
                "Este correo ya ha sido registrado anteriormente",
                Toast.LENGTH_SHORT
            ).show()
            good = false
        }

        if (good) {
            Toast.makeText(
                applicationContext,
                "El correo '" + correo.text.toString() + "' ha sido guardado correctamente",
                Toast.LENGTH_SHORT
            ).show()
            onBackPressed()
        }
        db.close()
    }

    private fun checkFields(): Boolean {
        if (asunto.text.toString() == "") {
            makeToast("Introduce un asunto/nombre")
            asunto.requestFocus()
            return false
        } else if (correo.text.toString() == "") {
            makeToast("Introduce un correo")
            correo.requestFocus()
            return false
        } else if (contrasenia.text.toString() == "") {
            makeToast("Introduce una contraseña")
            contrasenia.requestFocus()
            return false
        } else if (contrasenia1.text.toString() == "") {
            makeToast("Vuelve a introducir la contraseña")
            contrasenia1.requestFocus()
            return false
        } else {
            return true
        }
    }

    private fun checkPasswords(): Boolean {
        return if (contrasenia.text.toString() == contrasenia1.text.toString()) {
            true
        } else {
            makeToast("Las contraseñas no coinciden")
            false
        }

    }

    private fun setComponents(co: Correo) {
        asunto.setText(co.getNombre())
        correo.setText(co.getCorreo())
        contrasenia.setText(co.getContrasenia())
        contrasenia1.setText(co.getContrasenia())
        guardar.text = "EDITAR"
        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        toolbar.title = "Editar Correo"
    }

    private fun makeToast(mensaje: String) {
        Toast.makeText(applicationContext, mensaje, Toast.LENGTH_SHORT).show()
    }

    private fun getStringDate(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        return sdf.format(Date()).replace("/", "-")
    }

    private fun initComponents() {
        asunto = findViewById(R.id.nomUsuario)
        correo = findViewById(R.id.correo)
        contrasenia = findViewById(R.id.contrasenia)
        contrasenia1 = findViewById(R.id.contrasenia1)
        guardar = findViewById(R.id.guardarBtn)
        guardar.setOnClickListener(this)
        cancelar = findViewById(R.id.cancelarBtn)
        cancelar.setOnClickListener(this)

        val co = intent.getSerializableExtra("correo") as? Correo
        val co2 = intent.getSerializableExtra("correoupdated") as? Correo
        if (co != null || co2 != null) {
            insert = false
            if (co == null) {
                setComponents(co2!!)
            } else if (co2 == null) {
                setComponents(co)
            }

        } else {
            insert = true
        }
    }
}