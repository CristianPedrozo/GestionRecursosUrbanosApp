package com.example.recolectar_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

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
}