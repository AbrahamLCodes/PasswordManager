package portafolio.apps.passwordmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val loginBtn: Button = findViewById(R.id.loginBtn)
        val registerBtn: TextView = findViewById(R.id.registrarBtn)

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
                    Toast.makeText(
                            applicationContext,
                            "Accion Login en Construccion",
                            Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}