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
import com.example.recolectar_app.adapters.CamionListAdapter
import com.example.recolectar_app.entities.Camion
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [fragment_administrador_camiones.newInstance] factory method to
 * create an instance of this fragment.
 */
class Camiones : Fragment() {

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

    }

    fun onItemClick(position: Int): Boolean {
        Snackbar.make(v, position.toString(), Snackbar.LENGTH_SHORT).show()
        return true
    }
}

