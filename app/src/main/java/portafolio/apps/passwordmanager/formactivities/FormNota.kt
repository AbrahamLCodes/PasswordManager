package portafolio.apps.passwordmanager.formactivities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.appbar.MaterialToolbar
import portafolio.apps.passwordmanager.R
import portafolio.apps.passwordmanager.database.DBController
import portafolio.apps.passwordmanager.datamodel.Nota
import portafolio.apps.passwordmanager.formviewsactivities.ViewNotas
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class FormNota :
    AppCompatActivity(),
    View.OnClickListener {

    private lateinit var title: EditText
    private lateinit var body: EditText
    private var insert = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_nota)
        initComponents()
    }

    override fun onBackPressed() {
        if (insert) {
            super.onBackPressed()
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
            good = false
        }

        if (good) {
            Toast.makeText(
                applicationContext,
                "La nota ha sido actualizada correctamente",
                Toast.LENGTH_SHORT
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
        }
        startActivity(intent)
        finish()
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
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        return sdf.format(Date()).replace("/", "-")
    }

    private fun setComponents(n: Nota) {
        title.setText(n.getAsunto())
        body.setText(n.getNota())
        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        toolbar.setTitle("Editar Nota")
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