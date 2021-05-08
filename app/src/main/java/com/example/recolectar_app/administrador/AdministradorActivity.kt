package com.example.recolectar_app.administrador

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.recolectar_app.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class AdministradorActivity : AppCompatActivity() {

    private lateinit var bottomNavView: BottomNavigationView
    private lateinit var navHostView: NavHostFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_administrador)
        navHostView = supportFragmentManager.findFragmentById(R.id.nav_host_admin) as NavHostFragment

        bottomNavView = findViewById(R.id.bottom_bar_admin)
        NavigationUI.setupWithNavController(bottomNavView, navHostView.navController)
    }
}