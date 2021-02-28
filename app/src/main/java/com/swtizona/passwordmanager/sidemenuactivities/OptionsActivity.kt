package com.swtizona.passwordmanager.sidemenuactivities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import com.swtizona.passwordmanager.R
import com.swtizona.passwordmanager.mainactivities.HomeTabActivity
import com.swtizona.passwordmanager.database.DBController
import com.swtizona.passwordmanager.datamodel.Usuario
import com.swtizona.passwordmanager.alertfragments.ConfirmUserDialog

class OptionsActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var nombreTxt: TextView
    private lateinit var contraseniaTxt: TextView
    private lateinit var checkBox: CheckBox
    private var usuario: Usuario? = null

    companion object {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_options)
        initComponents()
    }

    override fun onBackPressed() {
        val db = DBController(applicationContext)
        var keepSigned = 0
        if (checkBox.isChecked) {
            keepSigned = 1
        }
        db.toggleSigned(nombreTxt.text.toString(), keepSigned)
        db.close()
        startActivity(Intent(this, HomeTabActivity::class.java).apply {
            putExtra("userObject", usuario)
        })
        finish()
    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {
                R.id.checkBox -> {
                }
                R.id.editar -> {
                    ConfirmUserDialog(usuario!!, true).show(supportFragmentManager, "Verificación")
                }
                R.id.cancel -> {
                    onBackPressed()
                }
                R.id.eliminar -> {
                    ConfirmUserDialog(usuario!!, false).show(supportFragmentManager, "Verificación")
                }
            }
        }
    }

    private fun initComponents() {
        nombreTxt = findViewById(R.id.nombreTxt)
        contraseniaTxt = findViewById(R.id.contraseniaTxt)
        checkBox = findViewById(R.id.checkBox)

        val editar = findViewById<Button>(R.id.editar)
        val cancel = findViewById<Button>(R.id.cancel)
        val eliminar = findViewById<Button>(R.id.eliminar)

        usuario = intent.getSerializableExtra("userObject") as? Usuario
        nombreTxt.text = (usuario!!.getNombre())
        contraseniaTxt.text = (usuario!!.getContrasenia())
        checkBox.isChecked = usuario!!.getChecked() != 0

        checkBox.setOnClickListener(this)
        editar.setOnClickListener(this)
        cancel.setOnClickListener(this)
        eliminar.setOnClickListener(this)
    }
}