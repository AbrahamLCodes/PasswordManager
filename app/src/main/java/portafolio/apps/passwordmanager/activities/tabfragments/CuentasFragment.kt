package portafolio.apps.passwordmanager.activities.tabfragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.PopupMenu
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import portafolio.apps.passwordmanager.R
import portafolio.apps.passwordmanager.adapters.CuentaAdapter
import portafolio.apps.passwordmanager.database.DBController

class CuentasFragment (username: String): Fragment() {

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

    private fun initComponents(v: View) {
        search = v.findViewById(R.id.search)
        recycler = v.findViewById(R.id.recycler)
        CorreosFragment.button = v.findViewById(R.id.btnDrop)
        CorreosFragment.button.setOnClickListener { v: View ->
            showMenu(v, R.menu.popup_menu)
        }
        setRecyclerData()
    }

    private fun showMenu(v: View, popupMenu: Int) {
        val popup = PopupMenu(context!!, v)
        popup.menuInflater.inflate(popupMenu, popup.menu)

        popup.setOnMenuItemClickListener { menuItem: MenuItem ->
            true
        }
        popup.setOnDismissListener {
            // Respond to popup being dismissed.
        }
        // Show the popup menu.
        popup.show()
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
    }

}
