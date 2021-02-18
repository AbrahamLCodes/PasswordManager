package portafolio.apps.passwordmanager.formactivities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import portafolio.apps.passwordmanager.R
import portafolio.apps.passwordmanager.database.DBController
import portafolio.apps.passwordmanager.datamodel.Usuario
import java.lang.Exception

class FormUsuario : AppCompatActivity(), View.OnClickListener {

    private lateinit var nombre: EditText
    private lateinit var pass1: EditText
    private lateinit var pass2: EditText
    private lateinit var checkBox: CheckBox
    private var userObject: Usuario? = null
    private var insert = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_usuario)

        if (intent.getSerializableExtra("userObject") != null) {
            insert = false
            userObject = intent.getSerializableExtra("userObject") as? Usuario
        }

        initComponents()
    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {
                R.id.accept -> {
                    if (checkFields() && checkPasswords()) {
                        if (insert) {
                            confirmDialog()
                        } else {
                            confirmUpdateDialog()
                        }
                    }
                }
                R.id.cancel -> {
                    finish()
                }

            }
        }
    }

    private fun confirmUpdateDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Actualizar Usuario")

        builder.setMessage(
            resources.getString(R.string.advertencia) + "\n\n\nNombre: " + nombre.text.toString() + "\n" +
                    "Contraseña: " + pass1.text.toString()
        )
        builder.setPositiveButton("Actualizar") { _, _ ->
            update()
        }
        builder.setNegativeButton("Cancelar") { _, _ ->
        }
        builder.show()
    }

    private fun confirmDialog() {
        val builder = AlertDialog.Builder(this)

        builder.setTitle("Registrar Usuario")
        builder.setMessage(
            resources.getString(R.string.advertencia) + "\n\n\nNombre: " + nombre.text.toString() + "\n" +
                    "Contraseña: " + pass1.text.toString()
        )
        builder.setPositiveButton("Registrar") { _, _ ->
            save()
        }
        builder.setNegativeButton("Cancelar") { _, _ ->
        }
        builder.show()
    }

    private fun update() {
        val db = DBController(applicationContext)
        var flag = true
        var checked = 0
        if (checkBox.isChecked) {
            checked = 1
        }

        try {
            db.updateUsuario(
                userObject!!.getNombre(),
                nombre.text.toString(),
                pass1.text.toString(),
                checked
            )
            updateTables()
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
                "El usuario '" + nombre.text.toString() + "' se ha actualizado correctamente",
                Toast.LENGTH_LONG
            ).show()
            db.close()
            finish()
        }

        db.close()
    }

    private fun updateTables() {
        val db = DBController(applicationContext)
        db.updateUserTable(userObject!!.getNombre(), nombre.text.toString(), "CORREOS")
        db.updateUserTable(userObject!!.getNombre(), nombre.text.toString(), "CUENTAS")
        db.updateUserTable(userObject!!.getNombre(), nombre.text.toString(), "CONTRASENIAS")
        db.updateUserTable(userObject!!.getNombre(), nombre.text.toString(), "NOTAS")
        db.updateUserTable(userObject!!.getNombre(), nombre.text.toString(), "TARJETAS")
        db.close()
    }

    private fun save() {
        val db = DBController(applicationContext)
        var flag = true
        var checked = 0
        if (checkBox.isChecked) {
            checked = 1
        }

        try {
            db.insertUsuario(
                nombre.text.toString(),
                pass1.text.toString(),
                checked
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

    private fun setUpdateComponents(editar: Button) {
        nombre.setText(userObject!!.getNombre())
        pass1.setText(userObject!!.getContrasenia())
        pass2.setText(userObject!!.getContrasenia())
        checkBox.isChecked = userObject!!.getChecked() == 1
        val titulo = findViewById<TextView>(R.id.formUserTitle)
        titulo.setText("Editar Usuario")
        editar.setText("Actualizar")
    }

    private fun initComponents() {

        val aceptar = findViewById<Button>(R.id.accept)
        val cancelar = findViewById<Button>(R.id.cancel)
        nombre = findViewById(R.id.usuario)
        pass1 = findViewById(R.id.password1)
        pass2 = findViewById(R.id.password2)
        checkBox = findViewById(R.id.checkBox)

        if (!insert) {
            setUpdateComponents(aceptar)
        }

        aceptar.setOnClickListener(this)
        cancelar.setOnClickListener(this)
    }
}