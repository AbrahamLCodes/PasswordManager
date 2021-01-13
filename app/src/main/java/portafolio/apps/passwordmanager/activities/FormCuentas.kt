package portafolio.apps.passwordmanager.activities

import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout
import portafolio.apps.passwordmanager.R

class FormCuentas : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_cuentas)
        val textField  = findViewById<TextInputLayout>(R.id.menuDrop)
        val textField2  = findViewById<TextInputLayout>(R.id.menuDrop2)
        val items = listOf("red social", "Plataforma de juego", "otro")
        val adapter = ArrayAdapter(this, R.layout.item_dropdown_menu, items)
        (textField.editText as? AutoCompleteTextView)?.setAdapter(adapter)
        (textField2.editText as? AutoCompleteTextView)?.setAdapter(adapter)
        Log.wtf("text", "" + adapter.getPosition("red social"))

    }

}