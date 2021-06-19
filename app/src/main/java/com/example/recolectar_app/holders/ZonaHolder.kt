package com.example.recolectar_app.holders

import android.annotation.SuppressLint
import android.view.View
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.recolectar_app.administrador.ZonasDirections
import com.example.recolectar_app.databinding.FragmentItemZonaBinding
import com.example.recolectar_app.zonas.Zona

class ZonaHolder (v: View) : RecyclerView.ViewHolder(v) {

    val binding = FragmentItemZonaBinding.bind(v)


    @SuppressLint("SetTextI18n")
    fun bind(zona: Zona) = with(binding){
        binding.txtIdItemZona.text = "Zona n°: "+ zona.id?.split(":")?.get(1)
        binding.txtContenedoresItemZona.text = "Contenedores: "+zona.contenedores.value.size
        binding.cardPackageItemZona.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(ZonasDirections.actionZonasToZonaDetalle(zona))
        }
    }

}