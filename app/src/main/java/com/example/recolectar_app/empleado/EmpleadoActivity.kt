package com.example.recolectar_app.empleado

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.recolectar_app.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class EmpleadoActivity : AppCompatActivity() {

    private lateinit var bottomNavView : BottomNavigationView
    private lateinit var navHostFragment : NavHostFragment




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_empleado)


        navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment

        bottomNavView = findViewById(R.id.bottom_bar)

        NavigationUI.setupWithNavController(bottomNavView, navHostFragment.navController)

    }



}