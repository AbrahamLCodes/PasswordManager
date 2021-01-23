package portafolio.apps.passwordmanager.formviewsactivities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import portafolio.apps.passwordmanager.R
import portafolio.apps.passwordmanager.datamodel.Correo
import portafolio.apps.passwordmanager.datamodel.Cuenta

class ViewCuentas :
    AppCompatActivity(),
    View.OnClickListener {

    private lateinit var sitio: TextView
    private lateinit var sitioBtn: ImageButton
    private lateinit var usuario: TextView
    private lateinit var usuarioBtn: ImageButton
    private lateinit var contrasenia: TextView
    private lateinit var contraseniaBtn: ImageButton
    private lateinit var correo: TextView
    private lateinit var correoBtn: ImageButton
    private lateinit var back: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_cuentas)
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
        sitio = findViewById(R.id.sitio)
        sitioBtn = findViewById(R.id.sitioBtn)
        usuario = findViewById(R.id.usuario)
        usuarioBtn = findViewById(R.id.usuarioBtn)
        contrasenia = findViewById(R.id.contrasenia)
        contraseniaBtn = findViewById(R.id.contraseniaBtn)
        correo = findViewById(R.id.correo)
        correoBtn = findViewById(R.id.correoBtn)
        back = findViewById(R.id.back)

        val cuenta = intent.getSerializableExtra("cuenta") as? Cuenta
        if (cuenta != null) {
            sitio.text = cuenta.getWebsite()
            usuario.text = cuenta.getNickname()
            contrasenia.text = cuenta.getContrasenia()
            correo.text = cuenta.getCorreo()
        }

        sitioBtn.setOnClickListener(this)
        usuarioBtn.setOnClickListener(this)
        contraseniaBtn.setOnClickListener(this)
        correoBtn.setOnClickListener(this)
        back.setOnClickListener(this)
    }

}