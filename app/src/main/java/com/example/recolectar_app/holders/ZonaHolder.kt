package com.example.recolectar_app.holders

import android.view.View
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.recolectar_app.R
import com.example.recolectar_app.administrador.ZonasDirections
import com.example.recolectar_app.zonas.Zona

class ZonaHolder (v: View) : RecyclerView.ViewHolder(v) {

    private var view: View = v
    private var idZona : TextView = v.findViewById(R.id.txt_id_itemZona)
    private var ref : TextView = v.findViewById(R.id.txt_refVehicle_itemZona)


    fun bind(zona: Zona) = with(view){
        idZona.text = zona.id.split(":")[1]
        ref.text = zona.refVehicle!!.value.split(":")[1]
        setOnClickListener {
            Navigation.findNavController(view).navigate(ZonasDirections.actionZonasToZonaDetalle(idZona.text as String,ref.text as String))
        }
    }

}