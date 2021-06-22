package com.example.recolectar_app.ui.view.camion

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.recolectar_app.databinding.FragmentUpdateCamionBinding
import com.example.recolectar_app.model.UpdateRequestModel
import com.example.recolectar_app.model.camion.CamionModel
import com.example.recolectar_app.ui.viewModel.camion.CamionUpdateVM
import com.google.gson.Gson
import org.json.JSONObject

class CamionUpdate : Fragment() {
    private val TAG = "Update Camion"
    private var _binding: FragmentUpdateCamionBinding? = null
    private val binding get() = _binding!!
    lateinit var thiscontext : Context
    private val camionUpdateVM : CamionUpdateVM by viewModels()
    private lateinit var camionModel : CamionModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
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
        val args = arguments?.let { CamionDetalleArgs.fromBundle(it) }
        camionModel = args?.camion!!
        loadCamionData(camionModel.id)

        //Para setear valor determinado en el combo
        //autoCompleteTextView.threshold(3)

        binding.botonConfirmarEditarCamion.setOnClickListener(){
            if(validarCampos()){
                editCamion()
                val action = CamionUpdateDirections.actionUpdateCamionToCamiones()
                binding.root.findNavController().navigate(action)
            }

        }

        binding.botonCancelarEdit.setOnClickListener(){
            val action = CamionUpdateDirections.actionUpdateCamionToCamiones()
            binding.root.findNavController().navigate(action)
        }
        return binding.root
    }

    private fun loadCamionData(camionId: String) = with(binding) {
        camionUpdateVM.camionSelectedData.observe(viewLifecycleOwner, {data ->
            textIdCamion.setText(data.id.split(":")[1])
            textPatenteCamion.setText(data.vehiclePlateIdentifier?.value)
            textCargaCamion.setText(data.cargoWeight?.value.toString())
            textEstadoCamion.setText(data.serviceStatus?.value)
        })
        val string = "?id=$camionId"
        camionUpdateVM.getCamionById(string)
    }

    private fun editCamion() = with(binding){
        val camion = CamionModel(textIdCamion.text.toString())
        camion.setCargoWeight(textCargaCamion.text.toString().toDouble())
        //camion.setServiceStatus(estado)
        camion.setVehiclePlateIdentifier(textPatenteCamion.text.toString())
        camion.setVehicleType("lorry")
        val camionUpdateRequest = UpdateRequestModel()
        camionUpdateRequest.addCamion(camion)
        camionUpdateVM.updateCamion(camionUpdateRequest)
    }


    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CamionUpdate().apply {
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