package com.swtizona.passwordmanager.tabfragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.ImageButton
import android.widget.PopupMenu
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.swtizona.passwordmanager.R
import com.swtizona.passwordmanager.adapters.CuentaAdapter
import com.swtizona.passwordmanager.database.DBController

class CuentasFragment (username: String): Fragment(), SearchView.OnQueryTextListener {

    private lateinit var search: SearchView
    private var mContext: Context? = null
    private val username = username
    lateinit var button: ImageButton

    companion object {
        lateinit var recycler: RecyclerView
        lateinit var cuentaAdapter: CuentaAdapter
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.tab_fragment_cuentas,container, false)
    }

    override fun onResume() {
        super.onResume()
        setRecyclerData()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initComponents(view)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onDetach() {
        super.onDetach()
        mContext = null
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        cuentaAdapter.filter.filter(newText)
        return false
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    private fun showMenu(v: View, popupMenu: Int) {
        val popup = PopupMenu(context!!, v)
        popup.menuInflater.inflate(popupMenu, popup.menu)

        popup.setOnMenuItemClickListener { menuItem: MenuItem ->
            when(menuItem.itemId){
                R.id.o1 -> {
                    orderRecyclerData(username,"CUENTA","ASC")
                }
                R.id.o2 -> {
                    orderRecyclerData(username,"CUENTA","DESC")
                }
                R.id.o3 -> {
                    orderRecyclerData(username,"CATEGORIA","ASC")
                }
                R.id.o4 -> {
                    orderRecyclerData(username,"FECHA","DESC")
                }
                R.id.o5 -> {
                    orderRecyclerData(username,"FECHA","ASC")
                }
            }
            true
        }
        popup.setOnDismissListener {
            // Respond to popup being dismissed.
        }
        // Show the popup menu.
        popup.show()
    }

    private fun orderRecyclerData(username: String, asunto: String, asc: String) {
        cuentaAdapter = CuentaAdapter()
        val db = DBController(mContext!!)
        cuentaAdapter.submitList(
            db.sortCuentas(username, asunto, asc)
        )

        recycler.apply {
            layoutManager = GridLayoutManager(mContext, 1)
            adapter = cuentaAdapter
        }
        db.close()
    }

    private fun setRecyclerData() {
        cuentaAdapter = CuentaAdapter()
        val db = DBController(mContext!!)
        cuentaAdapter.submitList(
            db.customMainCuentaSelect(
                "NOMUSUARIO",
                username
            )
        )

        recycler.apply {
            layoutManager = GridLayoutManager(mContext, 1)
            adapter = cuentaAdapter
        }
        db.close()
    }

    private fun initComponents(v: View) {
        search = v.findViewById(R.id.search)
        recycler = v.findViewById(R.id.recycler)

        button = v.findViewById(R.id.btnDrop)
        button.setOnClickListener { v: View ->
            showMenu(v, R.menu.popup_cuentas)
        }

        search.setOnQueryTextListener(this)
        search.imeOptions = EditorInfo.IME_ACTION_DONE
        setRecyclerData()
    }

}
