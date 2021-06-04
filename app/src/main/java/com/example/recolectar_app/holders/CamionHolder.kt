package com.example.recolectar_app.holders

import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.recolectar_app.R
import com.example.recolectar_app.administrador.CamionesDirections
import com.example.recolectar_app.camiones.Camion

class CamionHolder (v: View) : RecyclerView.ViewHolder(v) {

    private var view: View =v
    private var idCamion : TextView = v.findViewById(R.id.txt_id_item_camion)
/*
    init {
        v.setOnClickListener(){ v: View ->
            //var camion= Camion("ABDS01","Organico")
            val action = CamionesDirections.actionCamionesToCamionDetalle(camion)
            v.findNavController().navigate(action)
        //Toast.makeText(itemView.context,"clickeaste ", Toast.LENGTH_LONG).show()
        }
        this.view = v
    }

 */
    fun bind(camion: Camion, listener: (Camion) -> Unit) = with(view){
        idCamion.text = camion.id.split(":")[1]

        setOnClickListener {
            listener(camion)
            Navigation.findNavController(view).navigate(CamionesDirections.actionCamionesToCamionDetalle(idCamion.text as String))
        }
    }
    fun setId(id: String) {
        val txt: TextView = view.findViewById(R.id.txt_id_item_camion)
        txt.text = id
    }

    fun setTipo(tipo: String) {
        val txt: TextView = view.findViewById(R.id.txt_tipo_item_camion)
        txt.text = tipo
    }
    fun setZona(zona: String) {
        val txt: TextView = view.findViewById(R.id.txt_zona_item_camion)
        txt.text = zona
    }

//
//        fun getImageView () : ImageView {
//            return view.findViewById(R.id.img_item)
//        }
}