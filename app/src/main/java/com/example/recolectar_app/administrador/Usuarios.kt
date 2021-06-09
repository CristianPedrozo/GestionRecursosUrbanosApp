package com.example.recolectar_app.administrador

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.recolectar_app.Objetos.Contenedor.Contenedor
import com.example.recolectar_app.R
import com.example.recolectar_app.Usuario
import com.example.recolectar_app.UsuarioAdapter
import com.example.recolectar_app.adapters.ContenedorListAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.auth.User
import com.google.gson.Gson

class Usuarios : Fragment() {

    lateinit var v: View
    //Para botón flotante Agregar Contenedor
    lateinit var botton_agregar: FloatingActionButton
    //
    lateinit var recContenedores : RecyclerView
    var contenedores : MutableList<Usuario> = ArrayList<Usuario>()
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var contenedorListAdapter: ContenedorListAdapter

    lateinit var mRecyclerView : RecyclerView
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
    ): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_administrador_usuarios, container, false)
        val button = v.findViewById<Button>(R.id.btnIrPantallaAgregarUsuario)
        button.setOnClickListener {
            redireccionarAUsuarios()
        }
        getUsers()
        return v
    }

    companion object {
        fun newInstance() = Usuarios()
    }

    fun redireccionarAUsuarios(){
        var action = UsuariosDirections.actionUsuariosToDatos("")
        v.findNavController().navigate(action)
    }

    fun setUpRecyclerView(users:MutableList<Usuario>){
        mRecyclerView = v.findViewById<RecyclerView>(R.id.rvUsuario)
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.layoutManager = LinearLayoutManager(v.context)
        mAdapter.UsuarioAdapter(users, v.context)
        mRecyclerView.adapter = mAdapter
    }

    fun getUsers(){
        db.collection("usuarios")
            .get()
            .addOnSuccessListener { result ->
                var users:MutableList<Usuario> = ArrayList()
                for (document in result) {
                    users.add(Usuario(document.getString("razonSocial"), document.id,  document.getString("distrito"), document.getString("jefe"), document.getString("horarioEntrada"),document.getString("horarioSalida"), document.getBoolean("esAdmin"),"https://www.uclg-planning.org/sites/default/files/styles/featured_home_left/public/no-user-image-square.jpg?itok=PANMBJF-"))
                    setUpRecyclerView(users)
                }
            }
    }

}