package com.example.recolectar_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.android.volley.toolbox.Volley
import com.example.recolectar_app.administrador.AdministradorActivity
import com.example.recolectar_app.empleado.EmpleadoActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    val db = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val button = findViewById<Button>(R.id.btn_login)
        // Initialize Firebase Auth
        auth = Firebase.auth
        button.setOnClickListener {
            login()
        }
        val intent = Intent(this, AdministradorActivity::class.java)
        startActivity(intent)
    }

    fun login() {
        val email = findViewById<EditText>(R.id.login_email).text.toString()
        val password = findViewById<EditText>(R.id.login_password).text.toString()
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    consultarUsuario(email)
                } else {
                    var usuarioNoExiste = task.exception?.message?.contains("There is no user")
                    if (usuarioNoExiste != null && usuarioNoExiste == true) {
                        Toast.makeText(this, "Email no registrado, contacte a su administrador.", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "Email y/o contraseña incorrectos", Toast.LENGTH_SHORT).show()
                    }
                }
            }
    }

    fun consultarUsuario(email: String) {
        Log.d("TAG", "Empezando")
        db.collection("usuarios").document(email)
            .get()
            .addOnSuccessListener {
                var esAdmin = it.getBoolean("esAdmin")
                if(esAdmin == true){
                    val intent = Intent(this, AdministradorActivity::class.java)
                    startActivity(intent)
                }
                else{
                    val intent = Intent(this, EmpleadoActivity::class.java)
                    startActivity(intent)
                }

            }
    }

    fun getUserInstance(){
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            // User is signed in
        } else {
            // No user is signed in
        }
    }
}