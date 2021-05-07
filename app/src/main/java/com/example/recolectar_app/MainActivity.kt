package com.example.recolectar_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import com.example.recolectar_app.empleado.EmpleadoActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun login(view: View) {
        val email = findViewById<EditText>(R.id.login_email).text.toString()
        val password = findViewById<EditText>(R.id.login_password).text.toString()

        if(email == "admin@admin.com" && password == "admin") {
            val intent = Intent(this, DashboardAdminActivity::class.java)
            startActivity(intent)
        } else {
            val intent2 = Intent(this, EmpleadoActivity::class.java)
            startActivity(intent2)
            //Toast.makeText(this, "Email o contraseña invalidos", Toast.LENGTH_SHORT).show()
        }
    }
}