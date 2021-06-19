package com.example.recolectar_app.camiones

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.recolectar_app.RequestHandler
import com.example.recolectar_app.databinding.FragmentUpdateCamionBinding
import com.google.gson.Gson
import org.json.JSONObject

class Update_camion : Fragment() {
    private val TAG = "Update Camion"
    private var url = "http://46.17.108.122:1026/v2/op/update"

    private var _binding: FragmentUpdateCamionBinding? = null
    private val binding get() = _binding!!
    lateinit var thiscontext : Context
    private lateinit var id: String
    private lateinit var camion : Camion


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUpdateCamionBinding.inflate(layoutInflater,container,false)
        //Carga Combo Estados
/*
        val estados = resources.getStringArray(R.array.estados_camion)
        val arrayAdapter = ArrayAdapter(requireContext(),R.layout.combo_formulario,estados)
        autoCompleteTextView = v.findViewById(R.id.autoCompleteTextView_estado)
        autoCompleteTextView.setAdapter(arrayAdapter)
*/
        if (container != null) {
            thiscontext = container.context
        };
        val requestHandler = RequestHandler.getInstance(thiscontext)
        val args = arguments?.let { CamionDetalleArgs.fromBundle(it) }
        camion = args?.camion!!
        binding.textIdCamion.setText(camion.id)
        binding.textPatenteCamion.setText(camion.vehiclePlateIdentifier?.value)
        binding.textCargaCamion.setText(camion.cargoWeight?.value.toString())
        binding.textEstadoCamion.setText(camion.serviceStatus?.value)
        //Para setear valor determinado en el combo
        //autoCompleteTextView.threshold(3)

        binding.botonConfirmarEditarCamion.setOnClickListener(){
            if(validarCampos()){
                editCamion(requestHandler)
                val action = Update_camionDirections.actionUpdateCamionToCamiones()
                binding.root.findNavController().navigate(action)
            }

        }

        binding.botonCancelarEdit.setOnClickListener(){
            val action = Update_camionDirections.actionUpdateCamionToCamiones()
            binding.root.findNavController().navigate(action)
        }
        return binding.root
    }
    private fun editCamion(requestHandler: RequestHandler) {
        val gson = Gson()
        //estado = tiet_estado.text.toString()
        //Toast.makeText(thiscontext, estado, Toast.LENGTH_SHORT).show()
        val camion = Camion(id)
        camion.setCargoWeight(binding.textCargaCamion.text.toString().toDouble())
        //camion.setServiceStatus(estado)
        camion.setVehiclePlateIdentifier(binding.textPatenteCamion.text.toString())
        camion.setVehicleType("lorry")
        val patchObject = PatchCamionObject()
        patchObject.addEntitie(camion)
        val jsonPatchObject = gson.toJson(patchObject)
        val jsonObject = JSONObject(jsonPatchObject)
        //Toast.makeText(thiscontext, "$jsonObject", Toast.LENGTH_LONG).show()
        requestHandler.patchRequest(url,jsonObject,{},{})
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Update_camion().apply {
                arguments = Bundle().apply {

                }
            }
    }


    //Validación de los Campos del Formulario para dar de Modificación  el Camión

    private fun validarCampos(): Boolean {
        var resultT = true
        val result = arrayOf(validarPatente(),validarCarga())
        if(false in result){
            resultT =false
        }
        return resultT
    }

    private fun validarPatente():Boolean {
        return if (binding.textPatenteCamion.text.toString().isEmpty()) {
            binding.textPatenteCamion.error = "El campo es requerido"
            false
        }else{
            true
        }
    }
    private fun validarCarga():Boolean {
        return if (binding.textCargaCamion.text.toString().isEmpty()) {
            binding.textCargaCamion.error = "El campo es requerido"
            false
        }else{
            true
        }
    }

}