package com.example.recolectar_app.zonas

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import com.example.recolectar_app.RequestHandler
import com.example.recolectar_app.databinding.FragmentZonaDetalleBinding
import com.google.gson.Gson
import org.json.JSONObject


class ZonaDetalle : Fragment() {
    private val TAG = "ZonaDetalle"
    private var url = "http://46.17.108.122:1026/v2/op/update"
    private var _binding:FragmentZonaDetalleBinding? = null
    private val binding get() = _binding!!
    private lateinit var zona: Zona
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
        val args = arguments?.let { ZonaDetalleArgs.fromBundle(it) }
        zona = args?.zona!!
        binding.textIdDetalleZona.setText(zona.id!!.split(":")[1])
        binding.textCamionDetalleZona.setText(zona.refVehicle?.value!!.split(":")[1])
        binding.textContenedoresDetalleZona.setText(zona.contenedores.value.size.toString())

        binding.botonEditarZona.setOnClickListener {
            val action = ZonaDetalleDirections.actionZonaDetalleToUpdateZona(zona)
            binding.root.findNavController().navigate(action)
        }
        binding.botonEliminarZona.setOnClickListener{
            removeZona(requestHandler)
            val action = ZonaDetalleDirections.actionZonaDetalleToZonas()
            binding.root.findNavController().navigate(action)
        }
        return binding.root
    }

    private fun removeZona(requestHandler: RequestHandler) {
        val gson = Gson()
        val zona = Zona(zona.id.split(":")[1])
        val deleteObject = DeleteZonaRequest()
        deleteObject.addEntitie(zona)
        val jsonDeleteObject = gson.toJson(deleteObject)
        val jsonObject = JSONObject(jsonDeleteObject)
        Toast.makeText(thiscontext, "$jsonObject", Toast.LENGTH_SHORT).show()
        requestHandler.deleteRequest(url,jsonObject,{},{})
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