package com.example.recolectar_app.model.contenedor

import android.annotation.SuppressLint
import android.view.View
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.recolectar_app.databinding.FragmentItemContenedorBinding
import com.example.recolectar_app.ui.view.contenedor.ContenedoresListAdminDirections
import com.example.recolectar_app.ui.view.contenedor.ContenedoresListEmpleadoDirections

class ContenedorEmpleadoHolder(v: View) : RecyclerView.ViewHolder(v) {
    val binding = FragmentItemContenedorBinding.bind(v)

    @SuppressLint("SetTextI18n")
    fun bind(contenedorModel: ContenedorModel) {
        binding.txtIdItemContenedor.text = contenedorModel.id.split(":")[1]
        binding.txtZonaItemContenedor.text = contenedorModel.refZona.value.split(":")[1]
        binding.txtTipoItemContendor.text = contenedorModel.wasteType.value
        if (contenedorModel.fillingLevel.value.toString() == "0") {
            binding.txtLlenadoItemContenedor.text = "0%"
        } else {
            binding.txtLlenadoItemContenedor.text =
                contenedorModel.fillingLevel.value.toString().split(".")[1] + "%"
        }
        binding.cardPackageItemContenedor.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(
                ContenedoresListEmpleadoDirections.actionContenedoresListEmpleadoToContenedorDetalleEmpleado(contenedorModel)
            )
        }
    }
}
