package portafolio.apps.passwordmanager.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import portafolio.apps.passwordmanager.R
import portafolio.apps.passwordmanager.datamodel.Contrasenia

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

    class ContraseniaViewHolder constructor(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {
        val asunto: TextView = itemView.findViewById(R.id.nomUsuario)
        val contrasenia: TextView = itemView.findViewById(R.id.contrasenia)

        fun bind(contrasenia: Contrasenia) {
            asunto.setText(contrasenia.getAsunto())
            this.contrasenia.setText(contrasenia.getContrasenia())
        }
    }
}