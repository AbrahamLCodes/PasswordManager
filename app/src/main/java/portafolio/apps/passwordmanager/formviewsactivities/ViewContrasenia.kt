package portafolio.apps.passwordmanager.formviewsactivities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import portafolio.apps.passwordmanager.R
import portafolio.apps.passwordmanager.datamodel.Contrasenia
import portafolio.apps.passwordmanager.datamodel.Correo

class ViewContrasenia :
    AppCompatActivity(),
    View.OnClickListener {

    private lateinit var asunto: TextView
    private lateinit var asuntoBtn: ImageButton
    private lateinit var contrasenia: TextView
    private lateinit var contraseniaBtn: ImageButton
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
            }
        }
    }

    private fun initComponents() {
        asunto = findViewById(R.id.asunto)
        contrasenia = findViewById(R.id.contrasenia)
        asuntoBtn = findViewById(R.id.asuntoBtn)
        contraseniaBtn = findViewById(R.id.contraseniaBtn)
        back = findViewById(R.id.back)

        val contra = intent.getSerializableExtra("contrasenia") as? Contrasenia

        if (contra != null) {
            asunto.text = contra.getAsunto()
            contrasenia.text = contra.getContrasenia()
        }

        asuntoBtn.setOnClickListener(this)
        contraseniaBtn.setOnClickListener(this)
        back.setOnClickListener(this)
    }
}