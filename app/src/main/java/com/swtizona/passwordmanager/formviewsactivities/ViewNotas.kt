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
import com.swtizona.passwordmanager.datamodel.Nota
import com.swtizona.passwordmanager.datamodel.Usuario
import com.swtizona.passwordmanager.formactivities.FormNota

class ViewNotas :
    AppCompatActivity(),
    View.OnClickListener {

    private lateinit var titulo: TextView
    private lateinit var body: TextView
    private lateinit var back: ImageButton
    private var userIntent: Usuario? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_notas)
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
                    if (intent.getSerializableExtra("notaupdated") != null) {
                        val c = intent.getSerializableExtra("notaupdated") as? Nota
                        startActivity(Intent(this, FormNota::class.java).apply {
                            putExtra("notaupdated", c)
                            putExtra("username", c!!.getNomusuario())
                            putExtra("userObject", userIntent)
                        })
                    } else {
                        val c = intent.getSerializableExtra("nota") as? Nota
                        startActivity(Intent(this, FormNota::class.java).apply {
                            putExtra("nota", c)
                            putExtra("username", c!!.getNomusuario())
                            putExtra("userObject", userIntent)
                        })
                    }
                    finish()
                }
                R.id.notaBtn -> {
                    copy(body.text.toString())
                }
            }
        }
    }

    private fun copy(text: String) {
        val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip: ClipData = ClipData.newPlainText("simple text", text)
        Toast.makeText(
            applicationContext,
            "la nota completa ha sido copiada en el clipboard",
            Toast.LENGTH_SHORT
        ).show()
        clipboard.setPrimaryClip(clip)
    }

    private fun setComponents(n: Nota) {
        titulo.text = n.getAsunto()
        body.text = n.getNota()
    }

    private fun initComponents() {
        titulo = findViewById(R.id.titulo)
        body = findViewById(R.id.body)
        back = findViewById(R.id.back)
        val editar = findViewById<ImageButton>(R.id.editar)
        val btnNota = findViewById<ImageButton>(R.id.notaBtn)

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
        back.setOnClickListener(this)
        btnNota.setOnClickListener(this)
    }
}