package portafolio.apps.passwordmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

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
        Toast.makeText(
                applicationContext,
                "Usuario encontrado",
                Toast.LENGTH_SHORT
        ).show()
    }

    private fun incorrectUser() {
        Toast.makeText(
                applicationContext,
                "Usuario o contraseña equivocados",
                Toast.LENGTH_SHORT
        ).show()
    }
}