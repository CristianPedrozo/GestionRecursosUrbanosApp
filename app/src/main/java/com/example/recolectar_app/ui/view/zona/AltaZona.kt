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
import com.example.recolectar_app.databinding.FragmentAltaZonaBinding
import com.example.recolectar_app.model.zona.ZonaModel
import com.example.recolectar_app.ui.viewModel.zona.ZonaAltaVM


class ZonaAlta : Fragment() {

    var url = "http://46.17.108.122:1026/v2/entities/"
    lateinit var thiscontext : Context
    private var _binding: FragmentAltaZonaBinding? = null
    private val binding get() = _binding!!
    private val zonaAltaVM : ZonaAltaVM by viewModels()
    private lateinit var zona : ZonaModel

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
        binding.editTextEmpleado.hint = "Email del empleado"
        binding.botonAgregar.setOnClickListener {
            addZona()
            val action = ZonaAltaDirections.actionAltaZonaToListZonas()
            binding.root.findNavController().navigate(action)
        }
        binding.botonCancelar.setOnClickListener{
            val action = ZonaAltaDirections.actionAltaZonaToListZonas()
            binding.root.findNavController().navigate(action)
        }

        zonaAltaVM.altaZonaResult.observe(viewLifecycleOwner, { result ->
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
        zona.setEmpleado(binding.editTextEmpleado.editText?.text.toString())
        zona.setContenedores(ArrayList())
        zonaAltaVM.crearZona(zona)
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            ZonaAlta().apply {
                arguments = Bundle().apply {

                }
            }
    }
}