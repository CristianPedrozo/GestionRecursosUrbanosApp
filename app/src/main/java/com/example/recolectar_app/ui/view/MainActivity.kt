package com.example.recolectar_app.ui.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.recolectar_app.administrador.AdministradorActivity
import com.example.recolectar_app.core.UsuarioGlobal
import com.example.recolectar_app.databinding.ActivityMainBinding
import com.example.recolectar_app.empleado.EmpleadoActivity
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import wu.seal.jsontokotlin.utils.containsAnyOf
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    private val TAG = "Main Actv"
    private lateinit var binding : ActivityMainBinding
    private lateinit var auth: FirebaseAuth
    val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            binding = ActivityMainBinding.inflate(layoutInflater)
            setContentView(binding.root)
        }catch (e:Exception){
            Log.e(TAG,"onCreate",e)
        }

        // Initialize Firebase Auth
        auth = Firebase.auth
        binding.btnLogin.setOnClickListener {
            login()
        }
        val user = getUserInstance()
        if(user != null)
            consultarUsuario(user.email.toString())
    }
    fun login() {
        var email = binding.loginEmail.text.toString()
        if(!email.contains("@"))
            email = "$email@fiware.com.ar"

        val password = binding.loginPassword.text.toString()
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    consultarUsuario(email)
                } else {
                    val usuarioNoExiste = task.exception?.message?.contains("There is no user")
                    if (usuarioNoExiste != null && usuarioNoExiste == true) {
                        Toast.makeText(this, "Email no registrado, contacte a su administrador.", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "Email y/o contraseña incorrectos", Toast.LENGTH_SHORT).show()
                    }
                }
            }
    }

    fun consultarUsuario(email: String): Task<DocumentSnapshot> {
        Log.d("TAG", "Empezando")
        return db.collection("usuarios").document(email)
            .get()
            .addOnSuccessListener {
                UsuarioGlobal.usuario = email
                UsuarioGlobal.zona = it.getString("zona")
                UsuarioGlobal.email = it.getString("email")
                UsuarioGlobal.esAdmin = it.getBoolean("esAdmin")
                UsuarioGlobal.estaActivo = it.getBoolean("estaActivo")
                UsuarioGlobal.horarioEntrada = it.getString("horarioEntrada")
                UsuarioGlobal.horarioSalida = it.getString("horarioSalida")
                UsuarioGlobal.razonSocial = it.getString("razonSocial")

                if(UsuarioGlobal.esAdmin == true){
                    val intent = Intent(this, AdministradorActivity::class.java)
                    startActivity(intent)
                }
                else{
                    val intent = Intent(this, EmpleadoActivity::class.java)
                    startActivity(intent)
                }
            }
    }

    fun getUserInstance():FirebaseUser?{
        return FirebaseAuth.getInstance().currentUser
    }

}