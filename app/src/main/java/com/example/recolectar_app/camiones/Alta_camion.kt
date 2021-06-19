package com.example.recolectar_app.camiones

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.findNavController
import com.example.recolectar_app.R
import com.example.recolectar_app.RequestHandler
import com.example.recolectar_app.databinding.FragmentAltaCamionBinding
import com.google.gson.Gson
import org.json.JSONObject

class alta_camion : Fragment() {
    var url = "http://46.17.108.122:1026/v2/entities/"
    lateinit var thiscontext : Context

    private var _binding: FragmentAltaCamionBinding? = null
    private val binding get() = _binding!!
    lateinit var estado: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAltaCamionBinding.inflate(layoutInflater,container,false)
        if (container != null) {
            thiscontext = container.context
        };
        val requestHandler = RequestHandler.getInstance(thiscontext)


        //Carga Combo Estados
        val estados = resources.getStringArray(R.array.estados_camion)
        val arrayAdapter = ArrayAdapter(requireContext(),R.layout.combo_formulario,estados)
        binding.autoCompleteTextViewEstado.setAdapter(arrayAdapter)
/*
        //Carga Combo Tipo
        val tipos = resources.getStringArray(R.array.tipos_residuos)
        val arrayAdapterTipo = ArrayAdapter(requireContext(),R.layout.combo_formulario,tipos)
        autoCompleteTextView_tipo = v.findViewById(R.id.autoCompleteTextView_tipo)
        autoCompleteTextView_tipo.setAdapter(arrayAdapterTipo)

 */

        estado = arrayAdapter.getItem(0).toString()
        binding.autoCompleteTextViewEstado.setOnItemClickListener { parent, v, position, id ->
            estado= parent.getItemAtPosition(position).toString()
        }
        Log.d("Id camion", id.toString())

        //Botón para dar de Alta Camion
        binding.botonAgregar.setOnClickListener(){
            if(validarCampos()){
            addCamion(requestHandler)
            val action=alta_camionDirections.actionAltaCamionToCamiones()
            binding.root.findNavController().navigate(action)}
        }

        //Botón Cancelar
        binding.botonCancelar.setOnClickListener(){
            val action=alta_camionDirections.actionAltaCamionToCamiones()
            binding.root.findNavController().navigate(action)
        }
        return binding.root
    }


    private fun addCamion(requestHandler : RequestHandler) {
        val gson = Gson()

        //Armo el objeto camión para darlo de alta
        val camion = Camion(binding.editTextId.text.toString())
        camion.setCargoWeight(binding.editTextCarga.text.toString().toDouble())
        camion.setServiceStatus(estado)
        camion.setVehiclePlateIdentifier(binding.editTextPatente.text.toString())
        camion.setVehicleType("lorry")
        camion.setFillingLevel(0.0);
        //camion.setRefEmpleadoValue(empleado.toString())
        val string = gson.toJson(camion)
        val jsonObject = JSONObject(string)
        requestHandler.postRequest(url,{},{},jsonObject)
    }


    override fun onStart() {
        super.onStart()
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            alta_camion().apply {
                arguments = Bundle().apply {

                }
            }
    }

 //Validación de los Campos del Formulario para dar de Alta el Camión

    private fun validarCampos(): Boolean {
        var resultT = true
        val result = arrayOf(validarId(),validarPatente(),validarCarga())
        if(false in result){
            resultT =false
        }
        return resultT
    }


    private fun validarId():Boolean {
        return if (binding.editTextId.text.toString().isEmpty()) {
            binding.editTextId.error = "El campo es requerido"
            false
        }else{
            true
        }
    }

    private fun validarPatente():Boolean {
        return if (binding.editTextPatente.text.toString().isEmpty()) {
            binding.editTextPatente.error = "El campo es requerido"
            false
        }else{
            true
        }
    }
    private fun validarCarga():Boolean {
        return if (binding.editTextCarga.text.toString().isEmpty()) {
            binding.editTextCarga.error = "El campo es requerido"
            false
        }else{
            true
        }
    }

}