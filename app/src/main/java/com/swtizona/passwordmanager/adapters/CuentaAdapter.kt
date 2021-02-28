package com.swtizona.passwordmanager.adapters

import android.app.Activity
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
import com.swtizona.passwordmanager.R
import com.swtizona.passwordmanager.mainactivities.HomeTabActivity
import com.swtizona.passwordmanager.tabfragments.CuentasFragment
import com.swtizona.passwordmanager.database.DBController
import com.swtizona.passwordmanager.datamodel.Cuenta
import com.swtizona.passwordmanager.formactivities.FormCuenta
import com.swtizona.passwordmanager.formviewsactivities.ViewCuentas
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
                holder.bind(items[position])
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
                val filteredList: MutableList<Cuenta> = ArrayList()

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
        val categoria: TextView = itemView.findViewById(R.id.categoria)
        val image: ImageView = itemView.findViewById(R.id.imgViewCard)


        init {
            itemView.setOnClickListener { v: View ->
                val position: Int = adapterPosition
                val intent = Intent(itemView.context, ViewCuentas::class.java)
                intent.apply {
                    putExtra("cuenta", items[position])
                    putExtra("userObject", HomeTabActivity.usuarioIntent)
                }
                Log.d("CUENTA FECHA", items[position].getFecha())
                itemView.context.startActivity(intent)
                (itemView.context as Activity).finish()

            }
            itemView.setOnLongClickListener{
                val position: Int = adapterPosition
                MaterialAlertDialogBuilder(itemView.context).
                setTitle("Cuenta: "+asunto.text.toString().toUpperCase()).
                setMessage("¿Qué desea hacer?").
                setNeutralButton("Ver"){
                        _, _ -> val intent = Intent(itemView.context, ViewCuentas::class.java)
                    intent.apply {
                        putExtra("cuenta", items[position])
                    }
                    itemView.context.startActivity(intent)
                    (itemView.context as Activity).finish()
                }.setPositiveButton("Editar"){
                        _, _ ->
                    val intent2 = Intent(itemView.context, FormCuenta::class.java)
                    intent2. apply {
                        putExtra("cuenta", items[position])
                        putExtra("username", items[position].getNomUsuario())
                        itemView.context.startActivity(intent2)
                        (itemView.context as Activity).finish()
                    }
                }.setNegativeButton("Eliminar"){
                        _, _ ->
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
            asunto.text = cuenta.getWebsite()
            username.text = cuenta.getNickname()
            categoria.text = cuenta.getCategoria()
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