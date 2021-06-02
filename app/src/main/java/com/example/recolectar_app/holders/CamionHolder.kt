package com.example.recolectar_app.holders

import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.recolectar_app.R
import com.example.recolectar_app.administrador.CamionesDirections
import com.example.recolectar_app.entities.Camion
import kotlinx.coroutines.selects.select

class CamionHolder (v: View) : RecyclerView.ViewHolder(v) {

    private var view: View

    init {
        v.setOnClickListener(){ v: View ->
            var camion= Camion("ABDS01","Organico")
            val action = CamionesDirections.actionCamionesToCamionDetalle(camion)
            v.findNavController().navigate(action)
        //Toast.makeText(itemView.context,"clickeaste ", Toast.LENGTH_LONG).show()
        }
        this.view = v
    }

    fun setName(name: String) {
        val txt: TextView = view.findViewById(R.id.txt_patente_item)
        txt.text = name
    }

    fun getCardLayout (): CardView {
        return view.findViewById(R.id.card_package_item)
    }

//
//        fun getImageView () : ImageView {
//            return view.findViewById(R.id.img_item)
//        }
}