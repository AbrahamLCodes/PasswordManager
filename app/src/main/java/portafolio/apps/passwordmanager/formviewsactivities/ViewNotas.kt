package portafolio.apps.passwordmanager.formviewsactivities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import portafolio.apps.passwordmanager.R
import portafolio.apps.passwordmanager.datamodel.Nota

class ViewNotas :
    AppCompatActivity(),
    View.OnClickListener {

    private lateinit var titulo: TextView
    private lateinit var tituloBtn: ImageButton
    private lateinit var body: TextView
    private lateinit var back: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_notas)
        initComponents()
    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {
                R.id.back -> {
                    onBackPressed()
                }
            }
        }
    }

    private fun initComponents() {
        titulo = findViewById(R.id.titulo)
        tituloBtn = findViewById(R.id.tituloBtn)
        body = findViewById(R.id.body)
        back = findViewById(R.id.back)

        val nota = intent.getSerializableExtra("nota") as? Nota
        if (nota != null) {
            titulo.text = nota.getAsunto()
            body.text = nota.getNota()
        }

        tituloBtn.setOnClickListener(this)
        back.setOnClickListener(this)
    }
}