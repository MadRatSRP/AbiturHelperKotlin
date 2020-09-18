package com.madrat.abiturhelper.ui.activities

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.madrat.abiturhelper.R
import com.madrat.abiturhelper.databinding.ActivityAppBinding
import com.madrat.abiturhelper.data.interfaces.activities.AppActivityMVP
import com.madrat.abiturhelper.data.presenters.activities.AppPresenter

class AppActivity : AppCompatActivity(), AppActivityMVP.View {
    private lateinit var presenter: AppPresenter
    // ViewBinding variable
    private lateinit var binding: ActivityAppBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // ViewBinding initialization
        binding = ActivityAppBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        // Инициализируем presenter
        setupMVP()
        setupActivity()
    }

    override fun setupMVP() {
        presenter = AppPresenter(this)
    }
    override fun setupActivity() {
        val actionBar: ActionBar? = supportActionBar
        val navController = Navigation.findNavController(this, R.id.navHostFragment)

        actionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_menu)
        }

        setSupportActionBar(binding.toolbar)

        NavigationUI.setupWithNavController(binding.navigationView, navController)
        NavigationUI.setupActionBarWithNavController(this, navController, binding.drawerLayout)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                binding.drawerLayout.openDrawer(GravityCompat.START)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}