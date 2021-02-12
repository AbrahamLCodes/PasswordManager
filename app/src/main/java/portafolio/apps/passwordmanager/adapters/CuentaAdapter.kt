package portafolio.apps.passwordmanager.adapters

import android.content.Intent
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import portafolio.apps.passwordmanager.R
import portafolio.apps.passwordmanager.activities.HomeTabActivity
import portafolio.apps.passwordmanager.activities.tabfragments.CuentasFragment
import portafolio.apps.passwordmanager.database.DBController
import portafolio.apps.passwordmanager.datamodel.Cuenta
import portafolio.apps.passwordmanager.formactivities.FormCuenta
import portafolio.apps.passwordmanager.formviewsactivities.ViewCuentas
import java.util.ArrayList

class CuentaAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>(), Filterable {

    private lateinit var items: MutableList<Cuenta>
    private lateinit var itemsCopy : MutableList<Cuenta>

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
        items = ArrayList(cuentaList)
        itemsCopy = ArrayList(cuentaList)
    }

    override fun getFilter(): Filter {
        return object  : Filter(){
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                var filteredList: MutableList<Cuenta> = ArrayList()

                if(constraint == null || constraint.isEmpty()){
                    filteredList.addAll(itemsCopy)
                } else {
                    val filterPattern = constraint.toString().toLowerCase().trim()
                    for (item: Cuenta in itemsCopy){
                        if (
                            item.getWebsite().toLowerCase().contains(filterPattern)
                            || item.getCorreo().toLowerCase().contains(filterPattern)){
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
                items.addAll(results!!.values as ArrayList<Cuenta>)
                notifyDataSetChanged()
            }
        }
    }

    inner class CuentaViewHolder constructor(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {
        val asunto: TextView = itemView.findViewById(R.id.nomUsuario)
        val username: TextView = itemView.findViewById(R.id.correo)
        val contrasenia: TextView = itemView.findViewById(R.id.contrasenia)
        val image: ImageView = itemView.findViewById(R.id.imgViewCard)


        init {
            itemView.setOnClickListener { v: View ->
                val position: Int = adapterPosition
                val intent = Intent(itemView.context, ViewCuentas::class.java)
                intent.apply {
                    putExtra("cuenta", items.get(position))
                }
                Log.d("CUENTA FECHA", items.get(position).getFecha())
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

                    CuentasFragment.cuentaAdapter.submitList(
                        db.customMainCuentaSelect(
                            "NOMUSUARIO",
                            HomeTabActivity.username
                        )
                    )
                    CuentasFragment.recycler.apply {
                        layoutManager = GridLayoutManager(itemView.context, 1)
                        adapter = CuentasFragment.cuentaAdapter

                    }
                    db.close()
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
            if(cuenta.getWebsite().contains("youtube",ignoreCase = true)){
                image.setImageResource(R.drawable.ic_youtube)
            }
            if(cuenta.getWebsite().contains("steam",ignoreCase = true)){
                image.setImageResource(R.drawable.ic_steam_logo)
            }
            if(cuenta.getWebsite().contains("facebook",ignoreCase = true)){
                image.setImageResource(R.drawable.ic_facebook)
            }
            if(cuenta.getWebsite().contains("playstation",ignoreCase = true) ||cuenta.getWebsite().contains("psn",ignoreCase = true)){
                image.setImageResource(R.drawable.ic_playstation)
            }
            if(cuenta.getWebsite().contains("xbox",ignoreCase = true)){
                image.setImageResource(R.drawable.ic_xbox)
            }
            if(cuenta.getWebsite().contains("twitch",ignoreCase = true)){
                image.setImageResource(R.drawable.ic_twitch)
            }
            if(cuenta.getWebsite().contains("league",ignoreCase = true)|| cuenta.getWebsite().contains("lol",ignoreCase = true)){
                image.setImageResource(R.drawable.ic_league)
            }


        }
    }

}