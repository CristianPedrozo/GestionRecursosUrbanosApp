package com.example.recolectar_app.holders

import android.annotation.SuppressLint
import android.view.View
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.recolectar_app.R
import com.example.recolectar_app.administrador.CamionesDirections
import com.example.recolectar_app.camiones.Camion

class CamionHolder (v: View) : RecyclerView.ViewHolder(v) {

    private var view: View =v
    private var idCamion : TextView = v.findViewById(R.id.txt_id_item_camion)
    private var type : TextView = v.findViewById(R.id.txt_tipo_item_camion)
    private var status : TextView = v.findViewById(R.id.txt_estado_item_camion)

    @SuppressLint("SetTextI18n")
    fun bind(camion: Camion) = with(view){
        idCamion.text = "Camion n°: "+camion.id.split(":")[1]
        type.text = "Tipo: "+ camion.vehicleType?.value.toString()
        status.text = "Estado: "+camion.serviceStatus?.value

        setOnClickListener {
            Navigation.findNavController(view).navigate(CamionesDirections.actionCamionesToCamionDetalle(idCamion.text as String, camion.vehiclePlateIdentifier?.value.toString(), camion.cargoWeight?.value.toString(), status.text as String))
        }
    }

}