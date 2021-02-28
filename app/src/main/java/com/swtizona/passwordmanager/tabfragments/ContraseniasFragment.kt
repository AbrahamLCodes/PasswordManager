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
import com.swtizona.passwordmanager.adapters.ContraseniaAdapter
import com.swtizona.passwordmanager.database.DBController

class ContraseniasFragment (username: String): Fragment(), SearchView.OnQueryTextListener {

    private lateinit var search: SearchView
    private var mContext: Context? = null
    private val username = username
    lateinit var button: ImageButton

    companion object {
        lateinit var recycler: RecyclerView
        lateinit var contraseniaAdapter: ContraseniaAdapter
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.tab_fragment_contrasenias,container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initComponents(view)
    }

    override fun onResume() {
        super.onResume()
        setRecyclerData()
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
        contraseniaAdapter.filter.filter(newText)
        return false
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    private fun initComponents(v: View) {
        search = v.findViewById(R.id.search)
        recycler = v.findViewById(R.id.recycler)
        CorreosFragment.button = v.findViewById(R.id.btnDrop)
        CorreosFragment.button.setOnClickListener { v: View ->
            showMenu(v, R.menu.popup_contrasenias)
        }

        search.setOnQueryTextListener(this)
        search.imeOptions = EditorInfo.IME_ACTION_DONE
        setRecyclerData()
    }

    private fun showMenu(v: View, popupMenu: Int) {
        val popup = PopupMenu(context!!, v)
        popup.menuInflater.inflate(popupMenu, popup.menu)

        popup.setOnMenuItemClickListener { menuItem: MenuItem ->
            when(menuItem.itemId){
                R.id.o1 -> {
                    orderRecyclerData(username, "ASUNTO", "ASC")
                }
                R.id.o2 -> {
                    orderRecyclerData(username, "ASUNTO", "DESC")
                }
                R.id.o3 -> {
                    orderRecyclerData(username, "FECHA", "DESC")
                }
                R.id.o4 -> {
                    orderRecyclerData(username, "FECHA","ASC")
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
        contraseniaAdapter = ContraseniaAdapter()
        val db = DBController(mContext!!)
        contraseniaAdapter.submitList(
            db.sortContrasenias(username, asunto, asc)
        )

        recycler.apply {
            layoutManager = GridLayoutManager(mContext, 1)
            adapter = contraseniaAdapter
        }
        db.close()
    }

    private fun setRecyclerData() {
        contraseniaAdapter = ContraseniaAdapter()
        val db = DBController(mContext!!)
        contraseniaAdapter.submitList(
            db.customContraseniaSelect(
                "NOMUSUARIO",
                username
            )
        )

        recycler.apply {
            layoutManager = GridLayoutManager(mContext, 1)
            adapter = contraseniaAdapter
        }
        db.close()
    }
}