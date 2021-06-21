package com.example.recolectar_app.ui.view.zona

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.recolectar_app.RequestHandler
import com.example.recolectar_app.databinding.FragmentZonaDetalleBinding
import com.example.recolectar_app.model.zona.ZonaModel
//import com.example.recolectar_app.zonas.DeleteZonaRequest


class Detalle_Zona : Fragment() {
    private val TAG = "ZonaDetalle"
    private var url = "http://46.17.108.122:1026/v2/op/update"
    private var _binding:FragmentZonaDetalleBinding? = null
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
        _binding = FragmentZonaDetalleBinding.inflate(inflater,container,false)
        if (container != null) {
            thiscontext = container.context
        };
        val requestHandler = RequestHandler.getInstance(thiscontext)
        val args = arguments?.let { Detalle_ZonaArgs.fromBundle(it) }
        zona = args?.zona!!
        binding.textIdDetalleZona.setText(zona.id!!.split(":")[1])
        binding.textCamionDetalleZona.setText(zona.refVehicle?.value!!.split(":")[1])
        binding.textContenedoresDetalleZona.setText(zona.contenedores.value.size.toString())

        binding.botonEditarZona.setOnClickListener {
            val action = Detalle_ZonaDirections.actionDetalleZonaToUpdateZona(zona)
            binding.root.findNavController().navigate(action)
        }
        binding.botonEliminarZona.setOnClickListener{
//            removeZona(requestHandler)
            val action = Detalle_ZonaDirections.actionDetalleZonaToListZonas()
            binding.root.findNavController().navigate(action)
        }
        return binding.root
    }

//    private fun removeZona(requestHandler: RequestHandler) {
//        val gson = Gson()
//        val zona = ZonaModel(zona.id.split(":")[1])
//        val deleteObject = DeleteZonaRequest()
//        deleteObject.addEntitie(zona)
//        val jsonDeleteObject = gson.toJson(deleteObject)
//        val jsonObject = JSONObject(jsonDeleteObject)
//        Toast.makeText(thiscontext, "$jsonObject", Toast.LENGTH_SHORT).show()
//        requestHandler.deleteRequest(url,jsonObject,{},{})
//    }




    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Detalle_Zona().apply {
                arguments = Bundle().apply {

                }
            }
    }


}