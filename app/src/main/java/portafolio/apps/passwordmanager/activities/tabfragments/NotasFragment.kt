package portafolio.apps.passwordmanager.activities.tabfragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import portafolio.apps.passwordmanager.R
import portafolio.apps.passwordmanager.adapters.NotasAdapter
import portafolio.apps.passwordmanager.database.DBController

class NotasFragment (username: String): Fragment() {
    private lateinit var search: SearchView
    private var mContext: Context? = null
    private val username = username

    companion object {
        lateinit var recycler: RecyclerView
        lateinit var notasAdapter: NotasAdapter
    }

    override fun onResume() {
        super.onResume()
        setRecyclerData()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.tab_fragment_notas,container, false)
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
        setRecyclerData()
    }

    private fun setRecyclerData() {
        notasAdapter = NotasAdapter()
        val db = DBController(mContext!!)
        notasAdapter.submitList(
            db.customNotaSelect(
                "NOMUSUARIO",
                username
            )
        )

        recycler.apply {
            layoutManager = GridLayoutManager(mContext, 1)
            adapter = notasAdapter
        }
    }
}