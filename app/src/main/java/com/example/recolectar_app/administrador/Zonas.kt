package com.example.recolectar_app.administrador

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recolectar_app.R
import com.example.recolectar_app.adapters.ZonaListAdapter
import com.example.recolectar_app.zonas.Zona
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar


class Zonas : Fragment() {
    lateinit var v: View
    lateinit var botton_agregar: FloatingActionButton
    lateinit var recZonas : RecyclerView
    var zonas : MutableList<Zona> = ArrayList()
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var zonaListAdapter: ZonaListAdapter

    companion object {
        fun newInstance() = Zonas()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v =  inflater.inflate(R.layout.fragment_list_zonas, container, false)
        recZonas = v.findViewById(R.id.rec_zonas)
        botton_agregar = v.findViewById(R.id.btn_agregar_zona)
        botton_agregar.setOnClickListener(){
            val action = ZonasDirections.actionZonasToAltaZona()
            v.findNavController().navigate(action)
        }
        //
        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }



    override fun onStart() {
        super.onStart()

        for (i in 1..5) {

            zonas.add(Zona("1",Zona.RefVehicle("Relationship","vehicle:1"),"Zona"))

        }

        recZonas.setHasFixedSize(true)
        linearLayoutManager = LinearLayoutManager(context)
        recZonas.layoutManager = linearLayoutManager

        zonaListAdapter = ZonaListAdapter(zonas);


        recZonas.adapter = zonaListAdapter

    }

    fun onItemClick ( position : Int ) : Boolean {
        Snackbar.make(v,position.toString(), Snackbar.LENGTH_SHORT).show()
        return true
    }
}