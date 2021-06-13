package com.example.recolectar_app.holders

import android.annotation.SuppressLint
import android.view.View
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.recolectar_app.R
import com.example.recolectar_app.administrador.ZonasDirections
import com.example.recolectar_app.zonas.Zona

class ZonaHolder (v: View) : RecyclerView.ViewHolder(v) {

    private var view: View = v
    private var idZona : TextView = v.findViewById(R.id.txt_id_item_zona)
    private var contenedoresZona : TextView = v.findViewById(R.id.txt_contenedores_item_zona)


    @SuppressLint("SetTextI18n")
    fun bind(zona: Zona) = with(view){
        idZona.text = "Zona n°: "+ (zona.id?.split(":")?.get(1) ?: null)
        contenedoresZona.text = "Contenedores: "+zona.contenedores!!.value
        setOnClickListener {
            Navigation.findNavController(view).navigate(ZonasDirections.actionZonasToZonaDetalle(zona))
        }
    }

}