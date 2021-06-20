package com.example.recolectar_app.empleado

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.recolectar_app.MainActivity
import com.example.recolectar_app.databinding.FragmentPerfilBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class perfil : Fragment() {
    private var _binding: FragmentPerfilBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPerfilBinding.inflate(layoutInflater,container,false)

        binding.btnLogOutEmpleado.setOnClickListener{
            logOut()
        }
        return binding.root

    }

    fun cargarUltimosRegistros(){
        val usuario = getUserInstance()
        usuario?.email

    }

    fun getUserInstance(): FirebaseUser?{
        return FirebaseAuth.getInstance().currentUser
    }

    fun logOut(){
        Firebase.auth.signOut()
        val intent = Intent(binding.root.context, MainActivity::class.java)
        startActivity(intent)
    }
}