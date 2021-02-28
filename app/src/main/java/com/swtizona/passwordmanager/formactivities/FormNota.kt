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
import com.swtizona.passwordmanager.datamodel.Nota
import com.swtizona.passwordmanager.datamodel.Usuario
import com.swtizona.passwordmanager.formviewsactivities.ViewNotas
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class FormNota :
    AppCompatActivity(),
    View.OnClickListener {

    private lateinit var title: EditText
    private lateinit var body: EditText
    private var insert = false
    private var userIntent: Usuario? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_nota)
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
            val no = intent.getSerializableExtra("nota") as? Nota
            val noUpdated = intent.getSerializableExtra("notaupdated") as? Nota
            if (no == null) {
                goToView(noUpdated!!)
            } else if (noUpdated == null) {
                goToView(no)
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
        val no = intent.getSerializableExtra("nota") as? Nota
        val noUpdated = intent.getSerializableExtra("notaupdated") as? Nota
        var good = true
        try {
            if (no == null) {
                db.updateNota(
                    noUpdated!!.getNomusuario(),
                    noUpdated.getAsunto(),
                    title.text.toString(),
                    body.text.toString()
                )
            } else if (noUpdated == null) {
                db.updateNota(
                    no.getNomusuario(),
                    no.getAsunto(),
                    title.text.toString(),
                    body.text.toString()
                )
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            Toast.makeText(
                applicationContext,
                "Error al actualizar la nota",
                Toast.LENGTH_LONG
            ).show()
            good = false
        }

        if (good) {
            Toast.makeText(
                applicationContext,
                "La nota ha sido actualizada correctamente",
                Toast.LENGTH_LONG
            ).show()
            if (no == null) {
                goToView(noUpdated!!)
            } else if (noUpdated == null) {
                goToView(no)
            }
        }
        db.close()

    }

    private fun goToView(n: Nota) {
        val intent = Intent(this, ViewNotas::class.java)
        intent.apply {
            putExtra(
                "notaupdated", Nota(
                    n.getNomusuario(),
                    title.text.toString(),
                    body.text.toString(),
                    n.getFecha()
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
            db.insertNota(
                usuario!!.getNombre(),
                title.text.toString(),
                body.text.toString(),
                getStringDate()
            )
        } catch (ex: Exception) {
            ex.printStackTrace()
            makeToast("Error al introducir la nota")
            correcto = false
        }

        if (correcto) {
            Toast.makeText(
                applicationContext,
                "La nota ha sido guardada correctamente",
                Toast.LENGTH_LONG
            ).show()
            onBackPressed()
        }
        db.close()
    }

    private fun checkFields(): Boolean {
        return if (title.text.toString() == "") {
            makeToast("Escribe el título de la nota")
            title.requestFocus()
            false
        } else if (body.text.toString() == "") {
            makeToast("Escribe el cuerpo de la nota")
            body.requestFocus()
            false
        } else {
            true
        }
    }

    private fun makeToast(mensaje: String) {
        Toast.makeText(applicationContext, mensaje, Toast.LENGTH_SHORT).show()
    }

    private fun getStringDate(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        return sdf.format(Date()).replace("/", "-")
    }

    private fun setComponents(n: Nota) {
        title.setText(n.getAsunto())
        body.setText(n.getNota())
        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        toolbar.title = "Editar Nota"
    }

    private fun initComponents() {
        title = findViewById(R.id.title)
        body = findViewById(R.id.body)

        val guardarBtn = findViewById<Button>(R.id.guardarBtn)
        val cerrarBtn = findViewById<Button>(R.id.cancelarBtn)
        guardarBtn.setOnClickListener(this)
        cerrarBtn.setOnClickListener(this)

        val no = intent.getSerializableExtra("nota") as? Nota
        val noUpdated = intent.getSerializableExtra("notaupdated") as? Nota

        if (no != null || noUpdated != null) {
            insert = false
            if (no == null) {
                setComponents(noUpdated!!)
            } else if (noUpdated == null) {
                setComponents(no)
            }
        } else {
            insert = true
        }
    }
}