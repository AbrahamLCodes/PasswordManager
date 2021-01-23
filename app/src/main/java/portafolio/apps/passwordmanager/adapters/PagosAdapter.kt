package portafolio.apps.passwordmanager.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import portafolio.apps.passwordmanager.R
import portafolio.apps.passwordmanager.datamodel.Tarjeta
import portafolio.apps.passwordmanager.formviewsactivities.ViewCorreo
import portafolio.apps.passwordmanager.formviewsactivities.ViewPagos

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

    inner class PagosViewHolder constructor(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {
        val titular: TextView = itemView.findViewById(R.id.titular)
        val ntarjeta: TextView = itemView.findViewById(R.id.cuentaNum)
        val nip: TextView = itemView.findViewById(R.id.nip)
        val cad: TextView = itemView.findViewById(R.id.cad)

        init {
            itemView.setOnClickListener { v: View ->
                val position: Int = adapterPosition
                val intent = Intent(itemView.context, ViewPagos::class.java)
                intent.apply {
                    putExtra("tarjeta", items.get(position))
                }
                itemView.context.startActivity(intent)
            }
        }

        fun bind(tarjeta: Tarjeta) {
            titular.setText(tarjeta.getTitular())
            ntarjeta.setText(tarjeta.getNtarjeta())
            nip.setText(tarjeta.getCodseg())
            cad.setText(tarjeta.getCadM() + "/" + tarjeta.getCadY())
        }
    }
}