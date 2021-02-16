package portafolio.apps.passwordmanager.fragments

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.StrictMode
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatDialogFragment
import portafolio.apps.passwordmanager.R
import portafolio.apps.passwordmanager.database.DBController
import portafolio.apps.passwordmanager.services.GMailSender


class RecuperacionFragment : AppCompatDialogFragment(), View.OnClickListener {

    private lateinit var usuario: EditText

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
        return inflater.inflate(R.layout.fragment_recuperacion, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initComponents(view)
    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {
                R.id.enviar -> {
                    checkUser()
                }
                R.id.cancelar -> {
                    dismiss()
                }
            }
        }
    }

    private fun checkUser() {
        val db = DBController(context!!)
        if (db.findUsuarioByName(usuario.text.toString())) {
            sendEmail()
            Toast.makeText(
                context,
                "Se ha enviado el correo con la información si es que el usuario existe",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            Toast.makeText(
                context,
                "Se ha enviado el correo con la información si es que el usuario existe",
                Toast.LENGTH_SHORT
            ).show()
        }
        dismiss()
    }

    private fun sendEmail() {
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        val sender = GMailSender(
            "tizonalc@gmail.com",
            "TecateSoftware03"
        )
        sender.sendMail(
            "Recuperación de contraseña",
            "Este es el body",
            "tizonalc@gmail.com",
            "abraham11999@hotmail.com"
        )
    }

    private fun initComponents(v: View) {
        usuario = v.findViewById(R.id.usuario)
        val enviar = v.findViewById<Button>(R.id.enviar)
        val cancelar = v.findViewById<Button>(R.id.cancelar)

        enviar.setOnClickListener(this)
        cancelar.setOnClickListener(this)
    }
}