package com.swtizona.passwordmanager.formactivities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.textfield.TextInputLayout
import com.swtizona.passwordmanager.R
import com.swtizona.passwordmanager.mainactivities.HomeTabActivity
import com.swtizona.passwordmanager.database.DBController
import com.swtizona.passwordmanager.datamodel.Cuenta
import com.swtizona.passwordmanager.datamodel.Usuario
import com.swtizona.passwordmanager.formviewsactivities.ViewCuentas
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
    private var userIntent: Usuario? = null
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
        userIntent = intent.getSerializableExtra("userObject") as? Usuario
        initComponents()
    }

    override fun onStop() {
        super.onStop()
        if (userIntent!!.getChecked() == 1) {
            finish()
        }
    }

    override fun onBackPressed() {
        if (insert) {
            startActivity(Intent(this, HomeTabActivity::class.java).apply {
                putExtra("userObject", userIntent)
            })
            finish()
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
            makeToast(
                "Ya se ha registrado el correo '" + correoDrop.editText!!.text.toString() + "' a la " +
                        "cuenta de '" + website.text.toString() + "'"
            )
            good = false
        }

        if (good) {
            Toast.makeText(
                applicationContext,
                "La cuenta ha sido actualizada correctamente",
                Toast.LENGTH_LONG
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
            putExtra("userObject", userIntent)
        }
        startActivity(intent)
        finish()
    }

    private fun save() {
        val db = DBController(applicationContext)
        var correcto = true
        val usuario = intent.getSerializableExtra("userObject") as? Usuario
        try {
            db.insertCuenta(
                usuario!!.getNombre(),
                correoDrop.editText!!.text.toString(),
                website.text.toString(),
                contrasenia.text.toString(),
                categoriaDrop.editText!!.text.toString(),
                cuentaName.text.toString(),
                getStringDate(),
            )

        } catch (ex: Exception) {
            makeToast(
                "Ya se ha registrado el correo '" + correoDrop.editText!!.text.toString() + "' a la " +
                        "cuenta de '" + website.text.toString() + "'"
            )
            ex.printStackTrace()
            correcto = false
        }

        if (correcto) {
            makeToast(
                "la cuenta '" + cuentaName.text.toString() + "' de '" +
                        website.text.toString() + "' ha sido registrada al correo '" + correoDrop.editText!!.text.toString() + "'"
            )
            db.close()
            onBackPressed()
        }
        db.close()
    }

    private fun checkFields(): Boolean {
        if (categoriaDrop.editText!!.text.toString() == "") {
            makeToast("Elige una categoría")
            return false
        } else if (website.text.toString() == "") {
            makeToast("Introduce el sitio/plataforma")
            website.requestFocus()
            return false
        } else if (cuentaName.text.toString() == "") {
            makeToast("Introduce el nombre de la cuenta")
            cuentaName.requestFocus()
            return false
        } else if (contrasenia.text.toString() == "") {
            makeToast("Introduce una contraseña")
            contrasenia.requestFocus()
            return false
        } else if (contrasenia1.text.toString() == "") {
            makeToast("Vuelve a introducir la contraseña")
            contrasenia1.requestFocus()
            return false
        } else if (correoDrop.editText!!.text.toString() == "") {
            makeToast("Elige un correo")
            return false
        } else {
            return true
        }
    }

    private fun makeToast(mensaje: String) {
        Toast.makeText(applicationContext, mensaje, Toast.LENGTH_SHORT).show()
    }

    private fun getStringDate(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        return sdf.format(Date()).replace("/", "-")
    }

    private fun checkPasswords(): Boolean {
        val correcto: Boolean?
        if (contrasenia.text.toString() == contrasenia1.text.toString()) {
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
        guardarBtn.text = "Editar"
        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        toolbar.title = "Editar Cuenta"

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

        //Adapter for correoDrop
        // Getting rows from CORREOS database table
        val db = DBController(applicationContext)
        val list = db.customCorreoSelect("NOMUSUARIO", intent.getStringExtra("username")!!)
        // Creating an empty array of strings with size of rows in CORREOS
        val correoItems = arrayOfNulls<String>(list.size)
        // Putting values from DB into the array of strings
        for ((i, correo) in list.withIndex()) {//It
            correoItems[i] = correo.getCorreo()
        }
        val adapterCorreo = ArrayAdapter(this, R.layout.item_dropdown_menu, correoItems)
        (correoDrop.editText as? AutoCompleteTextView)?.setAdapter(adapterCorreo)

        val cu = intent.getSerializableExtra("cuenta") as? Cuenta
        val cu2 = intent.getSerializableExtra("cuentaupdated") as? Cuenta

        if (cu != null || cu2 != null) {
            insert = false
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