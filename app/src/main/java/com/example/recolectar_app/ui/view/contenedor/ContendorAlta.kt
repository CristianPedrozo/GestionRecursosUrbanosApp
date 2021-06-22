package com.example.recolectar_app.ui.view.contenedor

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.recolectar_app.R
import com.example.recolectar_app.RequestHandler
import com.example.recolectar_app.databinding.FragmentAltaContenedorBinding
import com.example.recolectar_app.model.contenedor.ContenedorModel
import com.example.recolectar_app.ui.viewModel.contenedor.ContenedorAltaVM
import com.google.gson.Gson
import org.json.JSONObject
import java.util.regex.Pattern

class ContenedorAlta : Fragment() {
    val url = "http://46.17.108.122:1026/v2/entities/"
    lateinit var thiscontext : Context
    private var _binding: FragmentAltaContenedorBinding? = null
    private val binding get() = _binding!!
    private val contenedorAltaVM : ContenedorAltaVM by viewModels()
    private lateinit var estado : String
    private lateinit var tipo : String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAltaContenedorBinding.inflate(layoutInflater,container,false)
        if (container != null) {
            thiscontext = container.context
        };
        val requestHandler = RequestHandler.getInstance(thiscontext)
        //Carga Combo Estados
        val estados = resources.getStringArray(R.array.estados_contenedor)
        val arrayAdapterEstado = ArrayAdapter(requireContext(),R.layout.combo_formulario,estados)
        binding.autoCompleteTextView.setAdapter(arrayAdapterEstado)
        estado = arrayAdapterEstado.getItem(0).toString()
        binding.autoCompleteTextView.setOnItemClickListener { parent, v, position, id ->
            estado= parent.getItemAtPosition(position).toString()
        }
        //Carga Combo Tipo
        val tipos = resources.getStringArray(R.array.tipos_residuos)
        val arrayAdapterTipo = ArrayAdapter(requireContext(),R.layout.combo_formulario,tipos)
        binding.autoCompleteTextViewTipo.setAdapter(arrayAdapterTipo)
        tipo = arrayAdapterTipo.getItem(0).toString()
        binding.autoCompleteTextViewTipo.setOnItemClickListener { parent, v, position, id ->
            tipo= parent.getItemAtPosition(position).toString()
        }

        //Validar campos para el alta de un contenedor
        binding.botonAgregar.setOnClickListener{
            validarCampos()
            addContenedor()
            val action = ContenedorAltaDirections.actionAltaContenedorToContenedores()
            binding.root.findNavController().navigate(action)
        }
        
        contenedorAltaVM.altaContenedorData.observe(viewLifecycleOwner, { result ->
            if(result){
                Toast.makeText(thiscontext, "EXITO", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(thiscontext, "FAIL", Toast.LENGTH_SHORT).show()
            }
        })
        
        return binding.root
    }

    private fun addContenedor(){
        val contenendor = ContenedorModel(binding.editTextCodigo.editText?.text.toString())
        val latlong : MutableList<Double> = arrayListOf()
        latlong.add(binding.editTextLatitud.editText?.text.toString().toDouble())
        latlong.add(binding.editTextLongitud.editText?.text.toString().toDouble())
        contenendor.setLocation(latlong)
        contenendor.setFillingLevel(0.0)
        contenendor.setWasteType(tipo)
        contenendor.setStatus(estado)
        contenendor.setRefZona(binding.editTextZona.editText?.text.toString())
        contenedorAltaVM.crearContenedor(contenendor)
    }


    //Validación de los campos del Formulario ALTA CONTENEDOR
    private fun validarCampos(){
        val result = arrayOf(validarCodigo(),validarLatitud(),validarLongitud())
        if(false in result){
            return
        }
    }

    private fun validarCodigo():Boolean {
        return if (binding.editTextCodigo.editText.toString().isEmpty()) {
            binding.editTextCodigo.error = "El campo es requerido"
            false
        }else{
            true
        }
    }

    private fun validarLatitud():Boolean{
        val latitudRegex = Pattern.compile("^[-+]?([1-8]?\\d(\\.\\d+)?|90(\\.0+)?)")
        return if (binding.editTextLatitud.editText?.text.toString().isEmpty()) {
            binding.editTextLatitud.error = "El campo es requerido"
            false
        }else if(!latitudRegex.matcher(binding.editTextLatitud.editText?.text.toString()).matches()){
            binding.editTextLatitud.error = "Formato longitud incorrecta"
            false
        }else{
            true
        }
    }

    private fun validarLongitud():Boolean{
        val longitudRegex = Pattern.compile("^[-+]?(180(\\.0+)?|((1[0-7]\\d)|([1-9]?\\d))(\\.\\d+)?)")

        return if (binding.editTextLongitud.editText?.text.toString().isEmpty()) {
            binding.editTextLongitud.error = "El campo es requerido"
            false
        }else if(!longitudRegex.matcher(binding.editTextLongitud.editText?.text.toString()).matches()){
            binding.editTextLongitud.error = "Formato longitud incorrecta"
            false
        }else{
            true
        }
    }
}