package portafolio.apps.passwordmanager.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import portafolio.apps.passwordmanager.R
import portafolio.apps.passwordmanager.activities.HomeActivity
import portafolio.apps.passwordmanager.database.DBController
import portafolio.apps.passwordmanager.datamodel.Correo
import portafolio.apps.passwordmanager.formactivities.FormCorreo
import portafolio.apps.passwordmanager.formviewsactivities.ViewCorreo

class CorreoAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items: List<Correo> = ArrayList()
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
        items = correoList
    }

    inner class CorreoViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val asunto: TextView = itemView.findViewById(R.id.nomUsuario)
        val correo: TextView = itemView.findViewById(R.id.correo)

        init {
            itemView.setOnClickListener { v: View ->
                val position: Int = adapterPosition
                val intent = Intent(itemView.context, ViewCorreo::class.java)
                intent.apply {
                    putExtra("correo", items.get(position))
                }
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
                    HomeActivity.correoAdapter.submitList(
                        db.customCorreoSelect(
                            "NOMUSUARIO",
                            HomeActivity.user
                        )
                    )
                    HomeActivity.recycler.apply {
                        layoutManager = GridLayoutManager(itemView.context, 1)
                        adapter = HomeActivity.correoAdapter

                    }

                }.show()
                return@setOnLongClickListener true
            }

        }

        fun bind(correo: Correo) {
            asunto.setText(correo.getNombre())
            this.correo.setText(correo.getCorreo())
        }
    }

}