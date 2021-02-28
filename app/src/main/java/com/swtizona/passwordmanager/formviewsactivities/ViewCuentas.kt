package com.swtizona.passwordmanager.formviewsactivities

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import com.swtizona.passwordmanager.R
import com.swtizona.passwordmanager.mainactivities.HomeTabActivity
import com.swtizona.passwordmanager.datamodel.Cuenta
import com.swtizona.passwordmanager.datamodel.Usuario
import com.swtizona.passwordmanager.formactivities.FormCuenta

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
    private var userIntent: Usuario? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_cuentas)
        userIntent = intent.getSerializableExtra("userObject") as? Usuario
        initComponents()
    }

    override fun onPause() {
        super.onPause()
        if (userIntent!!.getChecked() == 1) {
            finish()
        }
    }

    override fun onBackPressed() {
        startActivity(Intent(this, HomeTabActivity::class.java).apply {
            putExtra("userObject", userIntent)
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
                    if (intent.getSerializableExtra("cuentaupdated") != null) {
                        val c = intent.getSerializableExtra("cuentaupdated") as? Cuenta
                        startActivity(Intent(this, FormCuenta::class.java).apply {
                            putExtra("cuentaupdated", c)
                            putExtra("username", c!!.getNomUsuario())
                            putExtra("userObject", userIntent)
                        })
                    } else {
                        val c = intent.getSerializableExtra("cuenta") as? Cuenta
                        startActivity(Intent(this, FormCuenta::class.java).apply {
                            putExtra("cuenta", c)
                            putExtra("username", c!!.getNomUsuario())
                            putExtra("userObject", userIntent)
                        })
                    }
                    finish()
                }
                R.id.sitioBtn -> {
                    copy(sitio.text.toString())
                }
                R.id.usuarioBtn -> {
                    copy(usuario.text.toString())
                }
                R.id.contraseniaBtn -> {
                    copy(contrasenia.text.toString())
                }
                R.id.correoBtn -> {
                    copy(correo.text.toString())
                }
            }
        }
    }

    private fun copy(text: String) {
        val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip: ClipData = ClipData.newPlainText("simple text", text)
        Toast.makeText(
            applicationContext,
            "'" + text + "' copiado en el clipboard",
            Toast.LENGTH_SHORT
        ).show()
        clipboard.setPrimaryClip(clip)
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
        val editar = findViewById<ImageButton>(R.id.editar)
        val categoria = findViewById<TextView>(R.id.categoria)

        val cuenta = intent.getSerializableExtra("cuenta") as? Cuenta

        if (cuenta != null) {
            sitio.text = cuenta.getWebsite()
            usuario.text = cuenta.getNickname()
            contrasenia.text = cuenta.getContrasenia()
            correo.text = cuenta.getCorreo()
            categoria.text = cuenta.getCategoria()
        }

        val cuentaUpdated = intent.getSerializableExtra("cuentaupdated") as? Cuenta
        if (cuentaUpdated != null) {
            sitio.text = cuentaUpdated.getWebsite()
            usuario.text = cuentaUpdated.getNickname()
            contrasenia.text = cuentaUpdated.getContrasenia()
            correo.text = cuentaUpdated.getCorreo()
            categoria.text = cuentaUpdated.getCategoria()
        }

        sitioBtn.setOnClickListener(this)
        usuarioBtn.setOnClickListener(this)
        contraseniaBtn.setOnClickListener(this)
        correoBtn.setOnClickListener(this)
        back.setOnClickListener(this)
        editar.setOnClickListener(this)
    }

}