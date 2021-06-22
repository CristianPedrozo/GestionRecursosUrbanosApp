package com.example.recolectar_app.ui.view.contenedor

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import com.example.recolectar_app.PatchContenedorObject
import com.example.recolectar_app.RequestHandler
import com.example.recolectar_app.databinding.FragmentUpdateContenedorBinding
import com.example.recolectar_app.model.contenedor.ContenedorModel
import com.google.gson.Gson
import org.json.JSONObject


class ContenedorUpdate : Fragment() {
    private val TAG = "Update Contenedor"
    private var url = "http://46.17.108.122:1026/v2/op/update"
    private var _binding: FragmentUpdateContenedorBinding? = null
    private val binding get() = _binding!!
    private lateinit var contenedorModel: ContenedorModel
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
        _binding = FragmentUpdateContenedorBinding.inflate(layoutInflater,container,false)

        if (container != null) {
            thiscontext = container.context
        };
        val requestHandler = RequestHandler.getInstance(thiscontext)
        val args = arguments?.let { ContenedorDetalleArgs.fromBundle(it) }
        contenedorModel = args?.contenedor!!
        binding.edittextContenedorId.setText(contenedorModel.id!!.split(":")[1])
        binding.edittextContenedorCamion.setText(contenedorModel.refVehicle?.value)
        binding.edittextContenedorEstado.setText(contenedorModel.status.value)
        binding.edittextContenedorLatitud.setText(contenedorModel.location.value.coordinates[0].toString())
        binding.edittextContenedorLongitud.setText(contenedorModel.location.value.coordinates[1].toString())
//        binding.edittextContenedorRuta.setText(contenedor.refRuta?.value)
        binding.edittextContenedorZona.setText(contenedorModel.refZona?.value)
        binding.edittextContenedorTemperatura.setText(contenedorModel.temperature?.value.toString())
        binding.edittextContenedorLlenado.setText(contenedorModel.fillingLevel.value.toString())
//        binding.edittextContenedorUltimaVisita.setText(contenedor.dateLastEmptying?.value)
//        binding.edittextContenedorProximaVisita.setText(contenedor.nextActuationDeadline?.value)
        binding.botonConfirmarEditarContenedor.setOnClickListener(){
            editContenedor(requestHandler)
            val action = ContenedorUpdateDirections.actionUpdateContenedorToContenedores()
            binding.root.findNavController().navigate(action)
        }
        return binding.root
    }


    private fun editContenedor(requestHandler: RequestHandler) {
        val gson = Gson()
        val contEdit = ContenedorModel(contenedorModel.id!!.split(":")[1])
        if(binding.edittextContenedorEstado.text.toString() != contenedorModel.status.value && binding.edittextContenedorEstado.text.toString() != "")
            contEdit.setStatus(binding.edittextContenedorEstado.text.toString())
//        if(tiet_camion.text.toString() != contenedor.refVehicle?.value?.split(":")?.get(1) ?: "")
//            contEdit.setRefVehicle(tiet_camion.text.toString())
        if(binding.edittextContenedorLatitud.text.toString() != "" &&
            binding.edittextContenedorLatitud.text.toString() != ""){
            val latlong : MutableList<Double> = arrayListOf()
            latlong.add(binding.edittextContenedorLatitud.text.toString().toDouble())
            latlong.add(binding.edittextContenedorLongitud.text.toString().toDouble())
            contEdit.setLocation(latlong)
        }
        if(binding.edittextContenedorLlenado.text.toString() != contenedorModel.fillingLevel.value.toString() && binding.edittextContenedorLlenado.text.toString() != "")
            contEdit.setFillingLevel(binding.edittextContenedorLlenado.text.toString().toDouble())
//        if(tiet_proxima_visita.text.toString() != contenedor.nextActuationDeadline?.value ?: "")
//            contEdit.setStatus(tiet_proxima_visita.text.toString())
//        if(tiet_ultima_visita.text.toString() != contenedor.dateLastEmptying?.value ?: "")
//            contEdit.setStatus(tiet_ultima_visita.text.toString())
        if(binding.edittextContenedorTemperatura.text.toString() != contenedorModel.temperature?.value.toString() && binding.edittextContenedorTemperatura.text.toString() != "")
            contEdit.setTemperature(binding.edittextContenedorTemperatura.text.toString().toDouble())
//        if(tiet_zona.text.toString() != contenedor.refZona?.value?.split(":")?.get(1) ?: "")
//            contEdit.setRefZona(tiet_zona.text.toString())
//        if(tiet_ruta.text.toString() != contenedor.refRuta?.value?.split(":")?.get(1) ?: "")
//            contEdit.setRefRuta(tiet_zona.text.toString())
        val patchObject = PatchContenedorObject()
        patchObject.addEntitie(contEdit)
        val jsonPatchObject = gson.toJson(patchObject)
        val jsonObject = JSONObject(jsonPatchObject)
        Toast.makeText(thiscontext, "$jsonObject", Toast.LENGTH_SHORT).show()
        requestHandler.patchRequest(url,jsonObject,{},{})
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ContenedorUpdate().apply {
                arguments = Bundle().apply {

                }
            }
    }

}