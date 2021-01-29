package portafolio.apps.passwordmanager.activities

import android.content.Intent
import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import portafolio.apps.passwordmanager.database.DBController
import portafolio.apps.passwordmanager.R
import portafolio.apps.passwordmanager.fragments.SignUpFragment

class MainActivity : AppCompatActivity(), View.OnClickListener {

    var user: EditText? = null
    var pass: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val loginBtn: Button = findViewById(R.id.loginBtn)
        val registerBtn: TextView = findViewById(R.id.registrarBtn)

        user = findViewById(R.id.usuario)
        pass = findViewById(R.id.contra)
        loginBtn.setOnClickListener(this)
        registerBtn.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {
                R.id.registrarBtn -> {
                    val sf = SignUpFragment()
                    sf.show(supportFragmentManager, "Registrar")
                }
                R.id.loginBtn -> {
                    val db = DBController(applicationContext)
                    if (db.findUsuario(user?.text.toString(), pass?.text.toString())) {
                        nextActivity()
                    } else {
                        incorrectUser()
                    }
                }
            }
        }
    }

    private fun nextActivity() {
        startActivity(Intent(this, HomeTabActivity::class.java).apply {
            putExtra("username", user?.text.toString())
        })
    }

    private fun incorrectUser() {
        Toast.makeText(
            applicationContext,
            "Usuario o contraseña equivocados",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun screenWidth(): Int {
        return Resources.getSystem().displayMetrics.widthPixels
    }

    private fun screenHeight(): Int {
        return Resources.getSystem().displayMetrics.heightPixels
    }
}