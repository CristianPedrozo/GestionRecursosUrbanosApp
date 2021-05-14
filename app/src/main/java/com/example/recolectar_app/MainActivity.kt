package com.example.recolectar_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate

class MainActivity : AppCompatActivity() {
    var numero = 0;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun login(view: View) {
        if(numero == 1) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            delegate.applyDayNight()
            numero = 0;
        }
        else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            delegate.applyDayNight()
            numero = 1;
        }

        /*val email = findViewById<EditText>(R.id.login_email).text.toString()
        val password = findViewById<EditText>(R.id.login_password).text.toString()
        //hacer llamado a fiware para corroborar si el usuario existe, by email.

        if(email == "admin@admin.com" && password == "admin") {
            //Redirigir a vista admin
            //val intent = Intent(this, DashboardAdminActivity::class.java)
            //startActivity(intent)
        }else if(email == "empleado@empleado.com" && password == "empleado"){
            //Redirigir a vista empleado
            //val intent = Intent(this, DashboardAdminActivity::class.java)
            //startActivity(intent)
        } else {
            Toast.makeText(this, "Email o contraseña invalidos", Toast.LENGTH_SHORT).show()
        }*/
    }
}