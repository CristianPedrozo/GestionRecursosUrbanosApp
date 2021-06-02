package com.example.recolectar_app.holders

import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.recolectar_app.Objetos.Contenedor.Contenedor
import com.example.recolectar_app.Objetos.Contenedor.FillingLevel
import com.example.recolectar_app.Objetos.Contenedor.Location
import com.example.recolectar_app.Objetos.Contenedor.Temperature
import com.example.recolectar_app.R
import com.example.recolectar_app.administrador.ContenedoresDirections

class ContenedorHolder (v: View) : RecyclerView.ViewHolder(v) {

    private var view: View

    init {
        v.setOnClickListener(){ v: View ->
<<<<<<< HEAD
//            var contenedor= Contenedor("ABDS01")
            val action = ContenedoresDirections.actionContenedoresToContenedorDetalle()
=======
            //var contenedor= Contenedor("ABDS01")
            val id:String = "wastecontainer:1"
            val action = ContenedoresDirections.actionContenedoresToContenedorDetalle(id)
>>>>>>> 314e127a175ffbbb61e74fd8b6ae416743262c72
            v.findNavController().navigate(action)
            //Toast.makeText(itemView.context,"clickeaste ", Toast.LENGTH_LONG).show()
        }
        this.view = v
    }

    fun setId(id: String) {
        val txt: TextView = view.findViewById(R.id.txt_id_item)
        txt.text = id
    }
<<<<<<< HEAD

    fun setTemperature(temperature : Temperature){
        val txt: TextView = view.findViewById(R.id.txt_temp_item)
        txt.text = temperature.value.toString()
    }

    fun setLocation(location : Location){
        val txt : TextView = view.findViewById(R.id.txt_location_item)
        txt.text = location.value.coordinates.toString()
    }

    fun setFillingLevel(fillingLevel: FillingLevel){
        val txt : TextView = view.findViewById(R.id.txt_llenado_item)
        txt.text = fillingLevel.value.toString()
    }


=======
    fun setTipo(tipo: String) {
        val txt: TextView = view.findViewById(R.id.txt_tipo_item)
        txt.text = tipo
    }
    fun setZona(zona: String) {
        val txt: TextView = view.findViewById(R.id.txt_zona_item)
        txt.text = zona
    }
>>>>>>> 314e127a175ffbbb61e74fd8b6ae416743262c72
    fun getCardLayout (): CardView {
        return view.findViewById(R.id.card_package_item_contenedor)
    }

//
//        fun getImageView () : ImageView {
//            return view.findViewById(R.id.img_item)
//        }
}