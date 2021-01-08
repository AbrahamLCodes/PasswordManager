package portafolio.apps.passwordmanager.Activitys

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import portafolio.apps.passwordmanager.R

class HomeScreen : AppCompatActivity(), View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {

    var drawer: DrawerLayout? = null
    var sideMenu: NavigationView? = null
    var toolbar: androidx.appcompat.widget.Toolbar? = null
    var fab: FloatingActionButton? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)
        initComponents()
    }

    private fun initComponents() {
        Toast.makeText( // Showing with a Toast the username passed in Intent Extras
                applicationContext,
                intent.getStringExtra("username"),
                Toast.LENGTH_SHORT).show()

        drawer = findViewById(R.id.drawer)
        sideMenu = findViewById(R.id.sideMenu)
        toolbar = findViewById(R.id.toolbar)
        fab = findViewById(R.id.fab)
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

    private fun fabClicked() {
        Toast.makeText(
                applicationContext,
                "FAB Clicked",
                Toast.LENGTH_SHORT
        ).show()
    }
}
