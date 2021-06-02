package com.example.recolectar_app.holders

import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.recolectar_app.Objetos.Contenedor.Contenedor
import com.example.recolectar_app.Objetos.Contenedor.FillingLevel
import com.example.recolectar_app.Objetos.Contenedor.Location
import com.example.recolectar_app.Objetos.Contenedor.Temperature
import com.example.recolectar_app.R
import com.example.recolectar_app.administrador.ContenedoresDirections
import com.example.recolectar_app.administrador.ZonasDirections
import com.example.recolectar_app.zonas.Zona

class ContenedorHolder (v: View) : RecyclerView.ViewHolder(v) {

    private var view: View = v
    private var idContenedor : TextView = v.findViewById(R.id.txt_id_item_contenedor)
    private var type : TextView = v.findViewById(R.id.txt_tipo_item_contenedor)
    private var idZona : TextView = v.findViewById(R.id.txt_zona_item_contenedor)

    fun bind(contendor: Contenedor) = with(view){
        idZona.text = contendor.refZona.value
        idContenedor.text = contendor.id
        type.text = contendor.type
        setOnClickListener {
            Navigation.findNavController(view).navigate(ContenedoresDirections.actionContenedoresToContenedorDetalle(
                idContenedor.text as String
            ))
        }
    }

}