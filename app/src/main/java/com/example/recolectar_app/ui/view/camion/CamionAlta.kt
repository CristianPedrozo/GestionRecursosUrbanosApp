package com.example.recolectar_app.ui.view.camion

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.recolectar_app.R
import com.example.recolectar_app.databinding.FragmentAltaCamionBinding
import com.example.recolectar_app.model.camion.CamionModel
import com.example.recolectar_app.ui.viewModel.camion.CamionAltaVM

class CamionAlta : Fragment() {
    lateinit var thiscontext : Context
    private var _binding: FragmentAltaCamionBinding? = null
    private val binding get() = _binding!!
    private val camionAltaVM : CamionAltaVM by viewModels()
    lateinit var estado: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAltaCamionBinding.inflate(layoutInflater,container,false)
        if (container != null) {
            thiscontext = container.context
        };

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
            addCamion()
            val action=CamionAltaDirections.actionAltaCamionToCamiones()
            binding.root.findNavController().navigate(action)}
        }

        //Botón Cancelar
        binding.botonCancelar.setOnClickListener(){
            val action=CamionAltaDirections.actionAltaCamionToCamiones()
            binding.root.findNavController().navigate(action)
        }

        camionAltaVM.altaCamionResult.observe(viewLifecycleOwner, {result ->
            if(result){
                Toast.makeText(thiscontext, "EXITO", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(thiscontext, "FAIL", Toast.LENGTH_SHORT).show()
            }
        })
        
        return binding.root
    }


    private fun addCamion(){
        val camion = CamionModel(binding.editTextId.text.toString())
        camion.setCargoWeight(binding.editTextCarga.text.toString().toDouble())
        camion.setServiceStatus(estado)
        camion.setVehiclePlateIdentifier(binding.editTextPatente.text.toString())
        camion.setVehicleType("lorry")
        camion.setFillingLevel(0.0);
        camionAltaVM.crearCamion(camion)
    }


    companion object {

        @JvmStatic
        fun newInstance() =
            CamionAlta().apply {
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