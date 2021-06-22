package com.example.recolectar_app.model.camion

import android.view.View
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.recolectar_app.administrador.CamionesDirections
import com.example.recolectar_app.databinding.FragmentItemCamionBinding

class CamionHolder (v: View) : RecyclerView.ViewHolder(v) {
    val binding = FragmentItemCamionBinding.bind(v)


    fun bind(camionModel: CamionModel){
        binding.txtIdItemCamion.text = camionModel.id.split(":")[1]
        binding.txtPatenteItemCamion.text = camionModel.vehiclePlateIdentifier?.value
        binding.txtEstadoItemCamion.text = camionModel.serviceStatus?.value


        binding.cardPackageItemCamion.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(CamionesDirections.actionCamionesToCamionDetalle(camionModel))
        }
    }

}