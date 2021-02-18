package portafolio.apps.passwordmanager.adapters

import android.content.Intent
import android.util.Log
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
import portafolio.apps.passwordmanager.activities.tabfragments.CorreosFragment
import portafolio.apps.passwordmanager.database.DBController
import portafolio.apps.passwordmanager.datamodel.Correo
import portafolio.apps.passwordmanager.formactivities.FormCorreo
import portafolio.apps.passwordmanager.formviewsactivities.ViewCorreo

class CorreoAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>(), Filterable {

    private lateinit var items: MutableList<Correo>
    private lateinit var itemsCopy: MutableList<Correo>

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
        items = ArrayList(correoList)
        itemsCopy = ArrayList(correoList)
    }

    override fun getFilter(): Filter {
        return object : Filter() {

            override fun performFiltering(constraint: CharSequence?): FilterResults {
                var filteredList: MutableList<Correo> = ArrayList()

                if (constraint == null || constraint.isEmpty()) {
                    filteredList.addAll(itemsCopy)
                } else {
                    val filterPattern = constraint.toString().toLowerCase().trim()
                    for (item: Correo in itemsCopy) {
                        if (item.getNombre().toLowerCase().contains(filterPattern) ||
                                item.getCorreo().toLowerCase().contains(filterPattern)) {
                            filteredList.add(item)
                        }
                    }
                }

                val results = FilterResults()
                results.values = filteredList
                return results
            }

            override fun publishResults(charSequence: CharSequence?, filterResults: FilterResults) {
                items.clear()
                items.addAll(filterResults.values as ArrayList<Correo>)
                notifyDataSetChanged()

            }
        }
    }


    inner class CorreoViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val asunto: TextView = itemView.findViewById(R.id.nomUsuario)
        val correo: TextView = itemView.findViewById(R.id.correo)
        val image: ImageView = itemView.findViewById(R.id.imgViewCard)

        init {
            itemView.setOnClickListener { v: View ->
                val position: Int = adapterPosition
                val intent = Intent(itemView.context, ViewCorreo::class.java)
                intent.apply {
                    putExtra("correo", items.get(position))
                    putExtra("userObject", HomeTabActivity.usuarioIntent)
                }
                Log.d("FECHA DE CORREO", items.get(position).getFecha())
                itemView.context.startActivity(intent)
            }


            itemView.setOnLongClickListener { v: View ->
                val position: Int = adapterPosition
                MaterialAlertDialogBuilder(itemView.context).setTitle(
                    "Correo: " + asunto.text.toString().toUpperCase()
                ).setMessage("Que desea hacer?").setNeutralButton("Ver") { dialog, which ->
                    val intent = Intent(itemView.context, ViewCorreo::class.java)
                    intent.apply {
                        putExtra("correo", items.get(position))
                    }
                    itemView.context.startActivity(intent)
                }.setPositiveButton("editar") { dialog, which ->
                    val intent2 = Intent(itemView.context, FormCorreo::class.java)
                    intent2.apply {
                        putExtra("correoupdated", items.get(position))
                        itemView.context.startActivity(intent2)
                    }
                }.setNegativeButton("elminar") { dialog, which ->

                    // Eliminar
                    val db = DBController(itemView.context)
                    db.deleteCorreo(items[position].getCorreo(), items[position].getNomusuario())
                    CorreosFragment.correoAdapter.submitList(
                        db.customCorreoSelect(
                            "NOMUSUARIO",
                            HomeTabActivity.username
                        )
                    )
                    CorreosFragment.recycler.apply {
                        layoutManager = GridLayoutManager(itemView.context, 1)
                        adapter = CorreosFragment.correoAdapter

                    }
                    db.close()
                }.show()
                return@setOnLongClickListener true
            }

        }

        fun bind(correo: Correo) {
            asunto.setText(correo.getNombre())
            this.correo.setText(correo.getCorreo())
            if (correo.getCorreo().contains("gmail", ignoreCase = true)) {
                image.setImageResource(R.drawable.ic_gmail)
            }
            if (correo.getCorreo().contains("yahoo", ignoreCase = true)) {
                image.setImageResource(R.drawable.ic_yahoo)
            }
            if (correo.getCorreo().contains("outlook", ignoreCase = true)) {
                image.setImageResource(R.drawable.ic_outlook)
            }
            if (correo.getCorreo().contains("hotmail", ignoreCase = true)) {
                image.setImageResource(R.drawable.ic_email)
            }
        }
    }
}