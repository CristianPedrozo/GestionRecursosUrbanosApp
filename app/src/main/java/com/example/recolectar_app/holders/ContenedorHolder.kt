package com.example.recolectar_app.holders

import android.annotation.SuppressLint
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
    private var tipoContenedor : TextView = v.findViewById(R.id.txt_tipo_item_contenedor)
    private var llenadoContenedor : TextView = v.findViewById(R.id.txt_llenado_item_contenedor)

    @SuppressLint("SetTextI18n")
    fun bind(contenedor: Contenedor) = with(view){
        idContenedor.text = "Contenedor n°: "+ contenedor.id!!.split(":")[1]
        tipoContenedor.text = "Tipo: "+contenedor.wasteType.value
        llenadoContenedor.text = "Llenado: "+contenedor.fillingLevel.value.toString().split(".")[1]+"%"

        setOnClickListener {
            Navigation.findNavController(view).navigate(ContenedoresDirections.actionContenedoresToContenedorDetalle(
                contenedor
            ))
        }
    }


}