package com.madrat.abiturhelper.activities

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.google.android.material.navigation.NavigationView
import com.madrat.abiturhelper.R
import com.madrat.abiturhelper.interfaces.activities.AppActivityMVP
import com.madrat.abiturhelper.presenters.activities.AppPresenter

class AppActivity : AppCompatActivity(), AppActivityMVP.View {
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var appPresenter: AppPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app)
        setupMVP()
        setupActivity()
    }

    override fun setupMVP() {
        appPresenter = AppPresenter(this)
    }

    override fun setupActivity() {
        val actionBar: ActionBar? = supportActionBar
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        val navigationView: NavigationView = findViewById(R.id.navigationView)
        val navController = Navigation.findNavController(this, R.id.navHostFragment)

        drawerLayout = findViewById(R.id.drawerLayout)

        actionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_menu)
        }

        setSupportActionBar(toolbar)

        NavigationUI.setupWithNavController(navigationView, navController)
        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                drawerLayout.openDrawer(GravityCompat.START)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}