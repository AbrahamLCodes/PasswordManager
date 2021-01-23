package portafolio.apps.passwordmanager.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import portafolio.apps.passwordmanager.R
import portafolio.apps.passwordmanager.datamodel.Nota
import portafolio.apps.passwordmanager.formviewsactivities.ViewCorreo
import portafolio.apps.passwordmanager.formviewsactivities.ViewCuentas
import portafolio.apps.passwordmanager.formviewsactivities.ViewNotas

class NotasAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items: List<Nota> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return NotasViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_detalle_nota,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is NotasViewHolder -> {
                holder.bind(items.get(position))
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun submitList(notasList: List<Nota>) {
        items = notasList
    }

    inner class NotasViewHolder constructor(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.title)
        val body: TextView = itemView.findViewById(R.id.body)

        init {
            itemView.setOnClickListener { v: View ->
                val position: Int = adapterPosition
                val intent = Intent(itemView.context, ViewNotas::class.java)
                intent.apply {
                    putExtra("nota", items.get(position))
                }
                itemView.context.startActivity(intent)
            }
        }

        fun bind(nota: Nota) {
            title.setText(nota.getAsunto())
            body.setText(nota.getNota())
        }
    }
}