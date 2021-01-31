package portafolio.apps.passwordmanager.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.core.view.GravityCompat
import androidx.core.view.get
import androidx.core.view.iterator
import androidx.drawerlayout.widget.DrawerLayout
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton
import com.google.android.material.internal.NavigationMenu
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayoutMediator
import io.github.yavski.fabspeeddial.FabSpeedDial
import portafolio.apps.passwordmanager.R
import portafolio.apps.passwordmanager.activities.ui.main.SectionsPagerAdapter
import portafolio.apps.passwordmanager.formactivities.*
import kotlin.properties.Delegates

class HomeTabActivity : AppCompatActivity(),
    FabSpeedDial.MenuListener,
    NavigationView.OnNavigationItemSelectedListener{

    private lateinit var drawer: DrawerLayout
    private lateinit var sideMenu: NavigationView
    private lateinit var menu: MaterialToolbar
    private lateinit var logOutbtn: Button
    private var tab by Delegates.notNull<Int>()

    companion object {
        lateinit var username : String
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_tab)

        username = intent.getStringExtra("username").toString()

        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        val tabs: TabLayout = findViewById(R.id.tabs)
        viewPager.adapter = sectionsPagerAdapter
        tabs.setupWithViewPager(viewPager)
        var currenttab = 0
        val tabsnum = tabs.tabCount
        Log.d("mira","!!!!!"+tabsnum)



        while (currenttab < tabsnum+1){
            when(currenttab){
                0->{
                    val tab = tabs.getTabAt(0)
                    tab?.icon = resources.getDrawable(R.drawable.fabcorreo)
                    tab?.text = "correos"
                }
                1-> {
                    val tab = tabs.getTabAt(1)
                    tab?.icon = resources.getDrawable(R.drawable.fabpassword)
                    tab?.text = "cuentas"

                }
                2->{
                    val tab = tabs.getTabAt(2)
                    tab?.icon = resources.getDrawable(R.drawable.key_24px)
                    tab?.text = "contraseñas"
                }
                3-> {
                    val tab = tabs.getTabAt(3)
                    tab?.icon = resources.getDrawable(R.drawable.fabnotas)
                    tab?.text = "notas"
                }
                4->{
                    val tab = tabs.getTabAt(4)
                    tab?.icon = resources.getDrawable(R.drawable.fabtarjetas)
                    tab?.text = "pagos"
                }
            }
            currenttab++
        }
        initComponents()
    }

    override fun onBackPressed() {
        Toast.makeText(
            applicationContext,
            "No le des pa atras we",
            Toast.LENGTH_SHORT
        ).show()
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

    override fun onNavigationItemSelected(it: MenuItem): Boolean {
        when (it.itemId) {// it comes from an embedded parameter of the Listener
            R.id.creditItem -> {
                Toast.makeText(
                    applicationContext,
                    "a prro",
                    Toast.LENGTH_SHORT
                ).show()

            }
            R.id.infoItem -> {
                Toast.makeText(
                    applicationContext,
                    "Info Item",
                    Toast.LENGTH_SHORT
                ).show()
            }
            R.id.rateItem -> {
                Toast.makeText(
                    applicationContext,
                    "Rate Item",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        return true
    }

    private fun userHeader() {
        val headerView = sideMenu.getHeaderView(0)
        // Setting header username TextView
        val hu: TextView = headerView.findViewById(R.id.headerUser)
        hu.setText("Usuario: " + intent.getStringExtra("username"))
    }

    private fun initComponents(){
        drawer = findViewById(R.id.drawer)
        sideMenu = findViewById(R.id.sideMenu)
        sideMenu.bringToFront() // This line makes the SideMenu clickeable
        menu = findViewById(R.id.menu)
        logOutbtn = findViewById(R.id.btnCerrar)
        logOutbtn.setOnClickListener{
            Toast.makeText(
                applicationContext,
                "log out",
                Toast.LENGTH_SHORT
            ).show()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        menu.setNavigationOnClickListener{
            if(drawer.isDrawerOpen(GravityCompat.START)){
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