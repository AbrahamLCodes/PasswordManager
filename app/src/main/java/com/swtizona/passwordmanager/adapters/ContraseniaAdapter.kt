package com.swtizona.passwordmanager.adapters

import android.app.Activity
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
import com.swtizona.passwordmanager.R
import com.swtizona.passwordmanager.mainactivities.HomeTabActivity
import com.swtizona.passwordmanager.tabfragments.ContraseniasFragment
import com.swtizona.passwordmanager.database.DBController
import com.swtizona.passwordmanager.datamodel.Contrasenia
import com.swtizona.passwordmanager.formactivities.FormContrasenia
import com.swtizona.passwordmanager.formviewsactivities.ViewContrasenia
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
                holder.bind(items[position])
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
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                var filteredList: MutableList<Contrasenia> = ArrayList()

                if (constraint == null || constraint.isEmpty()) {
                    filteredList.addAll(itemsCopy)
                } else {
                    val filterPattern = constraint.toString().toLowerCase().trim()
                    for (item: Contrasenia in itemsCopy) {
                        if (item.getAsunto().toLowerCase().contains(filterPattern)) {
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
            itemView.setOnClickListener {
                val position: Int = adapterPosition
                val intent = Intent(itemView.context, ViewContrasenia::class.java)
                intent.apply {
                    putExtra("contrasenia", items[position])
                    putExtra("userObject", HomeTabActivity.usuarioIntent)
                }
                itemView.context.startActivity(intent)
                (itemView.context as Activity).finish()
            }
            itemView.setOnLongClickListener {
                MaterialAlertDialogBuilder(itemView.context).setTitle(
                    "Contraseña: " + asunto.text.toString().toUpperCase()
                ).setMessage("¿Qué desea hacer?")
                    .setNeutralButton("Ver") { _, _ ->
                        goToView()
                    }.setPositiveButton("Editar") { _, _ ->
                        goToEditar()
                    }.setNegativeButton("Eliminar") { _, _ ->
                        // Eliminar
                        eliminar()
                    }.show()
                return@setOnLongClickListener true
            }
        }

        private fun goToView() {
            val intent = Intent(itemView.context, ViewContrasenia::class.java)
            intent.apply {
                putExtra("contrasenia", items[adapterPosition])
                putExtra("userObject", HomeTabActivity.usuarioIntent)
            }
            itemView.context.startActivity(intent)
            (itemView.context as Activity).finish()
        }

        private fun goToEditar() {
            val intent2 = Intent(itemView.context, FormContrasenia::class.java)
            intent2.apply {
                putExtra("contraseniaupdated", items[adapterPosition])
                putExtra("userObject", HomeTabActivity.usuarioIntent)
            }
            itemView.context.startActivity(intent2)
            (itemView.context as Activity).finish()
        }

        private fun eliminar() {
            val bd = DBController(itemView.context)
            MaterialAlertDialogBuilder(itemView.context).setTitle(
                "¿Seguro que desea eliminar este correo?"
            ).setMessage(
                "Se eliminaran " + bd.getCountFromTable(
                    items[adapterPosition].getNomusuario(),
                    "CUENTAS",
                    "CORREO",
                    items[adapterPosition].getContrasenia()
                ) + " cuentas ligadas a este correo"
            )
                .setPositiveButton("Eliminar") { _, _ ->
                    delete()
                }.setNegativeButton("Cancelar") { _, _ ->
                }.show()
        }

        private fun delete() {
            val db = DBController(itemView.context)
            db.deleteContrasenia(
                items[adapterPosition].getNomusuario(),
                items[adapterPosition].getAsunto()
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
        }

        fun bind(contrasenia: Contrasenia) {
            asunto.text = contrasenia.getAsunto()
            this.contrasenia.text = contrasenia.getContrasenia()
        }
    }
}