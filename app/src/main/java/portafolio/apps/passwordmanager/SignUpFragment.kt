package portafolio.apps.passwordmanager

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDialogFragment

class SignUpFragment : AppCompatDialogFragment(), View.OnClickListener {

    //nombre, pass1, pass2
    private var nombre: EditText? = null
    private var pass1: EditText? = null
    private var pass2: EditText? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var color = ColorDrawable(android.graphics.Color.TRANSPARENT)
        dialog?.window?.setBackgroundDrawable(color)
        return inflater.inflate(R.layout.signup_fragmet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val accpet: TextView = view.findViewById(R.id.accept)
        val cancel: TextView = view.findViewById(R.id.cancel)

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
            db.insertUser(nombre?.text.toString(), pass1?.text.toString())
            Toast.makeText(
                    dialog!!.context,
                    "El usuario '" + nombre?.text.toString() + "' ha sido agregado correctamente",
                    Toast.LENGTH_LONG
            ).show()
            dismiss()
        } else {
            Toast.makeText(dialog?.context, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
        }
    }
}