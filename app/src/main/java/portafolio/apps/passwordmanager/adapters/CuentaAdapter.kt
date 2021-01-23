package portafolio.apps.passwordmanager.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import portafolio.apps.passwordmanager.R
import portafolio.apps.passwordmanager.datamodel.Cuenta
import portafolio.apps.passwordmanager.formviewsactivities.ViewCuentas

class CuentaAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items: List<Cuenta> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CuentaViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_detalle_cuenta,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is CuentaViewHolder -> {
                holder.bind(items.get(position))
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun submitList(cuentaList: List<Cuenta>) {
        items = cuentaList
    }

    inner class CuentaViewHolder constructor(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {
        val asunto: TextView = itemView.findViewById(R.id.nomUsuario)
        val username: TextView = itemView.findViewById(R.id.correo)
        val contrasenia: TextView = itemView.findViewById(R.id.contrasenia)

        init {
            itemView.setOnClickListener { v: View ->
                val position: Int = adapterPosition
                val intent = Intent(itemView.context, ViewCuentas::class.java)
                intent.apply {
                    putExtra("cuenta", items.get(position))
                }
                itemView.context.startActivity(intent)
            }
        }

        fun bind(cuenta: Cuenta) {
            asunto.setText(cuenta.getWebsite())
            username.setText(cuenta.getNickname())
            contrasenia.setText(cuenta.getContrasenia())
        }
    }

}