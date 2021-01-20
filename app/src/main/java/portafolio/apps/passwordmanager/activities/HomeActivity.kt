package portafolio.apps.passwordmanager.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.internal.NavigationMenu

import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayout
import io.github.yavski.fabspeeddial.FabSpeedDial
import portafolio.apps.passwordmanager.R
import portafolio.apps.passwordmanager.adapters.*
import portafolio.apps.passwordmanager.database.DBController
import portafolio.apps.passwordmanager.formactivities.*
import portafolio.apps.passwordmanager.forms_views_activities.View_correo
import portafolio.apps.passwordmanager.fragments.PasswordFragment
import javax.sql.DataSource

class HomeActivity : AppCompatActivity(),
    View.OnClickListener,
    NavigationView.OnNavigationItemSelectedListener,
    TabLayout.OnTabSelectedListener,
    FabSpeedDial.MenuListener {
    private lateinit var drawer: DrawerLayout
    private lateinit var sideMenu: NavigationView
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
    private var state: Int = 0
    private lateinit var search: SearchView
    private lateinit var fabSpeed: FabSpeedDial
    private lateinit var recycler: RecyclerView
    private lateinit var correoAdapter: CorreoAdapter
    private lateinit var cuentaAdapter: CuentaAdapter
    private lateinit var contraseniaAdapter: ContraseniaAdapter
    private lateinit var notaAdapter: NotasAdapter
    private lateinit var tarjetaAdatper: PagosAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        initComponents()
    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {
            }
        }
    }

    override fun onNavigationItemSelected(it: MenuItem): Boolean {
        when (it.itemId) {// it comes from an embedded parameter of the Listener
            R.id.homeItem -> {
                state = 0
                Toast.makeText(
                    applicationContext,
                    "Home Item",
                    Toast.LENGTH_SHORT
                ).show()
            }
            R.id.infoItem -> {
                state = 1
                Toast.makeText(
                    applicationContext,
                    "Info Item",
                    Toast.LENGTH_SHORT
                ).show()
            }
            R.id.rateItem -> {
                state = 2
                Toast.makeText(
                    applicationContext,
                    "Rate Item",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        return true
    }

    override fun onTabSelected(tab: TabLayout.Tab?) {
        if (tab != null) {
            when (tab.position) {
                0 -> {
                    Toast.makeText(
                        applicationContext,
                        "Todo",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                1 -> {
                    setRecyclerAdapter("correos")
                }
                2 -> {
                    setRecyclerAdapter("cuentas")
                }
                3 -> {
                    setRecyclerAdapter("contrasenias")
                }
                4 -> {
                    setRecyclerAdapter("notas")
                }
                5 -> {
                    setRecyclerAdapter("tarjetas")
                }
            }
        }
    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {

    }

    override fun onTabReselected(tab: TabLayout.Tab?) {

    }

    override fun onPrepareMenu(p0: NavigationMenu?): Boolean {
        return true
    }

    override fun onMenuItemSelected(menuItem: MenuItem?): Boolean {
        if (menuItem != null) {
            when (menuItem.itemId) {
                R.id.correoItem -> {
                    startActivity(
                        Intent(this,FormCorreo::class.java).apply {
                            putExtra("username", intent.getStringExtra("username"))
                        }
                    )
                    return true
                }
                R.id.cuentaItem -> {
                    startActivity(
                        Intent(this, FormCuenta::class.java).apply {
                            putExtra("username", intent.getStringExtra("username"))
                        }
                    )
                }
                R.id.contraseniaItem -> {
                    startActivity(
                        Intent(this, FormContrasenia::class.java).apply {
                            putExtra("username", intent.getStringExtra("username"))
                        }
                    )
                }
                R.id.notaItem -> {
                    startActivity(
                        Intent(this, FormNota::class.java).apply {
                            putExtra("username", intent.getStringExtra("username"))
                        }
                    )
                }
                R.id.tarjetaItem -> {
                    startActivity(
                        Intent(this, FormPagos::class.java).apply {
                            putExtra("username", intent.getStringExtra("username"))
                        }
                    )
                }
            }
        }
        return false
    }

    override fun onMenuClosed() {

    }

    private fun userHeader() {
        val headerView = sideMenu!!.getHeaderView(0)

        // Setting header username TextView
        val hu: TextView = headerView.findViewById(R.id.headerUser)
        hu.setText("Usuario: " + intent.getStringExtra("username"))
    }

    private fun setRecyclerAdapter(type: String) {
        val db = DBController(applicationContext)
        when (type) {
            "correos" -> {
                correoAdapter.submitList(
                    db.customCorreoSelect(
                        "NOMUSUARIO",
                        intent.getStringExtra("username")!!
                    )
                )

                recycler.apply {
                    layoutManager = GridLayoutManager(applicationContext, 1)
                    adapter = correoAdapter
                }
            }
            "cuentas" -> {
                cuentaAdapter.submitList(
                    db.customMainCuentaSelect(
                        "NOMUSUARIO",
                        intent.getStringExtra("username")!!
                    )
                )

                recycler.apply {
                    adapter = cuentaAdapter
                    layoutManager = GridLayoutManager(applicationContext, 1)
                }
            }
            "contrasenias" -> {
                contraseniaAdapter.submitList(
                    db.customContraseniaSelect(
                        "NOMUSUARIO",
                        intent.getStringExtra("username")!!
                    )
                )

                recycler.apply {
                    adapter = contraseniaAdapter
                    layoutManager = GridLayoutManager(applicationContext, 1)
                }
            }
            "notas" -> {
                notaAdapter.submitList(
                    db.customNotaSelect(
                        "NOMUSUARIO",
                        intent.getStringExtra("username")!!
                    )
                )

                recycler.apply {
                    adapter = notaAdapter
                    layoutManager = GridLayoutManager(applicationContext, 1)
                }
            }
            "tarjetas" -> {
                tarjetaAdatper.submitList(
                    db.customTarjetaSelect(
                        "NOMUSUARIO",
                        intent.getStringExtra("username")!!
                    )
                )

                recycler.apply {
                    adapter = tarjetaAdatper
                    layoutManager = GridLayoutManager(applicationContext, 1)
                }
            }
        }
    }

    private fun initAdapters() {
        correoAdapter = CorreoAdapter()
        cuentaAdapter = CuentaAdapter()
        contraseniaAdapter = ContraseniaAdapter()
        notaAdapter = NotasAdapter()
        tarjetaAdatper = PagosAdapter()
    }

    private fun initComponents() {
        drawer = findViewById(R.id.drawer)
        sideMenu = findViewById(R.id.sideMenu)
        toolbar = findViewById(R.id.toolbar)
        search = findViewById(R.id.search)
        fabSpeed = findViewById(R.id.fabSpeed)
        fabSpeed!!.setMenuListener(this)
        recycler = findViewById(R.id.recycler)

        setSupportActionBar(toolbar)
        val toggle = ActionBarDrawerToggle(
            this,
            drawer,
            toolbar,
            R.string.open,
            R.string.close
        )

        drawer!!.addDrawerListener(toggle)
        sideMenu!!.bringToFront() //This lines makes the SideMenu clickeable
        toggle.syncState()
        sideMenu!!.setNavigationItemSelectedListener(this)

        var tabs: TabLayout? = findViewById(R.id.tabs)
        tabs!!.addOnTabSelectedListener(this)
        userHeader()

        initAdapters()
        setRecyclerAdapter("correos")
    }
}