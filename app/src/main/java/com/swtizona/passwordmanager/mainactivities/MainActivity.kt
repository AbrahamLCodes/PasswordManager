package com.swtizona.passwordmanager.mainactivities

import android.content.Intent
import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.swtizona.passwordmanager.database.DBController
import com.swtizona.passwordmanager.R
import com.swtizona.passwordmanager.formactivities.FormUsuario

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var user: EditText
    private lateinit var pass: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initComponents()
    }

    override fun onResume() {
        super.onResume()
        user.setText("")
        pass.setText("")
    }

    override fun onBackPressed() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Salir de la App")
        builder.setMessage("¿Seguro que desea salir de la App?")

        builder.setPositiveButton("Salir") { _, _ ->
            moveTaskToBack(true)
        }

        builder.setNegativeButton("Cancelar") { _, _ ->
            //Do no thing
        }

        builder.show()
    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {
                R.id.registrarBtn -> {
                    startActivity(Intent(this, FormUsuario::class.java))
                }
                R.id.loginBtn -> {
                    val db = DBController(applicationContext)
                    if (db.findUsuario(user.text.toString(), pass.text.toString())) {
                        nextActivity()
                    } else {
                        incorrectUser()
                    }
                    db.close()
                }
            }
        }
    }

    private fun nextActivity() {
        val db = DBController(applicationContext)
        startActivity(Intent(this, HomeTabActivity::class.java).apply {
            putExtra("username", user.text.toString())
            putExtra("userObject", db.selectUsuario(user.text.toString(), pass.text.toString()))
        })
    }

    private fun incorrectUser() {
        Toast.makeText(
            applicationContext,
            "Usuario o contraseña equivocados",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun initComponents() {
        val loginBtn: Button = findViewById(R.id.loginBtn)
        val registerBtn: TextView = findViewById(R.id.registrarBtn)

        user = findViewById(R.id.usuario)
        pass = findViewById(R.id.contra)

        loginBtn.setOnClickListener(this)
        registerBtn.setOnClickListener(this)
    }

    private fun screenWidth(): Int {
        return Resources.getSystem().displayMetrics.widthPixels
    }

    private fun screenHeight(): Int {
        return Resources.getSystem().displayMetrics.heightPixels
    }
}