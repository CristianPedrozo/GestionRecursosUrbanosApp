package com.example.recolectar_app.holders

import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.recolectar_app.Objetos.Contenedor.Contenedor
import com.example.recolectar_app.R
import com.example.recolectar_app.administrador.ContenedoresDirections

class ContenedorHolder (v: View) : RecyclerView.ViewHolder(v) {

    private var view: View

    init {
        v.setOnClickListener(){ v: View ->
            //var contenedor= Contenedor("ABDS01")
            val action = ContenedoresDirections.actionContenedoresToContenedorDetalle()
            v.findNavController().navigate(action)
            //Toast.makeText(itemView.context,"clickeaste ", Toast.LENGTH_LONG).show()
        }
        this.view = v
    }

    fun setId(id: String) {
        val txt: TextView = view.findViewById(R.id.txt_id_item)
        txt.text = id
    }

    fun getCardLayout (): CardView {
        return view.findViewById(R.id.card_package_item_contenedor)
    }

//
//        fun getImageView () : ImageView {
//            return view.findViewById(R.id.img_item)
//        }
}