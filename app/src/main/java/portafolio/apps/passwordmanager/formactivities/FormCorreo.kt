package portafolio.apps.passwordmanager.formactivities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import portafolio.apps.passwordmanager.R
import portafolio.apps.passwordmanager.database.DBController
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class FormCorreo : AppCompatActivity(), View.OnClickListener {

    var asunto: EditText? = null
    var correo: EditText? = null
    var contrasenia: EditText? = null
    var contrasenia1: EditText? = null
    var guardar: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_correo)
        initComponents()
    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {
                R.id.guardarBtn -> {
                    if (checkFields()) {
                        if (checkPasswords()) {
                            insert()
                        } else {
                            Toast.makeText(
                                applicationContext,
                                "Las contraseñas no coinciden",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }
    }

    private fun insert() {
        val db = DBController(applicationContext)
        var good = true
        try {
            db.insertCorreo(
                intent.getStringExtra("username")!!,
                asunto!!.text.toString(),
                correo!!.text.toString(),
                contrasenia!!.text.toString(),
                getStringDate()
            )
        } catch (ex: Exception) {
            ex.printStackTrace()
            good = false
        }

        if (good) {
            Toast.makeText(
                applicationContext,
                "El correo '" + correo!!.text.toString() + "' ha sido guardado correctamente",
                Toast.LENGTH_SHORT
            ).show()
            finish()
        } else {
            Toast.makeText(
                applicationContext,
                "Este correo ya ha sido introducido",
                Toast.LENGTH_SHORT
            ).show()
        }
        db.close()
    }

    private fun checkFields(): Boolean {
        if (asunto!!.text.toString().equals("")) {
            Toast.makeText(applicationContext, "Introduce un asunto/nombre", Toast.LENGTH_SHORT)
                .show()
            asunto!!.requestFocus()
            return false
        } else if (correo!!.text.toString().equals("")) {
            Toast.makeText(applicationContext, "Introduce un correo", Toast.LENGTH_SHORT)
                .show()
            correo!!.requestFocus()
            return false
        } else if (contrasenia!!.text.toString().equals("")) {
            Toast.makeText(applicationContext, "Introduce una contraseña", Toast.LENGTH_SHORT)
                .show()
            contrasenia!!.requestFocus()
            return false
        } else if (contrasenia1!!.text.toString().equals("")) {
            Toast.makeText(
                applicationContext,
                "Vuelve a introducir la contraseña",
                Toast.LENGTH_SHORT
            )
                .show()
            contrasenia1!!.requestFocus()
            return false
        } else {
            return true
        }
    }

    private fun checkPasswords(): Boolean {
        return contrasenia!!.text.toString().equals(contrasenia1!!.text.toString())
    }

    private fun getStringDate(): String {
        val sdf = SimpleDateFormat("yyyy/MM/dd")
        return sdf.format(Date()).replace("/", "-")
    }

    private fun initComponents() {
        asunto = findViewById(R.id.asunto)
        correo = findViewById(R.id.correo)
        contrasenia = findViewById(R.id.contrasenia)
        contrasenia1 = findViewById(R.id.contrasenia1)
        guardar = findViewById(R.id.guardarBtn)
        guardar!!.setOnClickListener(this)
    }
}