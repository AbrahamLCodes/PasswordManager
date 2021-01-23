package portafolio.apps.passwordmanager.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import portafolio.apps.passwordmanager.R
import portafolio.apps.passwordmanager.datamodel.Correo
import portafolio.apps.passwordmanager.formviewsactivities.ViewCorreo

class CorreoAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items: List<Correo> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CorreoViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_detalle_correo,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is CorreoViewHolder -> {
                holder.bind(items.get(position))
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun submitList(correoList: List<Correo>) {
        items = correoList
    }

    inner class CorreoViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val asunto: TextView = itemView.findViewById(R.id.nomUsuario)
        val correo: TextView = itemView.findViewById(R.id.correo)

        init {
            itemView.setOnClickListener { v: View ->
                val position: Int = adapterPosition
                val intent = Intent(itemView.context, ViewCorreo::class.java)
                intent.apply {
                    putExtra("correo", items.get(position))
                }
                itemView.context.startActivity(intent)
            }
        }

        fun bind(correo: Correo) {
            asunto.setText(correo.getNombre())
            this.correo.setText(correo.getCorreo())
        }
    }

}