package com.example.recolectar_app.empleado

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.recolectar_app.R
import com.example.recolectar_app.databinding.ActivityEmpleadoBinding

class EmpleadoActivity : AppCompatActivity() {
    private lateinit var binding : ActivityEmpleadoBinding
    private lateinit var navHostFragment : NavHostFragment


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmpleadoBinding.inflate(layoutInflater)
        setContentView(binding.root)


        navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment

        NavigationUI.setupWithNavController(binding.bottomBar, navHostFragment.navController)

    }



}