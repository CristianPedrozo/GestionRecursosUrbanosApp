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
import com.example.recolectar_app.model.contenedor.ContenedorModel
import com.example.recolectar_app.databinding.FragmentAltaZonaBinding
import com.example.recolectar_app.model.zona.ZonaModel
import com.example.recolectar_app.ui.viewModel.zona.ZonaAltaVM
import com.google.gson.Gson


class ZonaAlta : Fragment() {

    var url = "http://46.17.108.122:1026/v2/entities/"
    lateinit var thiscontext : Context
    private var _binding: FragmentAltaZonaBinding? = null
    private val binding get() = _binding!!
    private val zonaAltaVM : ZonaAltaVM by viewModels()
    private lateinit var zona : ZonaModel
    private val id = String
    private val refVehicle = String
    private val name = String
    private val contenedores = ArrayList<ContenedorModel>()
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
            val action = ZonaAltaDirections.actionAltaZonaToListZonas()
            binding.root.findNavController().navigate(action)
        }
        binding.botonCancelar.setOnClickListener{
            val action = ZonaAltaDirections.actionAltaZonaToListZonas()
            binding.root.findNavController().navigate(action)
        }

        zonaAltaVM.altaZonaData.observe(viewLifecycleOwner, { result ->
            if(result){
                Toast.makeText(thiscontext, "EXITO", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(thiscontext, "FAIL", Toast.LENGTH_SHORT).show()
            }
        })

        return binding.root
    }

    private fun addZona() {
        zona = ZonaModel(binding.editTextId.editText?.text.toString())
        zona.setNombre(binding.editTextName.editText?.text.toString())
        zona.setRefVehicleValue(binding.editTextRefVehicle.editText?.text.toString())
        zona.setContenedores(ArrayList())
        zonaAltaVM.crearZona(zona)
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
            ZonaAlta().apply {
                arguments = Bundle().apply {

                }
            }
    }
}