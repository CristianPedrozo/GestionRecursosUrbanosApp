package com.example.recolectar_app.holders

import android.annotation.SuppressLint
import android.view.View
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.recolectar_app.administrador.ContenedoresDirections
import com.example.recolectar_app.contenedores.Contenedor
import com.example.recolectar_app.databinding.FragmentItemContenedorBinding

class ContenedorHolder (v: View) : RecyclerView.ViewHolder(v) {
    val binding = FragmentItemContenedorBinding.bind(v)

    @SuppressLint("SetTextI18n")
    fun bind(contenedor: Contenedor){
        binding.txtIdItemContenedor.text = "Contenedor n°: "+ contenedor.id!!.split(":")[1]
        binding.txtTipoItemContenedor.text = "Tipo: "+contenedor.wasteType.value
        binding.txtLlenadoItemContenedor.text = "Llenado: "+contenedor.fillingLevel.value.toString().split(".")[1]+"%"

        binding.cardPackageItemContenedor.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(ContenedoresDirections.actionContenedoresToContenedorDetalle(
                contenedor
            ))
        }
    }


}