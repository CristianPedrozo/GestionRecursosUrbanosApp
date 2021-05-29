package com.example.recolectar_app.administrador

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
import com.example.recolectar_app.adapters.CamionListAdapter
import com.example.recolectar_app.adapters.ContenedorListAdapter
import com.example.recolectar_app.entities.Camion
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [fragment_administrador_contenedores.newInstance] factory method to
 * create an instance of this fragment.
 */
class Contenedores : Fragment() {

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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
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
/*
    override fun onStart() {
        super.onStart()

        for (i in 1..5) {
            contenedores.add(Contenedor("Cont-1AD"))
            contenedores.add(Contenedor("Cont-1AD"))
            contenedores.add(Contenedor("Cont-1AD"))
            contenedores.add(Contenedor("Cont-1AD"))

        }

        //Configuración Obligatoria
        recContenedores.setHasFixedSize(true)
        linearLayoutManager = LinearLayoutManager(context)
        recContenedores.layoutManager = linearLayoutManager

        contenedorListAdapter = ContenedorListAdapter(contenedores);
        /*
        contactoListAdapter = ContactoListAdapter(contactos) { x ->
            onItemClick(x)
        }*/

        recContenedores.adapter = contenedorListAdapter

    }

 */

    fun onItemClick ( position : Int ) : Boolean {
        Snackbar.make(v,position.toString(), Snackbar.LENGTH_SHORT).show()
        return true
    }

}