package portafolio.apps.passwordmanager.adapters

import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import portafolio.apps.passwordmanager.R
import portafolio.apps.passwordmanager.activities.HomeTabActivity
import portafolio.apps.passwordmanager.activities.tabfragments.NotasFragment
import portafolio.apps.passwordmanager.database.DBController
import portafolio.apps.passwordmanager.datamodel.Nota
import portafolio.apps.passwordmanager.formactivities.FormNota
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
        val card: MaterialCardView = itemView.findViewById(R.id.card_view)

        init {
            itemView.setOnClickListener { v: View ->
                val position: Int = adapterPosition
                val intent = Intent(itemView.context, ViewNotas::class.java)
                intent.apply {
                    putExtra("nota", items.get(position))
                }
                itemView.context.startActivity(intent)
            }
            itemView.setOnLongClickListener { v: View ->
                val position: Int = adapterPosition
                MaterialAlertDialogBuilder(itemView.context).setTitle(
                    "Nota: " + title.text.toString().toUpperCase()
                ).setMessage("Que desea hacer?").setNeutralButton("Ver") { dialog, which ->
                    val intent = Intent(itemView.context, ViewNotas::class.java)
                    intent.apply {
                        putExtra("nota", items.get(position))
                    }
                    itemView.context.startActivity(intent)
                }.setPositiveButton("editar") { dialog, which ->
                    val intent2 = Intent(itemView.context, FormNota::class.java)
                    intent2.apply {
                        putExtra("notaupdated", items.get(position))
                        itemView.context.startActivity(intent2)
                    }
                }.setNegativeButton("elminar") { dialog, which ->
                    // Eliminar
                    val db = DBController(itemView.context)
                    db.deleteNotas(
                        items[position].getNomusuario(),
                        items[position].getAsunto()
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
                }.show()
                return@setOnLongClickListener true
            }
        }

        fun bind(nota: Nota) {
            title.setText(nota.getAsunto())
            body.setText(nota.getNota())
            card.strokeColor =  Color.parseColor("#ec4646")
        }
    }
}