package portafolio.apps.passwordmanager.Activitys

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import portafolio.apps.passwordmanager.DBController
import portafolio.apps.passwordmanager.R
import portafolio.apps.passwordmanager.SignUpFragment

class MainActivity : AppCompatActivity(), View.OnClickListener {

    var user: EditText? = null
    var pass: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val loginBtn: ImageButton = findViewById(R.id.loginBtn)
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
                    if (db.findUser(user?.text.toString(), pass?.text.toString())) {
                        nextActivity()
                    } else {
                        incorrectUser()
                    }
                }
            }
        }
    }

    private fun nextActivity() {
        startActivity(Intent(this, HomeScreen::class.java).apply {
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
}