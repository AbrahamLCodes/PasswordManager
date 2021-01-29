package portafolio.apps.passwordmanager.activities.ui.main

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import portafolio.apps.passwordmanager.activities.HomeTabActivity
import portafolio.apps.passwordmanager.activities.tabfragments.*

private val TAB_TITLES = arrayOf(
    "Correos",
    "Cuentas",
    "Contraseñas",
    "Notas",
    "Pagos"
)

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class SectionsPagerAdapter(private val context: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> {
                fragment = CorreosFragment(HomeTabActivity.username)
            }
            1 -> {
                fragment = CuentasFragment(HomeTabActivity.username)
            }
            2 -> {
                fragment = ContraseniasFragment(HomeTabActivity.username)
            }
            3 -> {
                fragment = NotasFragment(HomeTabActivity.username)
            }
            4 -> {
                fragment = TarjetasFragment(HomeTabActivity.username)
            }
        }
        return  fragment!!
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return TAB_TITLES[position]
    }

    override fun getCount(): Int {
        // Show 2 total pages.
        return 5
    }
}