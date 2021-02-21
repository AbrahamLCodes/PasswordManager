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
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDialogFragment
import portafolio.apps.passwordmanager.R
import portafolio.apps.passwordmanager.database.DBController
import portafolio.apps.passwordmanager.datamodel.Usuario
import portafolio.apps.passwordmanager.formactivities.FormUsuario
import java.lang.Exception

class ConfirmUserDialog(userObject: Usuario, editar: Boolean) : AppCompatDialogFragment(),
    View.OnClickListener {

    private val userObject = userObject
    private lateinit var usuario: EditText
    private lateinit var contrasenia: EditText
    private var editar = editar

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
                        if (editar) {
                            startActivity(Intent(activity, FormUsuario::class.java).apply {
                                putExtra("userObject", userObject)
                            })
                            activity!!.finish()
                        } else {
                            showAlertBox()
                        }
                    } else {
                        Toast.makeText(context, "Datos incorrectos", Toast.LENGTH_SHORT).show()
                    }
                }
                R.id.cancelar -> {
                    dismiss()
                }
            }
        }
    }

    private fun showAlertBox() {
        val builder = AlertDialog.Builder(context!!)
        val db = DBController(context!!)

        val correosCount = db.getRowCount(userObject.getNombre(), "CORREOS")
        val cuentasCount = db.getRowCount(userObject.getNombre(), "CUENTAS")
        val contraseniasCount = db.getRowCount(userObject.getNombre(), "CONTRASENIAS")
        val notasCount = db.getRowCount(userObject.getNombre(), "NOTAS")
        val tarjetasCount = db.getRowCount(userObject.getNombre(), "TARJETAS")

        builder.setTitle("Eliminar usuario")
        builder.setMessage(
            "¿Seguro que quieres eliminar el usuario?\n\nSe eliminarán:\n" +
                    correosCount + " correo(s)\n" + cuentasCount + " cuenta(s)\n" + contraseniasCount + " contraseña(s)\n" +
                    notasCount + " nota(s)\n" + tarjetasCount + " tarjeta(s)"
        )

        builder.setPositiveButton("Eliminar") { _, _ ->
            var eliminado = true

            try {
                db.deleteUsuario(userObject.getNombre())
            } catch (ex: Exception) {
                eliminado = false
                ex.printStackTrace()
            }

            if (eliminado) {
                dialog!!.dismiss()
                activity!!.finish()
                Toast.makeText(
                    context!!,
                    "Se ha eliminado el usuario '" + userObject.getNombre() + "' exitosamente",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        builder.setNegativeButton("Cancelar") { _, _ ->
            //Do no thing
        }

        builder.show()
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