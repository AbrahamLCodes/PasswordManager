package portafolio.apps.passwordmanager.formactivities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.textfield.TextInputLayout
import portafolio.apps.passwordmanager.R
import portafolio.apps.passwordmanager.database.DBController
import portafolio.apps.passwordmanager.datamodel.Contrasenia
import portafolio.apps.passwordmanager.formviewsactivities.ViewContrasenia
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_contrasenia)
        initComponents()
    }

    override fun onBackPressed() {
        if (insert) {
            super.onBackPressed()
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
            good = false
        }

        if (good) {
            Toast.makeText(
                applicationContext,
                "La contraseña ha sido actualizada correctamente",
                Toast.LENGTH_SHORT
            ).show()
            if (co == null) {
                goToView(coUpdated!!)
            } else if (coUpdated == null) {
                goToView(co)
            }
        }
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
        }
        startActivity(intent)
        finish()
    }

    private fun save() {
        val db = DBController(applicationContext)
        var correcto = true
        try {
            db.insertContrasenia(
                intent.getStringExtra("username")!!,
                nomUsuario!!.text.toString(),
                contrasenia1!!.text.toString(),
                getStringDate()
            )
        } catch (ex: Exception) {
            ex.printStackTrace()
            Toast.makeText(
                applicationContext, "Error al introducir el registro", Toast.LENGTH_LONG
            ).show()
            correcto = false
        }

        if (correcto) {
            db.close()
            Toast.makeText(
                applicationContext,
                "Contraseña de '" + nomUsuario!!.text.toString() + "' guardada exitosamente",
                Toast.LENGTH_LONG
            ).show()
            finish()
        }
    }

    private fun checkFields(): Boolean {
        if (nomUsuario!!.text.toString().equals("")) {
            Toast.makeText(
                applicationContext, "Introduce un nombre/asunto", Toast.LENGTH_SHORT
            ).show()
            nomUsuario!!.requestFocus()
            return false
        } else if (contrasenia!!.text.toString().equals("")) {
            Toast.makeText(
                applicationContext, "Introduce una contraseña", Toast.LENGTH_SHORT
            ).show()
            contrasenia!!.requestFocus()
            return false
        } else if (contrasenia1!!.text.toString().equals("")) {
            Toast.makeText(
                applicationContext, "Vuelve a introducir la contraseña", Toast.LENGTH_SHORT
            ).show()
            contrasenia1!!.requestFocus()
            return false
        } else {
            return true
        }
    }

    private fun checkPasswords(): Boolean {
        var correcto: Boolean?
        if (contrasenia!!.text.toString().equals(contrasenia1!!.text.toString())) {
            correcto = true
        } else {
            correcto = false
            Toast.makeText(
                applicationContext,
                "Las contraseñas no coinciden",
                Toast.LENGTH_SHORT
            ).show()
        }
        return correcto
    }

    private fun getStringDate(): String {
        val sdf = SimpleDateFormat("yyyy/MM/dd")
        return sdf.format(Date()).replace("/", "-")
    }

    private fun setComponents(c: Contrasenia) {
        nomUsuario.setText(c.getAsunto())
        contrasenia.setText(c.getContrasenia())
        contrasenia1.setText(c.getContrasenia())
        guardarBtn.setText("Editar")
        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        toolbar.setTitle("Editar Contraseña")
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