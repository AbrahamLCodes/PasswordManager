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

class FormNota :
    AppCompatActivity(),
    View.OnClickListener {

    private var title: EditText? = null
    private var body: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_nota)
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
            db.insertNota(
                intent.getStringExtra("username")!!,
                title!!.text.toString(),
                body!!.text.toString(),
                getStringDate()
            )
        } catch (ex: Exception) {
            ex.printStackTrace()
            Toast.makeText(
                applicationContext,
                "Error al introducir la nota",
                Toast.LENGTH_SHORT
            ).show()
            correcto = false
        }

        if (correcto) {
            Toast.makeText(
                applicationContext,
                "La nota se ha guardado exitosamente",
                Toast.LENGTH_SHORT
            ).show()
            finish()
        }
        db.close()
    }

    private fun checkFields(): Boolean {
        if (title!!.text.toString().equals("")) {
            Toast.makeText(
                applicationContext,
                "Introduce el nombre/asunto",
                Toast.LENGTH_SHORT
            ).show()
            title!!.requestFocus()
            return false
        } else if (body!!.text.toString().equals("")) {
            Toast.makeText(
                applicationContext,
                "Escribe el cuerpo de la nota",
                Toast.LENGTH_SHORT
            ).show()
            body!!.requestFocus()
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
        title = findViewById(R.id.title)
        body = findViewById(R.id.body)

        val guardarBtn = findViewById<Button>(R.id.guardarBtn)
        val cerrarBtn = findViewById<Button>(R.id.cancelarBtn)
        guardarBtn.setOnClickListener(this)
        cerrarBtn.setOnClickListener(this)
    }
}