package com.example.recolectar_app.administrador
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recolectar_app.R
import com.example.recolectar_app.RequestHandler
import com.example.recolectar_app.adapters.CamionListAdapter
import com.example.recolectar_app.camiones.Camion
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson

class Camiones : Fragment() {
    var url = "http://46.17.108.122:1026/v2/entities/?type=Vehicle"
    lateinit var thiscontext : Context
    lateinit var v: View
    //Para botón flotante Agregar Camión
    lateinit var botton_agregar: FloatingActionButton
    //
    lateinit var recCamiones: RecyclerView
    var camiones: MutableList<Camion> = ArrayList<Camion>()
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var camionListAdapter: CamionListAdapter

    companion object {
        fun newInstance() = Camiones()
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
        v = inflater.inflate(R.layout.fragment_list_camiones, container, false)
        recCamiones = v.findViewById(R.id.rec_camiones)
        //Para el botón flotante agregar Contenedor
        botton_agregar = v.findViewById(R.id.boton_agregar)
        botton_agregar.setOnClickListener(){

            val action = CamionesDirections.actionCamionesToAltaCamion()
            v.findNavController().navigate(action)
        }
        return v
    }

    override fun onStart() {
        super.onStart()
/*
        for (i in 1..5) {
            camiones.add(Camion("BNG0989", "Ecologico"))
            camiones.add(Camion("BNG0989", "Ecologico"))
            camiones.add(Camion("BNG0989", "Ecologico"))
            camiones.add(Camion("BNG0989", "Ecologico"))

        }

        //Configuración Obligatoria
        recCamiones.setHasFixedSize(true)
        linearLayoutManager = LinearLayoutManager(context)
        recCamiones.layoutManager = linearLayoutManager


        camionListAdapter = CamionListAdapter(camiones);

        /*
        camionListAdapter = CamionListAdapter(camiones) { x ->
            onItemClick(x)
        }
         */

        recCamiones.adapter = camionListAdapter


 */
    }

    fun onItemClick(position: Int): Boolean {
        Snackbar.make(v, position.toString(), Snackbar.LENGTH_SHORT).show()
        return true
    }

    fun getData(requestHandler: RequestHandler){
        val gson = Gson()
        requestHandler.getArrayRequest(url,
            { response ->
                for (i in 0 until response.length()) {
                    val camion : Camion = gson.fromJson(response.getJSONObject(i).toString(), Camion::class.java)
                    camiones.add(camion)
                }
                recCamiones.setHasFixedSize(true)
                linearLayoutManager = LinearLayoutManager(context)
                recCamiones.layoutManager = linearLayoutManager

                camionListAdapter = CamionListAdapter(camiones){
                    Toast.makeText(thiscontext, it.id, Toast.LENGTH_SHORT).show()
                }
                recCamiones.adapter = camionListAdapter
            },
            { error ->
                Toast.makeText(this@Camiones.requireContext(), "error" + error, Toast.LENGTH_LONG).show()
            }
            ,null)
    }
}

