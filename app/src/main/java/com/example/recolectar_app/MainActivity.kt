package com.example.recolectar_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.android.volley.toolbox.Volley
import com.example.recolectar_app.administrador.AdministradorActivity
import com.example.recolectar_app.empleado.EmpleadoActivity

class MainActivity : AppCompatActivity() {
    var url = "http://46.17.108.122:1026/v2/entities"



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val btnLogin = findViewById<Button>(R.id.btn_login)
        btnLogin.setOnClickListener { login() }

        val btnIniciar=findViewById<Button>(R.id.b_Inicio)
        btnIniciar.setOnClickListener{
            UtilidadesMaps.webServiceObtenerRuta(this,"")
            startActivity(Intent(this,RecorridoConductor::class.java))
        }


    }

    fun login() {
        val queue = Volley.newRequestQueue(this)
        val email = findViewById<EditText>(R.id.login_email).text.toString()
        val password = findViewById<EditText>(R.id.login_password).text.toString()

        //hacer llamado a fiware para corroborar si el usuario existe, by email.

        if(email == "admin@admin.com" && password == "admin") {
            val intent = Intent(this,AdministradorActivity::class.java)
            startActivity(intent)
//        }else if(email == "empleado@empleado.com" && password == "empleado"){
//            //Redirigir a vista empleado
//            //val intent = Intent(this, DashboardAdminActivity::class.java)
//            //startActivity(intent)
        } else {
//            val user = User("empleado:test${email}",email)
//            val jsonObject = JSONObject()
//            jsonObject.accumulate("id", "${user.getId()}")
//            jsonObject.accumulate("type", "Empleado")
//            jsonObject.accumulate("email", "${user.getEmail()}")
//
//
//            val jsonObjectRequest = JsonObjectRequest(url,jsonObject,
//                { response ->
//
//                },
//                { response ->
//
//                }
//            )
            //queue.add(jsonObjectRequest)
            val intent2 = Intent(this@MainActivity, EmpleadoActivity::class.java)
//            intent.putExtra("user",user)
            startActivity(intent2)
            //Toast.makeText(this, "Email o contraseña invalidos", Toast.LENGTH_SHORT).show()
        }
    }





}