package portafolio.apps.passwordmanager.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import portafolio.apps.passwordmanager.R
import portafolio.apps.passwordmanager.datamodel.Contrasenia
import portafolio.apps.passwordmanager.formviewsactivities.ViewContrasenia
import portafolio.apps.passwordmanager.formviewsactivities.ViewCorreo

class ContraseniaAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items: List<Contrasenia> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ContraseniaViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_detalle_contrasenia,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ContraseniaViewHolder -> {
                holder.bind(items.get(position))
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun submitList(correoList: List<Contrasenia>) {
        items = correoList
    }

    inner class ContraseniaViewHolder constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val asunto: TextView = itemView.findViewById(R.id.nomUsuario)
        val contrasenia: TextView = itemView.findViewById(R.id.contrasenia)

        init {
            itemView.setOnClickListener { v: View ->
                val position: Int = adapterPosition
                val intent = Intent(itemView.context, ViewContrasenia::class.java)
                intent.apply {
                    putExtra("contrasenia", items.get(position))
                }
                itemView.context.startActivity(intent)
            }
        }

        fun bind(contrasenia: Contrasenia) {
            asunto.setText(contrasenia.getAsunto())
            this.contrasenia.setText(contrasenia.getContrasenia())
        }
    }
}