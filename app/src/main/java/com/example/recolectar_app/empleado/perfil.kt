package com.example.recolectar_app.empleado

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.recolectar_app.ui.view.MainActivity
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
/*<<<<<<< HEAD
    ): View? {
        v = inflater.inflate(R.layout.fragment_perfil, container, false)
        cargarUltimosRegistros()
        val btnLogOut = v.findViewById<Button>(R.id.btnLogOut_empleado)
        btnLogOut.setOnClickListener{
            logOut()
        }
        /*val btnEntrada = v.findViewById<Button>(R.id.btnEntrada)
        btnLogOut.setOnClickListener{
        }
        val btnSalida = v.findViewById<Button>(R.id.btnSalida)
        btnLogOut.setOnClickListener{
        }*/

        return v
=======*/
    ): View {
        _binding = FragmentPerfilBinding.inflate(layoutInflater,container,false)

        binding.btnLogOutEmpleado.setOnClickListener{
            logOut()
        }
        return binding.root
/*>>>>>>> e26dffbbb5558a80e008862d8820d71e60a0fca0*/
    }

    fun cargarUltimosRegistros(){
        var usuario = getUserInstance()
        usuario?.email

    }

    fun getUserInstance(): FirebaseUser?{
        return FirebaseAuth.getInstance().currentUser
    }

    fun logOut(){
        Firebase.auth.signOut()
/*<<<<<<< HEAD
        var algo = UsuarioGlobal.email
        val intent = Intent(v.context, MainActivity::class.java)
=======*/
        val intent = Intent(binding.root.context, MainActivity::class.java)
/*>>>>>>> e26dffbbb5558a80e008862d8820d71e60a0fca0*/
        startActivity(intent)
    }
}