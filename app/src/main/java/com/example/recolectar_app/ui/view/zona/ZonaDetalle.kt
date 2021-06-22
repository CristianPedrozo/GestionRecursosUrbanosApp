package com.example.recolectar_app.ui.view.zona

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.recolectar_app.databinding.FragmentZonaDetalleBinding
import com.example.recolectar_app.model.DeleteRequestModel
import com.example.recolectar_app.model.zona.ZonaModel
import com.example.recolectar_app.ui.viewModel.zona.ZonaDetalleVM


class ZonaDetalle : Fragment() {
    private val TAG = "ZonaDetalle"
    private var _binding:FragmentZonaDetalleBinding? = null
    private val binding get() = _binding!!
    private val zonaDetalleVM : ZonaDetalleVM by viewModels()
    private lateinit var zona: ZonaModel
    lateinit var thiscontext : Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentZonaDetalleBinding.inflate(inflater,container,false)
        if (container != null) {
            thiscontext = container.context
        };
        val args = arguments?.let { ZonaDetalleArgs.fromBundle(it) }
        zona = args?.zona!!
        binding.textIdDetalleZona.setText(zona.id.split(":")[1])
        binding.textCamionDetalleZona.setText(zona.refVehicle.value.split(":")[1])
        binding.textContenedoresDetalleZona.setText(zona.contenedores.value.size.toString())
        binding.textNombreDetalleZona.setText(zona.nombre.value)


        binding.botonEditarZona.setOnClickListener {
            val action = ZonaDetalleDirections.actionDetalleZonaToUpdateZona(zona)
            binding.root.findNavController().navigate(action)
        }
        binding.botonEliminarZona.setOnClickListener{
            removeZona()
            val action = ZonaDetalleDirections.actionDetalleZonaToListZonas()
            binding.root.findNavController().navigate(action)
        }

        zonaDetalleVM.deleteZonaResult.observe(viewLifecycleOwner, { result ->
            if(result){
                Toast.makeText(thiscontext, "EXITO", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(thiscontext, "FAIL", Toast.LENGTH_SHORT).show()
            }
        })

        
        return binding.root
    }

    private fun removeZona() {
        val zona = ZonaModel(zona.id.split(":")[1])
        val deleteZonaRequest = DeleteRequestModel()
        deleteZonaRequest.addZona(zona)
        zonaDetalleVM.deleteZona(deleteZonaRequest)
    }




    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ZonaDetalle().apply {
                arguments = Bundle().apply {

                }
            }
    }


}