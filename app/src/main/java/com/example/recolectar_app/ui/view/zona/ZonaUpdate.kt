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
//import com.example.recolectar_app.PatchRequestObject
import com.example.recolectar_app.RequestHandler
import com.example.recolectar_app.databinding.FragmentUpdateZonaBinding
import com.example.recolectar_app.model.UpdateRequestModel
import com.example.recolectar_app.model.zona.UpdateZonaRequestModel
import com.example.recolectar_app.model.zona.ZonaModel
import com.example.recolectar_app.ui.viewModel.zona.ZonaUpdateVM
import com.google.gson.Gson


class ZonaUpdate : Fragment() {
    private val TAG = "Update Zona"
    private var url = "http://46.17.108.122:1026/v2/op/update"
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

        binding.textIdEditZona.hint = zona.id.split(":")[1]
        binding.textContenedoresEditZona.hint = zona.contenedores.value.size.toString()
        binding.textCamionEditZona.setText(zona.refVehicle.value.split(":")[1])
        binding.textNombreEditZona.setText(zona.nombre.value)
        binding.botonCancelarEditZona.setOnClickListener {
            val action = ZonaUpdateDirections.actionUpdateZonaToListZonas()
            binding.root.findNavController().navigate(action)
        }
        binding.botonConfirmarEditarZona.setOnClickListener {
            editZona()
            val action = ZonaUpdateDirections.actionUpdateZonaToListZonas()
            binding.root.findNavController().navigate(action)
        }

        zonaUpdateVM.zonaUpdateData.observe(viewLifecycleOwner, { result ->
            if(result){
                Toast.makeText(thiscontext, "EXITO", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(thiscontext, "FAIL", Toast.LENGTH_SHORT).show()
            }
        })
        return binding.root
    }


    private fun editZona(){
        val zona = ZonaModel(zona.id.split(":")[1])
        zona.setRefVehicleValue(binding.textCamionEditZona.text.toString())
        zona.setNombre(binding.textNombreEditZona.text.toString())
        val zonaUpdateRequest = UpdateRequestModel()
        zonaUpdateRequest.addZona(zona)
        val gson = Gson()
        val asd = gson.toJson(zonaUpdateRequest)
        Toast.makeText(thiscontext, asd, Toast.LENGTH_LONG).show()
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