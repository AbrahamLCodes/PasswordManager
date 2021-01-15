package portafolio.apps.passwordmanager.formactivities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.google.android.material.textfield.TextInputLayout
import portafolio.apps.passwordmanager.R
import portafolio.apps.passwordmanager.database.DBController
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class FormContrasenia :
    AppCompatActivity(),
    View.OnClickListener {

    private var nomUsuario: EditText? = null
    private var contrasenia: EditText? = null
    private var contrasenia1: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_contrasenia)
        initComponents()
    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {
                R.id.guardarBtn -> {
                    if (checkFields()) {
                        if (checkPasswords()) {
                            save()
                        }
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

    private fun initComponents() {
        nomUsuario = findViewById(R.id.nomUsuario)
        contrasenia = findViewById(R.id.contrasenia)
        contrasenia1 = findViewById(R.id.contrasenia1)

        val guardarBtn = findViewById<Button>(R.id.guardarBtn)
        val cancelarBtn = findViewById<Button>(R.id.cancelarBtn)

        guardarBtn.setOnClickListener(this)
        cancelarBtn.setOnClickListener(this)
    }

}