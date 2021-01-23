package portafolio.apps.passwordmanager.formviewsactivities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import portafolio.apps.passwordmanager.R
import portafolio.apps.passwordmanager.datamodel.Contrasenia
import portafolio.apps.passwordmanager.datamodel.Correo

class ViewCorreo :
    AppCompatActivity(),
    View.OnClickListener {


    private lateinit var nombre: TextView
    private lateinit var correo: TextView
    private lateinit var contrasenia: TextView
    private lateinit var nombreBtn: ImageButton
    private lateinit var correoBtn: ImageButton
    private lateinit var contraseniaBtn: ImageButton
    private lateinit var back: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_correo)
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
        nombre = findViewById(R.id.nombre)
        correo = findViewById(R.id.correo)
        contrasenia = findViewById(R.id.contrasenia)
        nombreBtn = findViewById(R.id.nombreBtn)
        correoBtn = findViewById(R.id.correoBtn)
        contraseniaBtn = findViewById(R.id.contraseniaBtn)
        back = findViewById(R.id.back)

        val correoO = intent.getSerializableExtra("correo") as? Correo
        if (correoO != null) {
            nombre.text = correoO.getNombre()
            correo.text = correoO.getCorreo()
            contrasenia.text = correoO.getContrasenia()
        }

        back.setOnClickListener(this)
        nombreBtn.setOnClickListener(this)
        correoBtn.setOnClickListener(this)
        contraseniaBtn.setOnClickListener(this)
    }
}