package com.example.recolectar_app.ui.view.zona

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
//import com.example.recolectar_app.PatchRequestObject
import com.example.recolectar_app.RequestHandler
import com.example.recolectar_app.databinding.FragmentUpdateZonaBinding
import com.example.recolectar_app.model.zona.ZonaModel


class ZonaUpdate : Fragment() {
    private val TAG = "Update Zona"
    private var url = "http://46.17.108.122:1026/v2/op/update"
    private var _binding: FragmentUpdateZonaBinding? = null
    private val binding get() = _binding!!
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
        val requestHandler = RequestHandler.getInstance(thiscontext)
        val args = arguments?.let { ZonaDetalleArgs.fromBundle(it) }
        zona = args?.zona!!

        binding.textIdEditZona.hint = "Zona n°: ${zona.id.split(":")[1]}"
        binding.textContenedoresEditZona.hint = "Contenedores: ${zona.contenedores.value.size}"
        binding.textCamionEditZona.setText(zona.refVehicle?.value)
        binding.botonCancelarEditZona.setOnClickListener {
            val action = ZonaUpdateDirections.actionUpdateZonaToListZonas()
            binding.root.findNavController().navigate(action)
        }
        binding.botonConfirmarEditarZona.setOnClickListener {
//            editZona(requestHandler)
            val action = ZonaUpdateDirections.actionUpdateZonaToListZonas()
            binding.root.findNavController().navigate(action)
        }
        return binding.root
    }

//
//    private fun editZona(requestHandler: RequestHandler) {
//        val gson = Gson()
//        val zona = ZonaModel(zona.id!!.split(":")[1])
//        zona.setRefVehicleValue(binding.textCamionEditZona.text.toString())
//        val patchObject = PatchRequestObject()
//        patchObject.addEntitie(zona)
//        val jsonPatchObject = gson.toJson(patchObject)
//        val jsonObject = JSONObject(jsonPatchObject)
//        requestHandler.patchRequest(url,jsonObject,{},{})
//    }

    companion object {
        @JvmStatic
        fun newInstance() =
            ZonaUpdate().apply {
                arguments = Bundle().apply {

                }
            }
    }
}