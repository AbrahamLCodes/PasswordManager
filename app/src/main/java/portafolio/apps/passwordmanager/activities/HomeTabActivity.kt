package portafolio.apps.passwordmanager.activities

import android.content.Intent
import android.os.Bundle
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.internal.NavigationMenu
import com.google.android.material.navigation.NavigationView
import io.github.yavski.fabspeeddial.FabSpeedDial
import portafolio.apps.passwordmanager.R
import portafolio.apps.passwordmanager.activities.ui.main.SectionsPagerAdapter
import portafolio.apps.passwordmanager.database.DBController
import portafolio.apps.passwordmanager.datamodel.Usuario
import portafolio.apps.passwordmanager.formactivities.*
import portafolio.apps.passwordmanager.sidemenuactivities.InfoActivity
import portafolio.apps.passwordmanager.sidemenuactivities.OptionsActivity

class HomeTabActivity : AppCompatActivity(),
    FabSpeedDial.MenuListener,
    NavigationView.OnNavigationItemSelectedListener {

    private lateinit var drawer: DrawerLayout
    private lateinit var sideMenu: NavigationView
    private lateinit var menu: MaterialToolbar
    private lateinit var logOutbtn: Button
    private lateinit var sectionsPagerAdapter: SectionsPagerAdapter
    private lateinit var viewPager: ViewPager
    private lateinit var tabs: TabLayout

    companion object {
        lateinit var username: String
        var usuarioIntent: Usuario? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_tab)

        usuarioIntent = intent.getSerializableExtra("userObject") as? Usuario
        val db = DBController(applicationContext)
        usuarioIntent =
            db.selectUsuario(usuarioIntent!!.getNombre(), usuarioIntent!!.getContrasenia())
        db.close()
        username = intent.getStringExtra("username").toString()

        sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        viewPager = findViewById(R.id.view_pager)
        tabs = findViewById(R.id.tabs)
        viewPager.adapter = sectionsPagerAdapter
        tabs.setupWithViewPager(viewPager)
        var currenttab = 0
        val tabsnum = tabs.tabCount



        while (currenttab < tabsnum + 1) {
            when (currenttab) {
                0 -> {
                    val tab = tabs.getTabAt(0)
                    tab?.icon = resources.getDrawable(R.drawable.fabcorreo)
                    tab?.text = "correos"
                }
                1 -> {
                    val tab = tabs.getTabAt(1)
                    tab?.icon = resources.getDrawable(R.drawable.fabpassword)
                    tab?.text = "cuentas"
                }
                2 -> {
                    val tab = tabs.getTabAt(2)
                    tab?.icon = resources.getDrawable(R.drawable.key_24px)
                    tab?.text = "contraseñas"
                }
                3 -> {
                    val tab = tabs.getTabAt(3)
                    tab?.icon = resources.getDrawable(R.drawable.fabnotas)
                    tab?.text = "notas"
                }
                4 -> {
                    val tab = tabs.getTabAt(4)
                    tab?.icon = resources.getDrawable(R.drawable.fabtarjetas)
                    tab?.text = "pagos"
                }
            }
            currenttab++
        }
        initComponents()
    }


    override fun onStop() {
        super.onStop()
        if (usuarioIntent!!.getChecked() == 1) {
            finish()
        }
    }


    override fun onBackPressed() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Cerrar Sesión")
        builder.setMessage("¿Seguro que desea cerrar sesión?")

        builder.setPositiveButton("Salir") { _, _ ->
            finish()
        }

        builder.setNegativeButton("Cancelar") { _, _ ->
            //Do no thing
        }

        builder.show()
    }

    override fun onPrepareMenu(p0: NavigationMenu?): Boolean {
        return true
    }

    override fun onMenuItemSelected(menuItem: MenuItem?): Boolean {
        if (menuItem != null) {
            when (menuItem.itemId) {
                R.id.correoItem -> {
                    startActivity(
                        Intent(this, FormCorreo::class.java).apply {
                            putExtra("userObject", usuarioIntent)
                        }
                    )
                    finish()
                }
                R.id.cuentaItem -> {
                    val db = DBController(applicationContext)
                    if (db.getRowCount(usuarioIntent!!.getNombre(),"CORREOS") > 0) {
                        startActivity(
                            Intent(this, FormCuenta::class.java).apply {
                                putExtra("userObject", usuarioIntent)
                                putExtra("username", usuarioIntent!!.getNombre())
                            }
                        )
                        finish()
                    } else {
                        Toast.makeText(
                            applicationContext,
                            "Si quieres registrar algo sin correo, ve a la pestaña de Contraseñas",
                            Toast.LENGTH_LONG
                        ).show()
                        viewPager.setCurrentItem(2)
                    }
                }
                R.id.contraseniaItem -> {
                    startActivity(
                        Intent(this, FormContrasenia::class.java).apply {
                            putExtra("userObject", usuarioIntent)
                        }
                    )
                    finish()
                }
                R.id.notaItem -> {
                    startActivity(
                        Intent(this, FormNota::class.java).apply {
                            putExtra("userObject", usuarioIntent)
                        }
                    )
                    finish()
                }
                R.id.tarjetaItem -> {
                    startActivity(
                        Intent(this, FormPagos::class.java).apply {
                            putExtra("userObject", usuarioIntent)
                        }
                    )
                    finish()
                }
            }
        }
        return true
    }

    override fun onMenuClosed() {

    }

    override fun onNavigationItemSelected(it: MenuItem): Boolean {
        when (it.itemId) {// it comes from an embedded parameter of the Listener
            R.id.optionItem -> {
                startActivity(Intent(this, OptionsActivity::class.java).apply {
                    putExtra("userObject", usuarioIntent)
                })
                finish()
            }
            R.id.infoItem -> {
                startActivity(Intent(this, InfoActivity::class.java).apply {
                    putExtra("userObject", usuarioIntent)
                })
                finish()
            }

        }
        return true
    }

    private fun userHeader() {
        val headerView = sideMenu.getHeaderView(0)
        // Setting header username TextView
        val hu: TextView = headerView.findViewById(R.id.headerUser)
        val user = intent.getSerializableExtra("userObject") as? Usuario
        hu.setText("Usuario: " + user!!.getNombre())
    }

    private fun initComponents() {
        drawer = findViewById(R.id.drawer)
        sideMenu = findViewById(R.id.sideMenu)
        sideMenu.bringToFront() // This line makes the SideMenu clickeable
        menu = findViewById(R.id.menu)
        logOutbtn = findViewById(R.id.btnCerrar)
        logOutbtn.setOnClickListener {
            onBackPressed()
        }
        menu.setNavigationOnClickListener {
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START)
            } else {
                drawer.openDrawer(GravityCompat.START)
            }
        }

        sideMenu.setNavigationItemSelectedListener(this)
        userHeader()
        val fabSpeed: FabSpeedDial = findViewById(R.id.fabSpeed)
        fabSpeed.setMenuListener(this)
    }
}