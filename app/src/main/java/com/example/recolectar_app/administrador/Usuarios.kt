package com.example.recolectar_app.administrador

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recolectar_app.ui.view.MainActivity
import com.example.recolectar_app.Usuario
import com.example.recolectar_app.UsuarioAdapter
import com.example.recolectar_app.databinding.FragmentAdministradorUsuariosBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import java.lang.Exception

class Usuarios : Fragment() {
    private val TAG = "Usuarios Adm Frag"
    private var _binding: FragmentAdministradorUsuariosBinding? = null
    private val binding get() = _binding!!
    var contenedores : MutableList<Usuario> = ArrayList()
    val mAdapter : UsuarioAdapter = UsuarioAdapter()
    val db = FirebaseFirestore.getInstance()

    /*override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val button = v.findViewById<Button>(R.id.btnIrPantallaAgregarUsuario)
        button.setOnClickListener {
            redireccionarAUsuarios()
        }
        getUsers()
    }*/

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        try{
            _binding = FragmentAdministradorUsuariosBinding.inflate(layoutInflater,container,false)
        }catch (e: Exception){
            Log.e(TAG,"onCreateView",e)
        }


        binding.btnIrPantallaAgregarUsuario.setOnClickListener {
            redireccionarAUsuarios()
        }

        binding.btnLogOut.setOnClickListener{
            logOut()
        }
        getUsers()
        return binding.root
    }

    fun logOut(){
        Firebase.auth.signOut()
        val intent = Intent(binding.root.context, MainActivity::class.java)
        startActivity(intent)
    }

    companion object {
        fun newInstance() = Usuarios()
    }

    fun redireccionarAUsuarios(){
        val action = UsuariosDirections.actionUsuariosToDatos("")
        binding.root.findNavController().navigate(action)
    }

    fun setUpRecyclerView(users:MutableList<Usuario>){
        binding.rvUsuario.setHasFixedSize(true)
        binding.rvUsuario.layoutManager = LinearLayoutManager(binding.root.context)
        mAdapter.UsuarioAdapter(users, binding.root.context)
        binding.rvUsuario.adapter = mAdapter
    }

    fun getUsers(){
        db.collection("usuarios")
            .get()
            .addOnSuccessListener { result ->
                val users:MutableList<Usuario> = ArrayList()
                for (document in result) {
                    users.add(Usuario(document.getString("razonSocial"), document.id,  document.getString("distrito"), document.getString("jefe"), document.getString("horarioEntrada"),document.getString("horarioSalida"), document.getBoolean("esAdmin"),"https://www.uclg-planning.org/sites/default/files/styles/featured_home_left/public/no-user-image-square.jpg?itok=PANMBJF-"))
                    setUpRecyclerView(users)
                }
            }
    }

}