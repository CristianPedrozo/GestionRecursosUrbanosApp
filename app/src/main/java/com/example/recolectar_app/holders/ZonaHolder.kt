package com.example.recolectar_app.holders

import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.recolectar_app.R
import com.example.recolectar_app.administrador.ZonasDirections

class ZonaHolder (v: View) : RecyclerView.ViewHolder(v) {

    private var view: View

    init {
        v.setOnClickListener(){ v: View ->
            val action = ZonasDirections.actionZonasToZonaDetalle()
            v.findNavController().navigate(action)
        }
        this.view = v
    }

    fun setId(id: String) {
        val txt: TextView = view.findViewById(R.id.txt_id_itemZona)
        txt.text = id
    }


    fun getCardLayout (): CardView {
        return view.findViewById(R.id.card_package_item_zona)
    }


}