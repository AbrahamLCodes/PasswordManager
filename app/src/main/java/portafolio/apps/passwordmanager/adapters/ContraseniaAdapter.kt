package portafolio.apps.passwordmanager.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import portafolio.apps.passwordmanager.R
import portafolio.apps.passwordmanager.activities.HomeActivity
import portafolio.apps.passwordmanager.database.DBController
import portafolio.apps.passwordmanager.datamodel.Contrasenia
import portafolio.apps.passwordmanager.formactivities.FormContrasenia
import portafolio.apps.passwordmanager.formactivities.FormCorreo
import portafolio.apps.passwordmanager.formviewsactivities.ViewContrasenia
import portafolio.apps.passwordmanager.formviewsactivities.ViewCorreo

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

                    HomeActivity.contraseniaAdapter.submitList(
                        db.customContraseniaSelect(
                            "NOMUSUARIO",
                            HomeActivity.user
                        )
                    )
                    HomeActivity.recycler.apply {
                        layoutManager = GridLayoutManager(itemView.context, 1)
                        adapter = HomeActivity.contraseniaAdapter
                    }
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