package com.example.recolectar_app.administrador

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.recolectar_app.Objetos.Contenedor.Contenedor
import com.example.recolectar_app.R
import com.example.recolectar_app.RequestHandler
import com.example.recolectar_app.adapters.CamionListAdapter
import com.example.recolectar_app.adapters.ContenedorListAdapter
import com.example.recolectar_app.adapters.ZonaListAdapter
import com.example.recolectar_app.entities.Camion
import com.example.recolectar_app.zonas.Zona
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson


class Contenedores : Fragment() {
    /*

    lateinit var v: View
    //Para botón flotante Agregar Contenedor
    lateinit var botton_agregar: FloatingActionButton
    //
    lateinit var recContenedores : RecyclerView
    var contenedores : MutableList<Contenedor> = ArrayList<Contenedor>()
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var contenedorListAdapter: ContenedorListAdapter

    companion object {
        fun newInstance() = Contenedores()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v =  inflater.inflate(R.layout.fragment_list_contenedores, container, false)
        recContenedores = v.findViewById(R.id.rec_contenedores)
        //Para el botón flotante agregar Contenedor
        botton_agregar = v.findViewById(R.id.boton_agregar)
        botton_agregar.setOnClickListener(){

            val action = ContenedoresDirections.actionContenedoresToAltaContenedor()
            v.findNavController().navigate(action)
        }
        //
        return v
    }


    var url = "http://46.17.108.122:1026/v2/entities/?type=WasteContainer"
    override fun onStart() {
        super.onStart()
        var gson = Gson()
        val queue = Volley.newRequestQueue(activity)
        val jsonArrayRequest = JsonArrayRequest(url,
            { response ->
                Log.d("Response Prueba", response.toString())
                for (i in 0 until response.length()){
                    val contenedor : Contenedor = gson.fromJson(response.getJSONObject(i).toString(),Contenedor::class.java)
                    Log.d("Contenedor", contenedor.toString())
                    contenedores.add(contenedor)
                }
                recContenedores.setHasFixedSize(true)
                linearLayoutManager = LinearLayoutManager(context)
                recContenedores.layoutManager = linearLayoutManager

                contenedorListAdapter = ContenedorListAdapter(contenedores);


                recContenedores.adapter = contenedorListAdapter
            }, {print("prueba error")})
        queue.add(jsonArrayRequest)
    }


    fun onItemClick ( position : Int ) : Boolean {
        Snackbar.make(v,position.toString(), Snackbar.LENGTH_SHORT).show()
        return true
    }

     */
    var url = "http://46.17.108.122:1026/v2/entities/?type=WasteContainer"
    lateinit var thiscontext : Context
    lateinit var v: View
    lateinit var botton_agregar: FloatingActionButton
    lateinit var recContenedor : RecyclerView
    var contenedores : MutableList<Contenedor> = ArrayList()
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var contenedorListAdapter: ContenedorListAdapter


    companion object {
        fun newInstance() = Contenedores()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (container != null) {
            thiscontext = container.context
        };
        var requestHandler = RequestHandler.getInstance(thiscontext)
        getData(requestHandler)
        v =  inflater.inflate(R.layout.fragment_list_contenedores, container, false)
        recContenedor = v.findViewById(R.id.rec_contenedores)
        botton_agregar = v.findViewById(R.id.boton_agregar)
        botton_agregar.setOnClickListener() {
            val action = ContenedoresDirections.actionContenedoresToAltaContenedor()
            v.findNavController().navigate(action)
        }
        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }



    override fun onStart() {
        super.onStart()

    }

    fun onItemClick ( position : Int ) : Boolean {
        Snackbar.make(v,position.toString(), Snackbar.LENGTH_SHORT).show()
        return true
    }

    fun getData(requestHandler: RequestHandler){
        val gson = Gson()
        requestHandler.getArrayRequest(url,
            { response ->
                for (i in 0 until response.length()) {
                    val contenedor : Contenedor = gson.fromJson(response.getJSONObject(i).toString(), Contenedor::class.java)
                    contenedores.add(contenedor)
                }
                recContenedor.setHasFixedSize(true)
                linearLayoutManager = LinearLayoutManager(context)
                recContenedor.layoutManager = linearLayoutManager

                contenedorListAdapter = ContenedorListAdapter(contenedores) {
                    Toast.makeText(thiscontext, it.id, Toast.LENGTH_SHORT).show()
                };

                recContenedor.adapter = contenedorListAdapter
            },
            { error ->
                Toast.makeText(this@Contenedores.requireContext(), "error" + error, Toast.LENGTH_LONG).show()
            }
            ,null)
    }

}