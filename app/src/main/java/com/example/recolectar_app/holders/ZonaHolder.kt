package com.example.recolectar_app.holders

import android.view.View
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.recolectar_app.R
import com.example.recolectar_app.administrador.ZonasDirections
import com.example.recolectar_app.zonas.Zona

class ZonaHolder (v: View) : RecyclerView.ViewHolder(v) {



//    init {
//        v.setOnClickListener(){ v: View ->
//            val txt: TextView = v.findViewById(R.id.txt_id_itemZona)
//            val id = txt.text.toString()
//            val bundle = bundleOf("id" to id)
//            v.findNavController().navigate(R.id.action_zonas_to_zonaDetalle,bundle)
//        }
//        this.view = v
//    }


    private var view: View = v
    private var txt : TextView = v.findViewById(R.id.txt_id_itemZona)


    fun bind(zona: Zona, listener: (Zona) -> Unit) = with(view){
        txt.text = zona.id
        val stringId = zona.id
        setOnClickListener {
            listener(zona)
            Navigation.findNavController(view).navigate(ZonasDirections.actionZonasToZonaDetalle(stringId))
        }
    }


}