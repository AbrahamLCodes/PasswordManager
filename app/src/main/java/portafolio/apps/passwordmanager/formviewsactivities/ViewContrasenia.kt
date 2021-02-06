package portafolio.apps.passwordmanager.formviewsactivities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import portafolio.apps.passwordmanager.R
import portafolio.apps.passwordmanager.datamodel.Contrasenia
import portafolio.apps.passwordmanager.formactivities.FormContrasenia

class ViewContrasenia :
    AppCompatActivity(),
    View.OnClickListener {

    private lateinit var asunto: TextView
    //private lateinit var asuntoBtn: ImageButton
    private lateinit var contrasenia: TextView
   // private lateinit var contraseniaBtn: ImageButton
    private lateinit var back: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_contrasenia)
        initComponents()
    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {
                R.id.back -> {
                    onBackPressed()
                }
                R.id.editar -> {
                    if (intent.getSerializableExtra("contraseniaupdated") != null) {
                        val c = intent.getSerializableExtra("contraseniaupdated") as? Contrasenia
                        startActivity(Intent(this, FormContrasenia::class.java).apply {
                            putExtra("contraseniaupdated", c)
                            putExtra("username", c!!.getNomusuario())
                        })
                    } else {
                        val c = intent.getSerializableExtra("contrasenia") as? Contrasenia
                        startActivity(Intent(this, FormContrasenia::class.java).apply {
                            putExtra("contrasenia", c)
                            putExtra("username", c!!.getNomusuario())
                        })
                    }
                    finish()
                }
            }
        }
    }

    private fun setComponents(c: Contrasenia) {
        asunto.text = c.getAsunto()
        contrasenia.text = c.getContrasenia()
    }

    private fun initComponents() {
        asunto = findViewById(R.id.asunto)
        contrasenia = findViewById(R.id.contrasenia)
       // asuntoBtn = findViewById(R.id.asuntoBtn)
       // contraseniaBtn = findViewById(R.id.contraseniaBtn)
        back = findViewById(R.id.back)
        val editar = findViewById<ImageButton>(R.id.editar)

        val co = intent.getSerializableExtra("contrasenia") as? Contrasenia
        if (co != null) {
            setComponents(co)
        }
        val coUpdated = intent.getSerializableExtra("contraseniaupdated") as? Contrasenia
        if (coUpdated != null) {
            setComponents(coUpdated)
        }
        //asuntoBtn.setOnClickListener(this)
        //contraseniaBtn.setOnClickListener(this)
        back.setOnClickListener(this)
        editar.setOnClickListener(this)
    }
}