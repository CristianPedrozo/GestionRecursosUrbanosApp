package com.example.recolectar_app.administrador

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.recolectar_app.R
import com.example.recolectar_app.databinding.ActivityAdministradorBinding
import java.lang.Exception

class AdministradorActivity : AppCompatActivity() {
    private val TAG = "Administrator Activity"
    private lateinit var binding : ActivityAdministradorBinding
    private lateinit var navHostView: NavHostFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            binding = ActivityAdministradorBinding.inflate(layoutInflater)
            setContentView(binding.root)
        }catch(e : Exception){
            Log.e(TAG, "onCreateeee", e)
        }


        navHostView = supportFragmentManager.findFragmentById(R.id.nav_host_admin) as NavHostFragment

        NavigationUI.setupWithNavController(binding.bottomBarAdmin, navHostView.navController)
    }
}