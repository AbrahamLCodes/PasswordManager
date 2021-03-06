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
import com.swtizona.passwordmanager.datamodel.Contrasenia
import com.swtizona.passwordmanager.datamodel.Usuario
import com.swtizona.passwordmanager.formactivities.FormContrasenia

class ViewContrasenia :
    AppCompatActivity(),
    View.OnClickListener {

    private lateinit var asunto: TextView
    private lateinit var asuntoBtn: ImageButton
    private lateinit var contrasenia: TextView
    private lateinit var contraseniaBtn: ImageButton
    private lateinit var back: ImageButton
    private var userIntent: Usuario? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_contrasenia)
        userIntent = intent.getSerializableExtra("userObject") as? Usuario
        initComponents()
    }

    override fun onPause() {
        super.onPause()
        if(userIntent!!.getChecked() == 1){
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
                    if (intent.getSerializableExtra("contraseniaupdated") != null) {
                        val c = intent.getSerializableExtra("contraseniaupdated") as? Contrasenia
                        startActivity(Intent(this, FormContrasenia::class.java).apply {
                            putExtra("contraseniaupdated", c)
                            putExtra("username", c!!.getNomusuario())
                            putExtra("userObject", userIntent)
                        })
                    } else {
                        val c = intent.getSerializableExtra("contrasenia") as? Contrasenia
                        startActivity(Intent(this, FormContrasenia::class.java).apply {
                            putExtra("contrasenia", c)
                            putExtra("username", c!!.getNomusuario())
                            putExtra("userObject", userIntent)
                        })
                    }
                    finish()
                }
                R.id.asuntoBtn -> {
                    copy(asunto.text.toString())
                }
                R.id.contraseniaBtn -> {
                    copy(contrasenia.text.toString())
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

    private fun setComponents(c: Contrasenia) {
        asunto.text = c.getAsunto()
        contrasenia.text = c.getContrasenia()
    }

    private fun initComponents() {
        asunto = findViewById(R.id.asunto)
        contrasenia = findViewById(R.id.contrasenia)
        asuntoBtn = findViewById(R.id.asuntoBtn)
        contraseniaBtn = findViewById(R.id.contraseniaBtn)
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
        asuntoBtn.setOnClickListener(this)
        contraseniaBtn.setOnClickListener(this)
        back.setOnClickListener(this)
        editar.setOnClickListener(this)
    }
}