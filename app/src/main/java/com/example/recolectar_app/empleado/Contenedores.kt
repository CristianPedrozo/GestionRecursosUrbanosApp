package com.example.recolectar_app.empleado

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.recolectar_app.Objetos.Contenedor.Contenedor
import com.example.recolectar_app.R
import com.example.recolectar_app.adapters.ContenedorListAdapter
import com.example.recolectar_app.administrador.Contenedores
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import org.json.JSONObject

class Contenedores : Fragment() {
    //esto tiene q ser variable dependiendo del usuario y su vehiculo
    var url = "http://46.17.108.122:1026/v2/entities/?q=refVehicle==vehicle:1&type=WasteContainer"

    lateinit var v: View
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
        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        var gson = Gson()
        val queue = Volley.newRequestQueue(activity)
        val jsonArrayRequest = JsonArrayRequest(url,
            { response ->
                for (i in 0 until response.length()){
                    val contenedor : Contenedor = gson.fromJson(response.getJSONObject(i).toString(),Contenedor::class.java)
                    contenedores.add(contenedor)
                }
                recContenedores.setHasFixedSize(true)
                linearLayoutManager = LinearLayoutManager(context)
                recContenedores.layoutManager = linearLayoutManager

                contenedorListAdapter = ContenedorListAdapter(contenedores);


                recContenedores.adapter = contenedorListAdapter
            }, {})
        queue.add(jsonArrayRequest)
    }
}