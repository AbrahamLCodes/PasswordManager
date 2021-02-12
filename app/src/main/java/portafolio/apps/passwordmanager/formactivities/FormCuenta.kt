package portafolio.apps.passwordmanager.formactivities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.textfield.TextInputLayout
import portafolio.apps.passwordmanager.R
import portafolio.apps.passwordmanager.database.DBController
import portafolio.apps.passwordmanager.datamodel.Cuenta
import portafolio.apps.passwordmanager.formviewsactivities.ViewCuentas
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class FormCuenta :
    AppCompatActivity(),
    View.OnClickListener {

    private lateinit var categoriaDrop: TextInputLayout
    private lateinit var correoDrop: TextInputLayout
    private lateinit var website: EditText
    private lateinit var cuentaName: EditText
    private lateinit var contrasenia: EditText
    private lateinit var contrasenia1: EditText
    private lateinit var guardarBtn: Button
    private var insert = false
    private val categoriaItems = listOf(
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

    override fun onBackPressed() {
        if (insert) {
            super.onBackPressed()
        } else {
            val co = intent.getSerializableExtra("cuenta") as? Cuenta
            val co2 = intent.getSerializableExtra("cuentaupdated") as? Cuenta
            if (co == null) {
                goToView(co2!!)
            } else if (co2 == null) {
                goToView(co)
            }

        }
    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {
                R.id.guardarBtn -> {
                    if (checkFields() && checkPasswords()) {
                        if (insert) {
                            save()
                        } else {
                            update()
                        }
                    }
                }
                R.id.cancelarBtn -> {
                    onBackPressed()
                }
            }
        }
    }

    private fun update() {
        val db = DBController(applicationContext)
        val co = intent.getSerializableExtra("cuenta") as? Cuenta
        val co2 = intent.getSerializableExtra("cuentaupdated") as? Cuenta
        var good = true

        try {
            if (co == null) {
                db.updateCuenta(
                    co2!!.getNomUsuario(),
                    co2.getCorreo(),
                    correoDrop.editText!!.text.toString(),
                    co2.getWebsite(),
                    website.text.toString(),
                    contrasenia.text.toString(),
                    categoriaDrop.editText?.text.toString(),
                    cuentaName.text.toString()
                )
            } else if (co2 == null) {
                db.updateCuenta(
                    co.getNomUsuario(),
                    co.getCorreo(),
                    correoDrop.editText!!.text.toString(),
                    co.getWebsite(),
                    website.text.toString(),
                    contrasenia.text.toString(),
                    categoriaDrop.editText!!.text.toString(),
                    cuentaName.text.toString()
                )
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            good = false
        }

        if (good) {
            Toast.makeText(
                applicationContext,
                "La cuenta ha sido actualizada correctamente",
                Toast.LENGTH_SHORT
            ).show()
            if (co == null) {
                goToView(co2!!)
            } else if (co2 == null) {
                goToView(co)
            }
        }
        db.close()
    }

    private fun goToView(c: Cuenta) {
        val intent = Intent(this, ViewCuentas::class.java)
        intent.apply {
            putExtra(
                "cuentaupdated", Cuenta(
                    c.getNomUsuario(),
                    correoDrop.editText!!.text.toString(),
                    website.text.toString(),
                    contrasenia.text.toString(),
                    categoriaDrop.editText!!.text.toString(),
                    cuentaName.text.toString(),
                    c.getFecha()
                )
            )
        }
        startActivity(intent)
        finish()
    }

    private fun save() {
        val db = DBController(applicationContext)
        var correcto = true
        try {
            db.insertCuenta(
                intent.getStringExtra("username")!!,
                correoDrop.editText!!.text.toString(),
                website.text.toString(),
                contrasenia.text.toString(),
                categoriaDrop.editText!!.text.toString(),
                cuentaName.text.toString(),
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
        db.close()
    }

    private fun checkFields(): Boolean {
        if (categoriaDrop.editText!!.text.toString().equals("")) {
            Toast.makeText(
                applicationContext,
                "Elige una categoría",
                Toast.LENGTH_SHORT
            ).show()
            return false
        } else if (website.text.toString().equals("")) {
            Toast.makeText(
                applicationContext,
                "Introduce el sitio/plataforma",
                Toast.LENGTH_SHORT
            ).show()
            website!!.requestFocus()
            return false
        } else if (cuentaName.text.toString().equals("")) {
            Toast.makeText(
                applicationContext,
                "Introduce el nombre de la cuenta",
                Toast.LENGTH_SHORT
            ).show()
            cuentaName.requestFocus()
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
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        return sdf.format(Date()).replace("/", "-")
    }

    private fun checkPasswords(): Boolean {
        val correcto: Boolean?
        if (contrasenia.text.toString().equals(contrasenia1.text.toString())) {
            correcto = true
        } else {
            correcto = false
            Toast.makeText(
                applicationContext,
                "Las contraseñas no coinciden",
                Toast.LENGTH_SHORT
            ).show()
        }
        return correcto
    }

    private fun setComponents(c: Cuenta) {
        categoriaDrop.editText!!.setText(c.getCategoria())
        website.setText(c.getWebsite())
        cuentaName.setText(c.getNickname())
        contrasenia.setText(c.getContrasenia())
        contrasenia1.setText(c.getContrasenia())
        correoDrop.editText!!.setText(c.getCorreo())
        guardarBtn.setText("Editar")
        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        toolbar.setTitle("Editar Cuenta")

    }

    private fun initComponents() {
        categoriaDrop = findViewById(R.id.categoriaDrop)
        website = findViewById(R.id.website)
        cuentaName = findViewById(R.id.cuentaName)
        contrasenia = findViewById(R.id.contrasenia)
        contrasenia1 = findViewById(R.id.contrasenia1)
        correoDrop = findViewById(R.id.correoDrop)
        guardarBtn = findViewById(R.id.guardarBtn)
        val cancelarBtn: Button = findViewById(R.id.cancelarBtn)
        guardarBtn.setOnClickListener(this)
        cancelarBtn.setOnClickListener(this)

        //Adapter for categoriaDrop
        val adapter = ArrayAdapter(this, R.layout.item_dropdown_menu, categoriaItems)
        (categoriaDrop.editText as? AutoCompleteTextView)?.setAdapter(adapter)
        categoriaDrop.editText!!.isEnabled = false

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
        (correoDrop.editText as? AutoCompleteTextView)?.setAdapter(adapterCorreo)
        correoDrop.editText!!.isEnabled = false

        val cu = intent.getSerializableExtra("cuenta") as? Cuenta
        val cu2 = intent.getSerializableExtra("cuentaupdated") as? Cuenta

        if (cu != null || cu2 != null) {
            insert = false
            categoriaDrop.editText!!.isEnabled = true
            correoDrop.editText!!.isEnabled = true
            if (cu == null) {
                setComponents(cu2!!)
            } else if (cu2 == null) {
                setComponents(cu)
            }
        } else {
            insert = true
        }

    }
}