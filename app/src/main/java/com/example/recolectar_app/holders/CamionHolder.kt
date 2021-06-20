package com.example.recolectar_app.holders

import android.view.View
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.recolectar_app.administrador.CamionesDirections
import com.example.recolectar_app.camiones.Camion
import com.example.recolectar_app.databinding.FragmentItemCamionBinding

class CamionHolder (v: View) : RecyclerView.ViewHolder(v) {
    val binding = FragmentItemCamionBinding.bind(v)


    fun bind(camion: Camion){
        binding.txtIdItemCamion.text = camion.id.split(":")[1]
        binding.txtPatenteItemCamion.text = camion.vehiclePlateIdentifier?.value
        binding.txtEstadoItemCamion.text = camion.serviceStatus?.value


        binding.cardPackageItemCamion.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(CamionesDirections.actionCamionesToCamionDetalle(camion))
        }
    }

}