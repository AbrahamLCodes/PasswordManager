package portafolio.apps.passwordmanager.fragments

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatDialogFragment
import portafolio.apps.passwordmanager.R
import portafolio.apps.passwordmanager.datamodel.Usuario
import portafolio.apps.passwordmanager.formactivities.FormUsuario

class ConfirmUserDialog(userObject: Usuario) : AppCompatDialogFragment(), View.OnClickListener {

    private val userObject = userObject
    private lateinit var usuario: EditText
    private lateinit var contrasenia: EditText

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
        return inflater.inflate(R.layout.fragment_password, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initComponents(view)
    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {
                R.id.verificar -> {
                    if (verifiyUser()) {
                        startActivity(Intent(activity, FormUsuario::class.java).apply {
                            putExtra("userObject", userObject)
                        })
                        activity!!.finish()
                    } else {
                        Toast.makeText(context,"Datos incorrectos", Toast.LENGTH_SHORT).show()
                    }
                }
                R.id.cancelar -> {
                    dismiss()
                }
            }
        }
    }

    private fun verifiyUser(): Boolean {
        return usuario.text.toString() == userObject.getNombre() && contrasenia.text.toString() == userObject.getContrasenia()
    }

    private fun initComponents(v: View) {
        usuario = v.findViewById(R.id.usuario)
        contrasenia = v.findViewById(R.id.contrasenia)

        val verificar = v.findViewById<Button>(R.id.verificar)
        val cancelar = v.findViewById<Button>(R.id.cancelar)

        verificar.setOnClickListener(this)
        cancelar.setOnClickListener(this)
    }

}