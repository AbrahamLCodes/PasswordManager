package portafolio.apps.passwordmanager.formviewsactivities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import portafolio.apps.passwordmanager.R
import portafolio.apps.passwordmanager.datamodel.Contrasenia
import portafolio.apps.passwordmanager.datamodel.Nota
import portafolio.apps.passwordmanager.formactivities.FormContrasenia
import portafolio.apps.passwordmanager.formactivities.FormNota

class ViewNotas :
    AppCompatActivity(),
    View.OnClickListener {

    private lateinit var titulo: TextView
    private lateinit var tituloBtn: ImageButton
    private lateinit var body: TextView
    private lateinit var back: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_notas)
        initComponents()
    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {
                R.id.back -> {
                    onBackPressed()
                }
                R.id.editar -> {
                    if (intent.getSerializableExtra("notaupdated") != null) {
                        val c = intent.getSerializableExtra("notaupdated") as? Nota
                        startActivity(Intent(this, FormNota::class.java).apply {
                            putExtra("notaupdated", c)
                            putExtra("username", c!!.getNomusuario())
                        })
                    } else {
                        val c = intent.getSerializableExtra("nota") as? Nota
                        startActivity(Intent(this, FormNota::class.java).apply {
                            putExtra("nota", c)
                            putExtra("username", c!!.getNomusuario())
                        })
                    }
                    finish()
                }
            }
        }
    }

    private fun setComponents(n: Nota) {
        titulo.setText(n.getAsunto())
        body.setText(n.getNota())
    }

    private fun initComponents() {
        titulo = findViewById(R.id.titulo)
        tituloBtn = findViewById(R.id.tituloBtn)
        body = findViewById(R.id.body)
        back = findViewById(R.id.back)
        val editar = findViewById<ImageButton>(R.id.editar)

        val nota = intent.getSerializableExtra("nota") as? Nota
        if (nota != null) {
            titulo.text = nota.getAsunto()
            body.text = nota.getNota()
        }

        val notaUpdated = intent.getSerializableExtra("notaupdated") as? Nota
        if (notaUpdated != null) {
            setComponents(notaUpdated)
        }

        editar.setOnClickListener(this)
        tituloBtn.setOnClickListener(this)
        back.setOnClickListener(this)
    }
}