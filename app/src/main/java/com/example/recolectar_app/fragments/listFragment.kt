package com.example.recolectar_app.fragments
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recolectar_app.R
import com.example.recolectar_app.adapters.CamionListAdapter
import com.example.recolectar_app.entities.Camion
import com.google.android.material.snackbar.Snackbar

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class listFragment : Fragment() {

    lateinit var v: View

    lateinit var recCamiones : RecyclerView

    var camiones : MutableList<Camion> = ArrayList<Camion>()

    private lateinit var linearLayoutManager: LinearLayoutManager

    private lateinit var camionListAdapter: CamionListAdapter

    companion object {
        fun newInstance() = listFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v =  inflater.inflate(R.layout.list_fragment, container, false)
        recCamiones = v.findViewById(R.id.rec_camiones)
        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()

        for (i in 1..5) {
            camiones.add(Camion("BNG0989","Ecologico"))
            camiones.add(Camion("BNG0989","Ecologico"))
            camiones.add(Camion("BNG0989","Ecologico"))
            camiones.add(Camion("BNG0989","Ecologico"))

        }

        //Configuración Obligatoria
        recCamiones.setHasFixedSize(true)
        linearLayoutManager = LinearLayoutManager(context)
        recCamiones.layoutManager = linearLayoutManager


        camionListAdapter = CamionListAdapter(camiones);

        /*
        contactoListAdapter = ContactoListAdapter(contactos) { x ->
            onItemClick(x)
        }*/

        recCamiones.adapter = camionListAdapter

    }

    fun onItemClick ( position : Int ) : Boolean {
        Snackbar.make(v,position.toString(), Snackbar.LENGTH_SHORT).show()
        return true
    }

}