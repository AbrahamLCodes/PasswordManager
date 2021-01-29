package portafolio.apps.passwordmanager.activities

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.internal.NavigationMenu
import com.google.android.material.navigation.NavigationView
import io.github.yavski.fabspeeddial.FabSpeedDial
import portafolio.apps.passwordmanager.R
import portafolio.apps.passwordmanager.activities.ui.main.SectionsPagerAdapter
import portafolio.apps.passwordmanager.formactivities.*

class HomeTabActivity : AppCompatActivity(),
    View.OnClickListener,
    FabSpeedDial.MenuListener,
    NavigationView.OnNavigationItemSelectedListener{

    private lateinit var drawer: DrawerLayout
    private lateinit var sideMenu: NavigationView
    private lateinit var menu: ImageView

    companion object {
        lateinit var username : String
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_tab)

        username = intent.getStringExtra("username").toString()

        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)

        initComponents()
    }

    override fun onBackPressed() {
        Toast.makeText(
            applicationContext,
            "No le des pa atras we",
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onClick(v: View?) {
        if(v != null){
            when(v.id){
                R.id.menu -> {
                    if(drawer.isDrawerOpen(GravityCompat.START)){
                        drawer.closeDrawer(GravityCompat.START)
                    } else {
                        drawer.openDrawer(GravityCompat.START)
                    }
                }
            }
        }
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
            R.id.homeItem -> {
                Toast.makeText(
                    applicationContext,
                    "Home Item",
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
        menu.setOnClickListener(this)
        sideMenu.setNavigationItemSelectedListener(this)
        userHeader()
        val fabSpeed: FabSpeedDial = findViewById(R.id.fabSpeed)
        fabSpeed.setMenuListener(this)
    }
}