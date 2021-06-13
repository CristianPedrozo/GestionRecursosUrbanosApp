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
    //private var type : TextView = v.findViewById(R.id.txt_tipo_item_camion)
    private var status : TextView = v.findViewById(R.id.txt_estado_item_camion)
    private var patente : TextView = v.findViewById(R.id.txt_tipo_item_camion)

    fun bind(camion: Camion, listener: (Camion) -> Unit) = with(view){
        idCamion.text = camion.id.split(":")[1]
        patente.text = camion.vehiclePlateIdentifier?.value
        status.text = camion.serviceStatus?.value

        setOnClickListener {
            listener(camion)
            Navigation.findNavController(view).navigate(CamionesDirections.actionCamionesToCamionDetalle(idCamion.text as String, camion.vehiclePlateIdentifier?.value.toString(), camion.cargoWeight?.value.toString(), status.text as String))
        }
    }

}