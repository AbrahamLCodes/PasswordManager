package portafolio.apps.passwordmanager.formviewsactivities

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import portafolio.apps.passwordmanager.R
import portafolio.apps.passwordmanager.activities.HomeTabActivity
import portafolio.apps.passwordmanager.datamodel.Contrasenia
import portafolio.apps.passwordmanager.datamodel.Correo
import portafolio.apps.passwordmanager.datamodel.Usuario
import portafolio.apps.passwordmanager.formactivities.FormCorreo

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
    private lateinit var editar: ImageButton

    companion object {
        var userIntent: Usuario? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_correo)

        userIntent = intent.getSerializableExtra("userObject") as? Usuario
        initComponents()
    }

    override fun onStop() {
        super.onStop()
        if(userIntent!!.getChecked() == 1){
            finish()
        }
    }

    override fun onBackPressed() {
        startActivity(Intent(this, HomeTabActivity::class.java).apply {
            putExtra("userObject", userIntent!!)
        })
        finish()
    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {
                R.id.back -> {
                    onBackPressed()
                }
                R.id.editar -> {
                    if (intent.getSerializableExtra("correoupdated") != null) {
                        startActivity(Intent(this, FormCorreo::class.java).apply {
                            putExtra("correoupdated", intent.getSerializableExtra("correoupdated"))
                            putExtra("userObject", userIntent)
                            putExtra("userObject", userIntent)
                        })
                    } else {
                        startActivity(Intent(this, FormCorreo::class.java).apply {
                            putExtra("correo", intent.getSerializableExtra("correo"))
                            putExtra("userObject", userIntent)
                            putExtra("userObject", userIntent)
                        })
                    }
                    finish()
                }
                R.id.nombreBtn -> {
                    copy(nombre.text.toString())
                }
                R.id.correoBtn -> {
                    copy(correo.text.toString())
                }
                R.id.contraseniaBtn -> {
                    copy(contrasenia.text.toString())
                }
            }
        }
    }

    private fun copy(text: String){
        val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip: ClipData = ClipData.newPlainText("simple text",text)
        Toast.makeText(
            applicationContext,
            "'"+text+"' copiado en el clipboard",
            Toast.LENGTH_SHORT
        ).show()
        clipboard.setPrimaryClip(clip)
    }

    private fun initComponents() {
        nombre = findViewById(R.id.nombre)
        correo = findViewById(R.id.correo)
        contrasenia = findViewById(R.id.contrasenia)
        nombreBtn = findViewById(R.id.nombreBtn)
        correoBtn = findViewById(R.id.correoBtn)
        contraseniaBtn = findViewById(R.id.contraseniaBtn)
        back = findViewById(R.id.back)
        editar = findViewById(R.id.editar)

        val correoO = intent.getSerializableExtra("correo") as? Correo
        if (correoO != null) {
            nombre.text = correoO.getNombre()
            correo.text = correoO.getCorreo()
            contrasenia.text = correoO.getContrasenia()
        }

        val correoUpdated = intent.getSerializableExtra("correoupdated") as? Correo
        if (correoUpdated != null) {
            nombre.text = correoUpdated.getNombre()
            correo.text = correoUpdated.getCorreo()
            contrasenia.text = correoUpdated.getContrasenia()
        }

        back.setOnClickListener(this)
        nombreBtn.setOnClickListener(this)
        correoBtn.setOnClickListener(this)
        contraseniaBtn.setOnClickListener(this)
        editar.setOnClickListener(this)
    }
}