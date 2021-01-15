package portafolio.apps.passwordmanager.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import portafolio.apps.passwordmanager.R
import portafolio.apps.passwordmanager.datamodel.Tarjeta

class PagosAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items: List<Tarjeta> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return PagosViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_detalle_tarjeta,
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is PagosViewHolder -> {
                holder.bind(items.get(position))
            }
        }
    }

    override fun getItemCount(): Int {
        return items.count()
    }

    fun submitList(notasList: List<Tarjeta>) {
        items = notasList
    }

    class PagosViewHolder constructor(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {
        val titular: TextView = itemView.findViewById(R.id.titular)
        val ntarjeta: TextView = itemView.findViewById(R.id.cuentaNum)
        val nip: TextView = itemView.findViewById(R.id.nip)
        val cad: TextView = itemView.findViewById(R.id.cad)

        fun bind(tarjeta: Tarjeta) {
            titular.setText(tarjeta.getTitular())
            ntarjeta.setText(tarjeta.getNtarjeta())
            nip.setText(tarjeta.getCodseg())
            cad.setText(tarjeta.getCadM() + "/" + tarjeta.getCadY())
        }
    }
}