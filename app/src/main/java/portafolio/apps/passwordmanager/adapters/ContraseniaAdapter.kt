package portafolio.apps.passwordmanager.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import portafolio.apps.passwordmanager.R
import portafolio.apps.passwordmanager.activities.HomeTabActivity
import portafolio.apps.passwordmanager.activities.tabfragments.ContraseniasFragment
import portafolio.apps.passwordmanager.database.DBController
import portafolio.apps.passwordmanager.datamodel.Contrasenia
import portafolio.apps.passwordmanager.formactivities.FormContrasenia
import portafolio.apps.passwordmanager.formviewsactivities.ViewContrasenia
import java.util.ArrayList

class ContraseniaAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>(), Filterable {

    private lateinit var items: MutableList<Contrasenia>
    private lateinit var itemsCopy: MutableList<Contrasenia>

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

    fun submitList(contraseniaList: List<Contrasenia>) {
        items = ArrayList(contraseniaList)
        itemsCopy = ArrayList(contraseniaList)
    }

    override fun getFilter(): Filter {
        return object  : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                var filteredList : MutableList<Contrasenia> = ArrayList()

                if(constraint == null || constraint.isEmpty()){
                    filteredList.addAll(itemsCopy)
                } else {
                    val filterPattern = constraint.toString().toLowerCase().trim()
                    for(item: Contrasenia in itemsCopy){
                        if (item.getAsunto().toLowerCase().contains(filterPattern)){
                            filteredList.add(item)
                        }
                    }
                }

                val results = FilterResults()
                results.values = filteredList
                return results
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                items.clear()
                items.addAll(results!!.values as ArrayList<Contrasenia>)
                notifyDataSetChanged()
            }
        }
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
                    putExtra("userObject", HomeTabActivity.usuarioIntent)
                }
                itemView.context.startActivity(intent)
            }
            itemView.setOnLongClickListener { v: View ->
                val position: Int = adapterPosition
                MaterialAlertDialogBuilder(itemView.context).setTitle(
                    "Contraseña: " + asunto.text.toString().toUpperCase()
                ).setMessage("Que desea hacer?").setNeutralButton("Ver") { dialog, which ->
                    val intent = Intent(itemView.context, ViewContrasenia::class.java)
                    intent.apply {
                        putExtra("contrasenia", items.get(position))
                    }
                    itemView.context.startActivity(intent)
                }.setPositiveButton("editar") { dialog, which ->
                    val intent2 = Intent(itemView.context, FormContrasenia::class.java)
                    intent2.apply {
                        putExtra("contraseniaupdated", items.get(position))
                        itemView.context.startActivity(intent2)
                    }
                }.setNegativeButton("elminar") { dialog, which ->

                    // Eliminar
                    val db = DBController(itemView.context)
                    db.deleteContrasenia(
                        items[position].getNomusuario(),
                        items[position].getAsunto()
                    )

                    ContraseniasFragment.contraseniaAdapter.submitList(
                        db.customContraseniaSelect(
                            "NOMUSUARIO",
                            HomeTabActivity.username
                        )
                    )
                    ContraseniasFragment.recycler.apply {
                        layoutManager = GridLayoutManager(itemView.context, 1)
                        adapter = ContraseniasFragment.contraseniaAdapter
                    }
                    db.close()
                }.show()
                return@setOnLongClickListener true
            }
        }

        fun bind(contrasenia: Contrasenia) {
            asunto.setText(contrasenia.getAsunto())
            this.contrasenia.setText(contrasenia.getContrasenia())
        }
    }
}