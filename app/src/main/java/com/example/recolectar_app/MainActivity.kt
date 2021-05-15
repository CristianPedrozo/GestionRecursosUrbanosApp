package com.example.recolectar_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.view.View
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btnIniciar=findViewById<Button>(R.id.b_Inicio)
        btnIniciar.setOnClickListener{
            UtilidadesMaps.webServiceObtenerRuta(this,"")
            startActivity(Intent(this,RecorridoConductor::class.java))
        }
    }

    fun login(view: View) {
        val email = findViewById<EditText>(R.id.login_email).text.toString()
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
        }
    }
}