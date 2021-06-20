package com.example.recolectar_app.holders

import android.view.View
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.recolectar_app.R
import com.example.recolectar_app.administrador.ContenedoresDirections
import com.example.recolectar_app.contenedores.Contenedor

class ContenedorHolder (v: View) : RecyclerView.ViewHolder(v) {
    private var view: View = v
    private var idContenedor : TextView = v.findViewById(R.id.txt_id_item_contenedor)

    fun bind(contenedor: Contenedor, listener: (Contenedor) -> Unit) = with(view){
        idContenedor.text = contenedor.id.split(":")[1]

        setOnClickListener {
            listener(contenedor)
            Navigation.findNavController(view).navigate(ContenedoresDirections.actionContenedoresToContenedorDetalle(idContenedor.text as String, contenedor.status.value))
        }
    }


}