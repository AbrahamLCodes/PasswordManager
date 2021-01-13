package portafolio.apps.passwordmanager.formactivities

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout
import portafolio.apps.passwordmanager.R
import portafolio.apps.passwordmanager.database.DBController
import portafolio.apps.passwordmanager.datamodel.Correo
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class FormCuenta :
    AppCompatActivity(),
    View.OnClickListener {

    var categoriaDrop: TextInputLayout? = null
    var correoDrop: TextInputLayout? = null
    var website: EditText? = null
    var cuentaName: EditText? = null
    var contrasenia: EditText? = null
    var contrasenia1: EditText? = null
    val categoriaItems = listOf(
        "Red Social",
        "Plataforma de Gaming",
        "Tienda Online",
        "Blog",
        "Plataforma Escolar",
        "Plataforma Laboral",
        "Otro"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_cuentas)
        initComponents()
    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {
                R.id.guardarBtn -> {
                    if (checkFields() && checkPasswords()) {
                        save()
                    }
                }
                R.id.cancelarBtn -> {
                    finish()
                }
            }
        }
    }

    private fun save() {
        val db = DBController(applicationContext)
        var correcto = true
        try {
            db.insertCuenta(
                correoDrop!!.editText!!.text.toString(),
                cuentaName!!.text.toString(),
                contrasenia!!.text.toString(),
                categoriaDrop!!.editText!!.text.toString(),
                cuentaName!!.text.toString(),
                website!!.text.toString(),
                getStringDate(),
            )

        } catch (ex: Exception) {
            Toast.makeText(
                applicationContext,
                "Ya se ha registrado el correo '" + correoDrop!!.editText!!.text.toString() + "' a la " +
                        "cuenta de '" + website!!.text.toString() + "'",
                Toast.LENGTH_SHORT
            ).show()
            ex.printStackTrace()
            correcto = false
        }

        if (correcto) {
            Toast.makeText(
                applicationContext,
                "la cuenta '" + cuentaName!!.text.toString() + "' de '" +
                        website!!.text.toString() + "' ha sido registrada al correo '" + correoDrop!!.editText!!.text.toString() + "'",
                Toast.LENGTH_LONG
            ).show()
            db.close()
            finish()
        }
    }

    private fun checkFields(): Boolean {
        if (categoriaDrop!!.editText!!.text.toString().equals("")) {
            Toast.makeText(
                applicationContext,
                "Elige una categoría",
                Toast.LENGTH_SHORT
            ).show()
            return false
        } else if (website!!.text.toString().equals("")) {
            Toast.makeText(
                applicationContext,
                "Introduce el sitio/plataforma",
                Toast.LENGTH_SHORT
            ).show()
            website!!.requestFocus()
            return false
        } else if (cuentaName!!.text.toString().equals("")) {
            Toast.makeText(
                applicationContext,
                "Introduce el nombre de la cuenta",
                Toast.LENGTH_SHORT
            ).show()
            cuentaName!!.requestFocus()
            return false
        } else if (contrasenia!!.text.toString().equals("")) {
            Toast.makeText(
                applicationContext,
                "Introduce una contraseña",
                Toast.LENGTH_SHORT
            ).show()
            contrasenia!!.requestFocus()
            return false
        } else if (contrasenia1!!.text.toString().equals("")) {
            Toast.makeText(
                applicationContext,
                "Vuelve a introducir la contraseña",
                Toast.LENGTH_SHORT
            ).show()
            contrasenia1!!.requestFocus()
            return false
        } else if (correoDrop!!.editText!!.text.toString().equals("")) {
            Toast.makeText(
                applicationContext,
                "Elige un correo",
                Toast.LENGTH_SHORT
            ).show()
            return false
        } else {
            return true
        }
    }

    private fun getStringDate(): String {
        val sdf = SimpleDateFormat("yyyy/MM/dd")
        return sdf.format(Date()).replace("/", "-")
    }

    private fun checkPasswords(): Boolean {
        var correcto:Boolean?
        if(contrasenia!!.text.toString().equals(contrasenia1!!.text.toString())){
            correcto = true
        }else{
            correcto = false
            Toast.makeText(
                applicationContext,
                "Las contraseñas no coinciden",
                Toast.LENGTH_SHORT
            ).show()
        }
        return correcto
    }

    private fun initComponents() {
        categoriaDrop = findViewById(R.id.categoriaDrop)
        website = findViewById(R.id.website)
        cuentaName = findViewById(R.id.cuentaName)
        contrasenia = findViewById(R.id.contrasenia)
        contrasenia1 = findViewById(R.id.contrasenia1)
        correoDrop = findViewById(R.id.correoDrop)
        val guardarBtn: Button = findViewById(R.id.guardarBtn)
        val cancelarBtn: Button = findViewById(R.id.cancelarBtn)
        guardarBtn.setOnClickListener(this)
        cancelarBtn.setOnClickListener(this)

        //Adapter for categoriaDrop
        val adapter = ArrayAdapter(this, R.layout.item_dropdown_menu, categoriaItems)
        (categoriaDrop!!.editText as? AutoCompleteTextView)?.setAdapter(adapter)
        categoriaDrop!!.editText!!.isEnabled = false

        //Adapter for correoDrop
        // Getting rows from CORREOS database table
        val db = DBController(applicationContext)
        var list = db.customCorreoSelect("NOMUSUARIO", intent.getStringExtra("username")!!)
        // Creating an empty array of strings with size of rows in CORREOS
        val correoItems = arrayOfNulls<String>(list.size)
        var i = 0
        // Putting values from DB into the array of strings
        for (correo in list) {//It
            correoItems[i] = correo.getCorreo()
            i++
        }
        val adapterCorreo = ArrayAdapter(this, R.layout.item_dropdown_menu, correoItems)
        (correoDrop!!.editText as? AutoCompleteTextView)?.setAdapter(adapterCorreo)
        correoDrop!!.editText!!.isEnabled = false
        Log.wtf("text", "" + adapter.getPosition("red social"))
    }
}