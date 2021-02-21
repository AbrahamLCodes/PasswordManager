package portafolio.apps.passwordmanager.adapters

import android.content.Intent
import android.opengl.Matrix
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import portafolio.apps.passwordmanager.R
import portafolio.apps.passwordmanager.activities.HomeTabActivity
import portafolio.apps.passwordmanager.activities.tabfragments.TarjetasFragment
import portafolio.apps.passwordmanager.database.DBController
import portafolio.apps.passwordmanager.datamodel.Tarjeta
import portafolio.apps.passwordmanager.formactivities.FormPagos
import portafolio.apps.passwordmanager.formviewsactivities.ViewPagos
import java.util.ArrayList

class PagosAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>(), Filterable {

    private lateinit var items: MutableList<Tarjeta>
    private lateinit var itemsCopy : MutableList<Tarjeta>

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
        items = ArrayList(notasList)
        itemsCopy = ArrayList(notasList)
    }

    override fun getFilter(): Filter {
        return object  : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
               var filteredList : MutableList<Tarjeta> = ArrayList()

               if(constraint == null || constraint.isEmpty()){
                   filteredList.addAll(itemsCopy)
               } else {
                   val filterPattern = constraint.toString().toLowerCase().trim()
                   for (item: Tarjeta in itemsCopy) {
                       if (
                           item.getBanco().toLowerCase().contains(filterPattern)
                           || item.getAsunto().toLowerCase().contains(filterPattern)
                           || item.getTitular().toLowerCase().contains(filterPattern)) {
                           filteredList.add(item)
                       }
                   }
               }

                val results = FilterResults()
                results.values = filteredList
                return  results
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                items.clear()
                items.addAll(results!!.values as ArrayList<Tarjeta>)
                notifyDataSetChanged()
            }
        }
    }

    inner class PagosViewHolder constructor(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {
        val titular: TextView = itemView.findViewById(R.id.titular)
        val ntarjeta: TextView = itemView.findViewById(R.id.cuentaNum)
        val cad: TextView = itemView.findViewById(R.id.cad)
        val image: ImageView = itemView.findViewById(R.id.imgViewCard)

        init {
            itemView.setOnClickListener { v: View ->
                val position: Int = adapterPosition
                val intent = Intent(itemView.context, ViewPagos::class.java)
                intent.apply {
                    putExtra("tarjeta", items.get(position))
                    putExtra("userObject", HomeTabActivity.usuarioIntent)
                }
                itemView.context.startActivity(intent)
            }
            itemView.setOnLongClickListener { v: View ->
                val position: Int = adapterPosition
                MaterialAlertDialogBuilder(itemView.context).setTitle(
                    "Cuenta: " + titular.text.toString().toUpperCase()
                ).setMessage("¿Qué desea hacer?").setNeutralButton("Ver") { dialog, which ->
                    val intent = Intent(itemView.context, ViewPagos::class.java)
                    intent.apply {
                        putExtra("tarjeta", items.get(position))
                    }
                    itemView.context.startActivity(intent)
                }.setPositiveButton("Editar") { dialog, which ->
                    val intent2 = Intent(itemView.context, FormPagos::class.java)
                    intent2.apply {
                        putExtra("tarjetaupdated", items.get(position))
                        itemView.context.startActivity(intent2)
                    }
                }.setNegativeButton("Eliminar") { dialog, which ->
                    // Eliminar
                    val db = DBController(itemView.context)
                    db.deleteTarjetas(
                        items[position].getNomusuario(),
                        items[position].getAsunto()
                    )

                    TarjetasFragment.tarjetasAdapter.submitList(
                        db.customTarjetaSelect(
                            "NOMUSUARIO",
                            HomeTabActivity.username
                        )
                    )
                    TarjetasFragment.recycler.apply {
                        layoutManager = GridLayoutManager(itemView.context, 1)
                        adapter = TarjetasFragment.tarjetasAdapter
                    }
                    db.close()
                }.show()
                return@setOnLongClickListener true
            }
        }

        fun bind(tarjeta: Tarjeta) {
            titular.setText(tarjeta.getTitular())
            ntarjeta.setText(tarjeta.getNtarjeta())
            cad.setText(tarjeta.getCadM() + "/" + tarjeta.getCadY())
            if(tarjeta.getBanco().contains("santander", ignoreCase = true)){
                image.setImageResource(R.drawable.ic_icons8_santander)
            }
            if(tarjeta.getBanco().contains("citibanamex", ignoreCase = true)||tarjeta.getBanco().contains("banamex", ignoreCase = true)){
                image.setImageResource(R.drawable.ic_citibanamex)
            }
            if(tarjeta.getBanco().contains("HSBC", ignoreCase = true)){
                image.setImageResource(R.drawable.ic_hsbc_icon_icons_com_60512)
            }
            if(tarjeta.getBanco().contains("paypal", ignoreCase = true)){
                image.setImageResource(R.drawable.ic_paypal_39_icon_icons_com_60555)
            }
            if(tarjeta.getBanco().contains("american", ignoreCase = true)){
                image.setImageResource(R.drawable.ic_american)
            }
            if(tarjeta.getBanco().contains("scotiabank", ignoreCase = true)){
                image.setImageResource(R.drawable.ic_scotiabank_4)
            }
            if(tarjeta.getBanco().contains("BBVA", ignoreCase = true)||tarjeta.getBanco().contains("bancomer", ignoreCase = true)){
                image.setImageResource(R.drawable.ic_bbva_2019)
                image.scaleType = ImageView.ScaleType.CENTER_INSIDE
            }
            if(tarjeta.getBanco().contains("banorte", ignoreCase = true)){
                image.setImageResource(R.drawable.ic_banorte_73512)
                image.scaleType = ImageView.ScaleType.CENTER_CROP
            }
        }
    }
}