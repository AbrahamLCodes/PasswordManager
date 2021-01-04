package portafolio.apps.passwordmanager

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDialogFragment

class SignUpFragment : AppCompatDialogFragment(), View.OnClickListener {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var color = ColorDrawable(android.graphics.Color.TRANSPARENT)
        dialog?.window?.setBackgroundDrawable(color)
        return inflater.inflate(R.layout.signup_fragmet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val accpet: TextView = view.findViewById(R.id.accept)
        val cancel: TextView = view.findViewById(R.id.cancel)
        accpet.setOnClickListener(this)
        cancel.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        if (p0 != null) {
            when (p0.id) {
                R.id.accept -> {
                    Toast.makeText(
                            dialog?.context,
                            "Accion Registrar en Construccion",
                            Toast.LENGTH_SHORT
                    ).show()
                }
                R.id.cancel -> {
                    dialog?.dismiss()
                }
            }
        }
    }
}