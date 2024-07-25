package com.app.navig_botttom_menu


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    lateinit var drawerLayout: DrawerLayout
    lateinit var  actionBarDrawerToggle: ActionBarDrawerToggle
    lateinit var   bottomNavigation : BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drawerLayout = findViewById(R.id.drawerLayout)
        actionBarDrawerToggle = ActionBarDrawerToggle(this,drawerLayout,R.string.nav_open,R.string.nav_close)

        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()



        // show home icon on the app bar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle("Drawer and bottom menu")


        val firstFragment = HomeFragment()
        val secondFragment = DashboardFragment()
        val thirdFragment = NotificationsFragment()

        setCurrentFragment(firstFragment)

        bottomNavigation = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        createBadge(R.id.nav_notifications)

        bottomNavigation  .setOnItemSelectedListener {
            when(it.itemId){

                R.id.nav_home-> setCurrentFragment(firstFragment)
                R.id.nav_dashboard->setCurrentFragment(secondFragment)
                R.id.nav_notifications-> {
                    setCurrentFragment(thirdFragment)
                    removeBadge(R.id.nav_notifications)
                }
            }
            true
        }


        var navigationView = findViewById<NavigationView>(R.id.navigationView)

        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_home -> {
                    setCurrentFragment(HomeFragment())
                }
                R.id.nav_dashboard -> {
                    setCurrentFragment(DashboardFragment())
                }
                R.id.nav_notifications -> {
                    setCurrentFragment(NotificationsFragment())
                }
            }
            menuItem.isChecked = true
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }
    }

    private fun createBadge(menuItem : Int) {
        val badge = bottomNavigation.getOrCreateBadge(menuItem)
        badge.isVisible = true
        badge.number = 1
    }

    private fun removeBadge(menuItem : Int) {
        bottomNavigation.removeBadge(menuItem)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (actionBarDrawerToggle.onOptionsItemSelected(item)){
            true
        }else{
            super.onOptionsItemSelected(item)
        }

    }

    private fun setCurrentFragment(fragment: Fragment)=
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.nav_host_fragment,fragment)
            commit()
        }

}