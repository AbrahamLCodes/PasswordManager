package portafolio.apps.passwordmanager.adapters

import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputLayout
import portafolio.apps.passwordmanager.R
import portafolio.apps.passwordmanager.activities.HomeActivity
import portafolio.apps.passwordmanager.database.DBController
import portafolio.apps.passwordmanager.datamodel.Cuenta
import portafolio.apps.passwordmanager.formactivities.FormContrasenia
import portafolio.apps.passwordmanager.formactivities.FormCuenta
import portafolio.apps.passwordmanager.formviewsactivities.ViewContrasenia
import portafolio.apps.passwordmanager.formviewsactivities.ViewCuentas

class CuentaAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items: List<Cuenta> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CuentaViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_detalle_cuenta,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is CuentaViewHolder -> {
                holder.bind(items.get(position))
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun submitList(cuentaList: List<Cuenta>) {
        items = cuentaList
    }

    inner class CuentaViewHolder constructor(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {
        val asunto: TextView = itemView.findViewById(R.id.nomUsuario)
        val username: TextView = itemView.findViewById(R.id.correo)
        val contrasenia: TextView = itemView.findViewById(R.id.contrasenia)

        init {
            itemView.setOnClickListener { v: View ->
                val position: Int = adapterPosition
                val intent = Intent(itemView.context, ViewCuentas::class.java)
                intent.apply {
                    putExtra("cuenta", items.get(position))
                }
                itemView.context.startActivity(intent)
            }
            itemView.setOnLongClickListener{ v: View->
                val position: Int = adapterPosition
                MaterialAlertDialogBuilder(itemView.context).
                setTitle("Cuenta: "+asunto.text.toString().toUpperCase()).
                setMessage("Que desea hacer?").
                setNeutralButton("Ver"){
                        dialog, which -> val intent = Intent(itemView.context, ViewCuentas::class.java)
                    intent.apply {
                        putExtra("cuenta", items.get(position))
                    }
                    itemView.context.startActivity(intent)
                }.setPositiveButton("editar"){
                        dialog, which ->
                    val intent2 = Intent(itemView.context, FormCuenta::class.java)
                    intent2. apply {
                        putExtra("cuenta", items.get(position))
                        putExtra("username", items.get(position).getNomUsuario())
                        itemView.context.startActivity(intent2)
                    }
                }.setNegativeButton("elminar"){
                        dialog, which ->
                    // Eliminar
                    val db = DBController(itemView.context)
                    db.deleteCuenta(
                        items[position].getNomUsuario(),
                        items[position].getCorreo(),
                        items[position].getWebsite()
                    )

                    HomeActivity.cuentaAdapter.submitList(
                        db.customMainCuentaSelect(
                            "NOMUSUARIO",
                            HomeActivity.user
                        )
                    )
                    HomeActivity.recycler.apply {
                        layoutManager = GridLayoutManager(itemView.context, 1)
                        adapter = HomeActivity.cuentaAdapter

                    }
                }.
                show()
                return@setOnLongClickListener true
            }
        }

        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(cuenta: Cuenta) {
            asunto.setText(cuenta.getWebsite())
            username.setText(cuenta.getNickname())
            contrasenia.setText(cuenta.getContrasenia())

        }
    }

}