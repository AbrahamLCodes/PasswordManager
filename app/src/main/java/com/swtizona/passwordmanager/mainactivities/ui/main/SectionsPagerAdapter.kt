package com.swtizona.passwordmanager.mainactivities.ui.main

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.swtizona.passwordmanager.mainactivities.HomeTabActivity
import com.swtizona.passwordmanager.tabfragments.*

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
                fragment = CorreosFragment(HomeTabActivity.usuarioIntent!!.getNombre())
            }
            1 -> {
                fragment = CuentasFragment(HomeTabActivity.usuarioIntent!!.getNombre())
            }
            2 -> {
                fragment = ContraseniasFragment(HomeTabActivity.usuarioIntent!!.getNombre())
            }
            3 -> {
                fragment = NotasFragment(HomeTabActivity.usuarioIntent!!.getNombre())
            }
            4 -> {
                fragment = TarjetasFragment(HomeTabActivity.usuarioIntent!!.getNombre())
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