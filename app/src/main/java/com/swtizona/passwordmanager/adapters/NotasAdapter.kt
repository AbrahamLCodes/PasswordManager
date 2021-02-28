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
import com.swtizona.passwordmanager.tabfragments.NotasFragment
import com.swtizona.passwordmanager.database.DBController
import com.swtizona.passwordmanager.datamodel.Nota
import com.swtizona.passwordmanager.formactivities.FormNota
import com.swtizona.passwordmanager.formviewsactivities.ViewNotas
import java.util.ArrayList

class NotasAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>(), Filterable {

    private lateinit var items: MutableList<Nota>
    private lateinit var itemsCopy: MutableList<Nota>

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
                holder.bind(items[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun submitList(notasList: List<Nota>) {
        items = ArrayList(notasList)
        itemsCopy = ArrayList(notasList)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                var filteredList: MutableList<Nota> = ArrayList()

                if (constraint == null || constraint.isEmpty()) {
                    filteredList.addAll(itemsCopy)
                } else {
                    val filterPattern = constraint.toString().toLowerCase().trim()
                    for (item: Nota in itemsCopy) {
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
                items.addAll(results!!.values as ArrayList<Nota>)
                notifyDataSetChanged()
            }
        }
    }

    inner class NotasViewHolder constructor(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.title)
        private val body: TextView = itemView.findViewById(R.id.body)

        init {
            itemView.setOnClickListener {
                val position: Int = adapterPosition
                val intent = Intent(itemView.context, ViewNotas::class.java)
                intent.apply {
                    putExtra("nota", items[position])
                    putExtra("userObject", HomeTabActivity.usuarioIntent)
                }
                itemView.context.startActivity(intent)
                (itemView.context as Activity).finish()

            }
            itemView.setOnLongClickListener {
                val position: Int = adapterPosition
                MaterialAlertDialogBuilder(itemView.context).setTitle(
                    "Nota: " + title.text.toString().toUpperCase()
                ).setMessage("¿Qué desea hacer?").setNeutralButton("Ver") { _, _ ->
                    goToView()
                }.setPositiveButton("Editar") { _, _ ->
                    goToEditar()
                }.setNegativeButton("Eliminar") { _, _ ->
                    eliminar()
                }.show()
                return@setOnLongClickListener true
            }
        }

        private fun goToView() {
            val intent = Intent(itemView.context, ViewNotas::class.java)
            intent.apply {
                putExtra("nota", items[adapterPosition])
                putExtra("userObject", HomeTabActivity.usuarioIntent)
            }
            itemView.context.startActivity(intent)
            (itemView.context as Activity).finish()
        }

        private fun goToEditar() {
            val intent2 = Intent(itemView.context, FormNota::class.java)
            intent2.apply {
                putExtra("notaupdated", items[adapterPosition])
                putExtra("username", items[adapterPosition].getNomusuario())
                putExtra("userObject", HomeTabActivity.usuarioIntent)
            }
            itemView.context.startActivity(intent2)
            (itemView.context as Activity).finish()
        }

        private fun eliminar() {
            MaterialAlertDialogBuilder(itemView.context)
                .setTitle(
                    "Eliminar Cuenta"
                )
                .setMessage("¿Seguro que quiere eliminar la cuenta?")
                .setPositiveButton("Eliminar") { _, _ ->
                    delete()
                }.setNegativeButton("Cancelar") { _, _ ->
                }.show()
        }

        private fun delete() {
            // Eliminar
            val db = DBController(itemView.context)
            db.deleteNotas(
                items[adapterPosition].getNomusuario(),
                items[adapterPosition].getAsunto()
            )

            NotasFragment.notasAdapter.submitList(
                db.customNotaSelect(
                    "NOMUSUARIO",
                    HomeTabActivity.username
                )
            )
            NotasFragment.recycler.apply {
                layoutManager = GridLayoutManager(itemView.context, 1)
                adapter = NotasFragment.notasAdapter

            }
            db.close()
        }

        fun bind(nota: Nota) {
            title.text = nota.getAsunto()
            body.text = nota.getNota()
        }
    }
}