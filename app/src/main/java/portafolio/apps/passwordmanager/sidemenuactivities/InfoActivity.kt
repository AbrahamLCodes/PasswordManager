package portafolio.apps.passwordmanager.sidemenuactivities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import portafolio.apps.passwordmanager.R
import portafolio.apps.passwordmanager.activities.HomeTabActivity

class InfoActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)
        initComponents()
    }

    override fun onStop() {
        super.onStop()
        if (HomeTabActivity.usuarioIntent!!.getChecked() == 1) {
            finish()
        }
    }

    override fun onBackPressed() {
        startActivity(Intent(this, HomeTabActivity::class.java).apply {
            putExtra("userObject", intent.getSerializableExtra("userObject"))
        })
        finish()
    }

    private fun initComponents(){
        val back = findViewById<ImageButton>(R.id.back)
        back.setOnClickListener{
            onBackPressed()
        }
    }

}