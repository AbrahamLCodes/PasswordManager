package portafolio.apps.passwordmanager.formactivities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.material.textview.MaterialTextView
import portafolio.apps.passwordmanager.R
import portafolio.apps.passwordmanager.database.DBController
import java.lang.Exception

class FormUsuario : AppCompatActivity(), View.OnClickListener {

    private lateinit var nombre: EditText
    private lateinit var pass1: EditText
    private lateinit var pass2: EditText
    private lateinit var correo1: EditText
    private lateinit var correo2: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_usuario)
        initComponents()
    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {
                R.id.accept -> {
                    if (checkFields()) {
                        if (checkPasswords() && checkEmails()) {
                            if (validEmails()) {
                                confirmDialog()
                            }
                        }
                    }
                }
                R.id.cancel -> {
                    finish()
                }

            }
        }
    }

    private fun confirmDialog() {
        val builder = AlertDialog.Builder(this)

        builder.setTitle("Registrar Usuario")
        builder.setMessage(
            "Nombre: " + nombre.text.toString() + "\n" +
                    "Contraseña: " + pass1.text.toString() + "\n" +
                    "Correo de recuperación: " + correo1.text.toString()
        )
        builder.setPositiveButton("Registrar") { _, _ ->
            save()
        }
        builder.setNegativeButton("Cancelar") { _, _ ->
        }
        builder.show()
    }

    private fun save() {
        val db = DBController(applicationContext)
        var flag = true

        try {
            db.insertUsuario(
                nombre.text.toString(),
                pass1.text.toString(),
                correo1.text.toString()
            )
        } catch (ex: Exception) {
            ex.printStackTrace()
            Toast.makeText(
                applicationContext,
                "El usuario ya ha sido registrado anteriormente",
                Toast.LENGTH_SHORT
            ).show()
            flag = false
        }

        if (flag) {
            Toast.makeText(
                applicationContext,
                "El usuario '" + nombre?.text.toString() + "' se ha registrado correctamente",
                Toast.LENGTH_LONG
            ).show()
            finish()
        }
        db.close()
    }

    private fun checkFields(): Boolean {
        if (nombre.text.toString().isEmpty()) {
            Toast.makeText(applicationContext, "Introduce un nombre", Toast.LENGTH_SHORT).show()
            return false
        } else if (pass1.text.toString().isEmpty()) {
            Toast.makeText(applicationContext, "Introduce una contraseña", Toast.LENGTH_SHORT)
                .show()
            return false
        } else if (pass2.text.toString().isEmpty()) {
            Toast.makeText(
                applicationContext,
                "Vuelve a introducir la contraseña",
                Toast.LENGTH_SHORT
            ).show()
            return false
        } else if (correo1.text.toString().isEmpty()) {
            Toast.makeText(
                applicationContext,
                "Introduce un e-mail para recuperación de la cuenta",
                Toast.LENGTH_SHORT
            ).show()
            return false
        } else if (correo1.text.toString().isEmpty()) {
            Toast.makeText(
                applicationContext,
                "Vuelve a introducir el e-mail de recuperación",
                Toast.LENGTH_SHORT
            ).show()
            return false
        } else {
            return true
        }
    }

    private fun checkPasswords(): Boolean {
        if (pass1.text.toString() == pass2.text.toString()) {
            return true
        } else {
            Toast.makeText(applicationContext, "Las contraseñas no coinciden", Toast.LENGTH_SHORT)
                .show()
            return false
        }
    }

    private fun checkEmails(): Boolean {
        if (correo1.text.toString() == correo2.text.toString()) {
            return true
        } else {
            Toast.makeText(applicationContext, "Los correos no coinciden", Toast.LENGTH_SHORT)
                .show()
            return false
        }
    }

    private fun validEmails(): Boolean {
        if (correo1.text.toString().contains(" ") || !correo1.text.toString().contains("@")) {
            Toast.makeText(applicationContext, "Correo invalido", Toast.LENGTH_SHORT)
                .show()
            return false
        } else if (correo2.text.toString().contains(" ") || !correo2.text.toString()
                .contains("@")
        ) {
            Toast.makeText(applicationContext, "Correo invalido", Toast.LENGTH_SHORT)
                .show()
            return false
        } else {
            return true
        }
    }

    private fun initComponents() {

        val aceptar = findViewById<Button>(R.id.accept)
        val cancelar = findViewById<Button>(R.id.cancel)
        nombre = findViewById(R.id.usuario)
        pass1 = findViewById(R.id.password1)
        pass2 = findViewById(R.id.password2)

        correo1 = findViewById(R.id.correo1)
        correo2 = findViewById(R.id.correo2)

        aceptar.setOnClickListener(this)
        cancelar.setOnClickListener(this)
    }
}