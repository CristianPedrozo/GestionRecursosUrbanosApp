package com.example.recolectar_app.contenedores

import DeleteContenedorRequest
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import com.example.recolectar_app.RequestHandler
import com.example.recolectar_app.databinding.FragmentContenedorDetalleBinding
import com.google.gson.Gson
import org.json.JSONObject

class ContenedorDetalle : Fragment() {
    private val TAG = "ContenedorDetalle"
    private var url = "http://46.17.108.122:1026/v2/op/update"
    private var _binding: FragmentContenedorDetalleBinding? = null
    private val binding get() = _binding!!
    private lateinit var contenedor: Contenedor
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
        _binding = FragmentContenedorDetalleBinding.inflate(layoutInflater,container,false)
        if (container != null) {
            thiscontext = container.context
        };
        val requestHandler = RequestHandler.getInstance(thiscontext)
        val args = arguments?.let { ContenedorDetalleArgs.fromBundle(it) }
        contenedor = args?.contenedor!!

        binding.textId.setText(contenedor.id!!.split(":")[1])
        binding.textTipo.setText(contenedor.wasteType.value)
        binding.textLatitud.setText(contenedor.location.value.coordinates[0].toString())
        binding.textLongitud.setText(contenedor.location.value.coordinates[1].toString())
        binding.textEstado.setText(contenedor.status.value)
        binding.textRuta.setText(contenedor.refRuta?.value)
        binding.textCamion.setText(contenedor.refVehicle?.value)
        binding.textTemperatura.setText(contenedor.temperature?.value.toString())
        binding.textZona.setText(contenedor.refZona?.value)
        binding.textProximaVisita.setText(contenedor.nextActuationDeadline?.value)
        binding.textUltimaVisita.setText(contenedor.dateLastEmptying?.value)
        binding.textLlenado.setText(contenedor.fillingLevel.value.toString())

        binding.botonEditarContenedor.setOnClickListener(){
            val action = ContenedorDetalleDirections.actionContenedorDetalleToUpdateContenedor(contenedor)
            binding.root.findNavController().navigate(action)
        }

        binding.botonRemoverContenedor.setOnClickListener(){
            removeContenedor(requestHandler)
            val action = ContenedorDetalleDirections.actionContenedorDetalleToContenedores()
            binding.root.findNavController().navigate(action)
        }

        return binding.root
    }

    private fun removeContenedor(requestHandler: RequestHandler) {
        val gson = Gson()
        val contenedor = Contenedor(contenedor.id.split(":")[1])
        val deleteObject = DeleteContenedorRequest()
        deleteObject.addEntitie(contenedor)
        val jsonDeleteObject = gson.toJson(deleteObject)
        val jsonObject = JSONObject(jsonDeleteObject)
        Toast.makeText(thiscontext, "$jsonObject", Toast.LENGTH_SHORT).show()
        requestHandler.deleteRequest(url,jsonObject,{},{})
    }


    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ContenedorDetalle().apply {
                arguments = Bundle().apply {

                }
            }
    }
}