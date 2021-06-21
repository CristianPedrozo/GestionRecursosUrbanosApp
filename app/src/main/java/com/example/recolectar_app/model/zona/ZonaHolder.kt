package com.example.recolectar_app.model.zona

import android.view.View
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.recolectar_app.ui.view.zona.List_ZonasDirections
import com.example.recolectar_app.databinding.FragmentItemZonaBinding

class ZonaHolder (v: View) : RecyclerView.ViewHolder(v) {

    val binding = FragmentItemZonaBinding.bind(v)

    fun bind(zonaModel: ZonaModel) = with(binding){
        binding.txtIdItemZona.text = zonaModel.id.split(":")[1]
        binding.txtContenedoresItemZona.text = zonaModel.contenedores.value.size.toString()
        binding.txtNombreItemZona.text = zonaModel.nombre.value
        binding.cardPackageItemZona.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(List_ZonasDirections.actionListZonasToDetalleZona(zonaModel))
        }
    }

}