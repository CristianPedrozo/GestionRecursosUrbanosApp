package com.example.recolectar_app.ui.view.zona

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.recolectar_app.contenedores.Contenedor
import com.example.recolectar_app.databinding.FragmentAltaZonaBinding
import com.example.recolectar_app.domain.PostZonaUseCase
import com.example.recolectar_app.model.zona.ZonaModel
import com.google.gson.Gson
import org.json.JSONObject


class alta_zona : Fragment() {

    var url = "http://46.17.108.122:1026/v2/entities/"
    lateinit var thiscontext : Context
    private var _binding: FragmentAltaZonaBinding? = null
    private val binding get() = _binding!!
    private lateinit var zona : ZonaModel
    private val id = String
    private val refVehicle = String
    private val name = String
    private val contenedores = ArrayList<Contenedor>()
    private val gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAltaZonaBinding.inflate(inflater,container,false)
        if (container != null) {
            thiscontext = container.context
        };
        binding.editTextId.hint = "Coloca un id"
        binding.editTextRefVehicle.hint = "Coloca el id del camion"
        binding.editTextName.hint = "Nombre de la zona"
        binding.botonAgregar.setOnClickListener {
            addZona()
            val action = alta_zonaDirections.actionAltaZonaToListZonas()
            binding.root.findNavController().navigate(action)
        }
        binding.botonCancelar.setOnClickListener{
            val action = alta_zonaDirections.actionAltaZonaToListZonas()
            binding.root.findNavController().navigate(action)
        }


        return binding.root
    }

    private fun addZona() {
        zona = ZonaModel(binding.editTextId.editText?.text.toString())
        val jsonObject = JSONObject(gson.toJson(zona))
        PostZonaUseCase(jsonObject)
    }

//    private fun addZona(requestHandler : RequestHandler) {
//        val gson = Gson()
//        val zona = ZonaModel(binding.editTextIdAltaZona.text.toString())
//        zona.setRefVehicleValue(binding.editTextRefVehicleAltaZona.text.toString())
//        zona.setContenedores(ArrayList())
//        val string = gson.toJson(zona)
//        val jsonObject = JSONObject(string)
//        Toast.makeText(thiscontext, "$jsonObject", Toast.LENGTH_SHORT).show()
//        requestHandler.postRequest(url,{},{},jsonObject)
//    }


    override fun onStart() {
        super.onStart()
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            alta_zona().apply {
                arguments = Bundle().apply {

                }
            }
    }
}