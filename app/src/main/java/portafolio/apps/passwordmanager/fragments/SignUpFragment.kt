package portafolio.apps.passwordmanager.fragments

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatDialogFragment
import portafolio.apps.passwordmanager.database.DBController
import portafolio.apps.passwordmanager.R
import java.lang.Exception

class SignUpFragment : AppCompatDialogFragment(), View.OnClickListener {

    //nombre, pass1, pass2
    private var nombre: EditText? = null
    private var pass1: EditText? = null
    private var pass2: EditText? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
        return inflater.inflate(R.layout.fragment_signup, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val accpet: Button = view.findViewById(R.id.accept)
        val cancel: Button = view.findViewById(R.id.cancel)

        nombre = view.findViewById(R.id.usuario)
        pass1 = view.findViewById(R.id.password1)
        pass2 = view.findViewById(R.id.password2)

        accpet.setOnClickListener(this)
        cancel.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        if (p0 != null) {
            when (p0.id) {
                R.id.accept -> {
                    checkPasswords()
                }
                R.id.cancel -> {
                    dialog?.dismiss()
                }
            }
        }
    }

    private fun checkPasswords() {
        if (pass1?.text.toString().equals(pass2?.text.toString())) {
            val db = DBController(dialog!!.context)
            var flag = false

            try {
                db.insertUsuario(nombre?.text.toString(), pass1?.text.toString())
            } catch (e: Exception) {
                e.printStackTrace()
                flag = true
            }


            if (!flag) {
                Toast.makeText(
                    dialog!!.context,
                    "El usuario '" + nombre?.text.toString() + "' ha sido agregado correctamente",
                    Toast.LENGTH_LONG
                ).show()
                dismiss()
            } else {
                Toast.makeText(
                    dialog!!.context,
                    "El usuario '" + nombre?.text.toString() + "' ya está registrado",
                    Toast.LENGTH_LONG
                ).show()
            }

        } else {
            Toast.makeText(dialog?.context, "Las contraseñas no coinciden", Toast.LENGTH_SHORT)
                .show()
        }
    }
}