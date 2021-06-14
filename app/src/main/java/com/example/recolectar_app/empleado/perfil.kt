package com.example.recolectar_app.empleado

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.recolectar_app.MainActivity
import com.example.recolectar_app.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class perfil : Fragment() {
    lateinit var v: View
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_perfil, container, false)

        val btnLogOut = v.findViewById<Button>(R.id.btnLogOut_empleado)
        btnLogOut.setOnClickListener{
            logOut()
        }
        return v
    }

    fun logOut(){
        Firebase.auth.signOut()
        val intent = Intent(v.context, MainActivity::class.java)
        startActivity(intent)
    }
}