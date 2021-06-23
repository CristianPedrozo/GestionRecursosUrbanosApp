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
import com.example.recolectar_app.databinding.FragmentUpdateZonaBinding
import com.example.recolectar_app.model.UpdateRequestModel
import com.example.recolectar_app.model.zona.ZonaModel
import com.example.recolectar_app.ui.viewModel.zona.ZonaUpdateVM


class ZonaUpdate : Fragment() {
    private val TAG = "Update Zona"
    private var _binding: FragmentUpdateZonaBinding? = null
    private val binding get() = _binding!!
    private val zonaUpdateVM : ZonaUpdateVM by viewModels()
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
        _binding = FragmentUpdateZonaBinding.inflate(inflater,container,false)
        if (container != null) {
            thiscontext = container.context
        }

        val args = arguments?.let { ZonaDetalleArgs.fromBundle(it) }
        zona = args?.zona!!
        loadZonaData(zona.id)


        binding.botonCancelarEditZona.setOnClickListener {
            val action = ZonaUpdateDirections.actionUpdateZonaToListZonas()
            binding.root.findNavController().navigate(action)
        }
        binding.botonConfirmarEditarZona.setOnClickListener {
            editZona()
            val action = ZonaUpdateDirections.actionUpdateZonaToListZonas()
            binding.root.findNavController().navigate(action)
        }

        zonaUpdateVM.zonaUpdateResult.observe(viewLifecycleOwner, { result ->
            if(result){
                Toast.makeText(thiscontext, "EXITO", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(thiscontext, "FAIL", Toast.LENGTH_SHORT).show()
            }
        })
        return binding.root
    }

    private fun loadZonaData(zonaId : String) = with(binding) {
        zonaUpdateVM.zonaSelectedData.observe(viewLifecycleOwner, { data->
            textIdEditZona.setText(data.id.split(":")[1])
            textContenedoresEditZona.setText(data.contenedores.value.size.toString())
            textCamionEditZona.setText(zona.refVehicle.value.split(":")[1])
            textNombreEditZona.setText(zona.nombre.value)
            textEmpleadoEditZona.setText(zona.empleado?.value)
        })
        val string = "?id=$zonaId"
        zonaUpdateVM.getZonaById(string)
    }

    private fun editZona() = with(binding){
        val zona = ZonaModel(zona.id.split(":")[1])
        zona.setRefVehicleValue(textCamionEditZona.text.toString())
        zona.setNombre(textNombreEditZona.text.toString())
        zona.setEmpleado(textEmpleadoEditZona.text.toString())
        val zonaUpdateRequest = UpdateRequestModel()
        zonaUpdateRequest.addZona(zona)
        zonaUpdateVM.updateZona(zonaUpdateRequest)
    }



    companion object {
        @JvmStatic
        fun newInstance() =
            ZonaUpdate().apply {
                arguments = Bundle().apply {

                }
            }
    }
}