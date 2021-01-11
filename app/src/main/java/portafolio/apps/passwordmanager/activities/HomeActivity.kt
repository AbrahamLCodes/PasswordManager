package portafolio.apps.passwordmanager.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayout
import portafolio.apps.passwordmanager.R
import portafolio.apps.passwordmanager.forms.Form_correo
import portafolio.apps.passwordmanager.fragments.PasswordFragment

class HomeActivity : AppCompatActivity(),
        View.OnClickListener,
        NavigationView.OnNavigationItemSelectedListener,
        TabLayout.OnTabSelectedListener {


    var drawer: DrawerLayout? = null
    var sideMenu: NavigationView? = null
    var toolbar: androidx.appcompat.widget.Toolbar? = null
    var fab: FloatingActionButton? = null
    var state:Int = 0
    var search:SearchView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        initComponents()
    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {
                R.id.fab -> {
                    fabClicked()
                }
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

    private fun fabClicked() {

        val intent = Intent(this,Form_correo::class.java)
        startActivity(intent)

    }

    override fun onTabSelected(tab: TabLayout.Tab?) {
        if (tab != null) {
            when (tab.position) {
                0 -> {
                    Toast.makeText(
                            applicationContext,
                            "Todos los elementos",
                            Toast.LENGTH_SHORT
                    ).show()
                }
                1 -> {
                    Toast.makeText(
                            applicationContext,
                            "Contraseñas",
                            Toast.LENGTH_SHORT
                    ).show()
                }
                2 -> {
                    Toast.makeText(
                            applicationContext,
                            "Notas",
                            Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {

    }

    override fun onTabReselected(tab: TabLayout.Tab?) {

    }

    private fun userHeader() {
        val headerView = sideMenu!!.getHeaderView(0)

        // Setting header username TextView
        val hu: TextView = headerView.findViewById(R.id.headerUser)
        hu.setText("Usuario: " + intent.getStringExtra("username"))
    }

    private fun initComponents() {
        drawer = findViewById(R.id.drawer)
        sideMenu = findViewById(R.id.sideMenu)
        toolbar = findViewById(R.id.toolbar)
        fab = findViewById(R.id.fab)
        search = findViewById(R.id.search)
        fab!!.setOnClickListener(this)

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
    }
}
