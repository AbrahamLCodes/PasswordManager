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
import com.swtizona.passwordmanager.datamodel.Contrasenia
import com.swtizona.passwordmanager.datamodel.Usuario
import com.swtizona.passwordmanager.formviewsactivities.ViewContrasenia
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class FormContrasenia :
    AppCompatActivity(),
    View.OnClickListener {

    private lateinit var nomUsuario: EditText
    private lateinit var contrasenia: EditText
    private lateinit var contrasenia1: EditText
    private lateinit var guardarBtn: Button
    private var insert = false
    private var userIntent: Usuario? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_contrasenia)
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
            val co = intent.getSerializableExtra("contrasenia") as? Contrasenia
            val coUpdated = intent.getSerializableExtra("contraseniaupdated") as? Contrasenia
            if (co == null) {
                goToView(coUpdated!!)
            } else if (coUpdated == null) {
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
        val co = intent.getSerializableExtra("contrasenia") as? Contrasenia
        val coUpdated = intent.getSerializableExtra("contraseniaupdated") as? Contrasenia
        var good = true

        try {
            if (co == null) {
                db.updateContrasenia(
                    coUpdated!!.getNomusuario(),
                    coUpdated.getAsunto(),
                    nomUsuario.text.toString(),
                    contrasenia.text.toString()
                )
            } else if (coUpdated == null) {
                db.updateContrasenia(
                    co.getNomusuario(),
                    co.getAsunto(),
                    nomUsuario.text.toString(),
                    contrasenia.text.toString()
                )
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            makeToast("Error al actualizar contraseña")
            good = false
        }

        if (good) {
            Toast.makeText(
                applicationContext,
                "La contraseña ha sido actualizada correctamente",
                Toast.LENGTH_LONG
            ).show()
            if (co == null) {
                goToView(coUpdated!!)
            } else if (coUpdated == null) {
                goToView(co)
            }
        }
        db.close()
    }

    private fun goToView(c: Contrasenia) {
        val intent = Intent(this, ViewContrasenia::class.java)
        intent.apply {
            putExtra(
                "contraseniaupdated", Contrasenia(
                    c.getNomusuario(),
                    nomUsuario.text.toString(),
                    contrasenia.text.toString(),
                    c.getFecha()
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
            db.insertContrasenia(
                usuario!!.getNombre(),
                nomUsuario.text.toString(),
                contrasenia1.text.toString(),
                getStringDate()
            )
        } catch (ex: Exception) {
            ex.printStackTrace()
            makeToast("Error al introducir el registro")
            correcto = false
        }

        if (correcto) {
            db.close()
            Toast.makeText(
                applicationContext,
                "Contraseña de '" + nomUsuario.text.toString() + "' guardada exitosamente",
                Toast.LENGTH_LONG
            ).show()
            onBackPressed()
        }
        db.close()
    }

    private fun checkFields(): Boolean {
        return if (nomUsuario.text.toString() == "") {
            makeToast("Introduce un nombre/asunto")
            nomUsuario.requestFocus()
            false
        } else if (contrasenia.text.toString() == "") {
            makeToast("Introduce una contraseña")
            contrasenia.requestFocus()
            false
        } else if (contrasenia1.text.toString() == "") {
            makeToast("Vuelve a introducir la contraseña")
            contrasenia1.requestFocus()
            false
        } else {
            true
        }
    }

    private fun checkPasswords(): Boolean {
        val correcto: Boolean?
        if (contrasenia.text.toString() == contrasenia1.text.toString()) {
            correcto = true
        } else {
            correcto = false
            makeToast("Las contraseñas no coinciden")
        }
        return correcto
    }

    private fun makeToast(mensaje: String) {
        Toast.makeText(applicationContext, mensaje, Toast.LENGTH_SHORT)
    }

    private fun getStringDate(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        return sdf.format(Date()).replace("/", "-")
    }

    private fun setComponents(c: Contrasenia) {
        nomUsuario.setText(c.getAsunto())
        contrasenia.setText(c.getContrasenia())
        contrasenia1.setText(c.getContrasenia())
        guardarBtn.setText("Editar")
        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        toolbar.title = "Editar Contraseña"
    }

    private fun initComponents() {
        nomUsuario = findViewById(R.id.nomUsuario)
        contrasenia = findViewById(R.id.contrasenia)
        contrasenia1 = findViewById(R.id.contrasenia1)
        guardarBtn = findViewById(R.id.guardarBtn)
        val cancelarBtn = findViewById<Button>(R.id.cancelarBtn)

        guardarBtn.setOnClickListener(this)
        cancelarBtn.setOnClickListener(this)

        val co = intent.getSerializableExtra("contrasenia") as? Contrasenia
        val coUpdated = intent.getSerializableExtra("contraseniaupdated") as? Contrasenia
        if (co != null || coUpdated != null) {
            insert = false
            if (co == null) {
                setComponents(coUpdated!!)
            } else if (coUpdated == null) {
                setComponents(co)
            }
        } else {
            insert = true
        }
    }

}