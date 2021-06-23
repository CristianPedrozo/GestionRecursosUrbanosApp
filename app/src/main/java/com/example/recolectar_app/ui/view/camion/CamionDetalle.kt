package com.example.recolectar_app.ui.view.camion
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.recolectar_app.databinding.FragmentCamionDetalleBinding
import com.example.recolectar_app.model.DeleteRequestModel
import com.example.recolectar_app.model.camion.CamionModel
import com.example.recolectar_app.ui.viewModel.camion.CamionDetalleVM

class CamionDetalle : Fragment() {
    private val TAG = "CamionDetalle"
    private var _binding: FragmentCamionDetalleBinding? = null
    private val binding get() = _binding!!
    private val camionDetalleVM : CamionDetalleVM by viewModels()
    private lateinit var camionModel : CamionModel
    lateinit var thiscontext : Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCamionDetalleBinding.inflate(layoutInflater,container,false)
        if (container != null) {
            thiscontext = container.context
        };
        val args = arguments?.let { CamionDetalleArgs.fromBundle(it) }
        camionModel = args?.camion!!
        binding.textIdCamion.setText(camionModel.id)
        binding.textPatenteCamion.setText(camionModel.vehiclePlateIdentifier?.value)
        binding.textCargaCamion.setText(camionModel.cargoWeight?.value.toString())
        binding.textEstadoCamion.setText(camionModel.serviceStatus?.value)

//        binding.textTipoCamion.setText(camion.vehicleType?.value)
//        binding.textZonaCamion.setText(camion.refZona?.value)
//        binding.textEmpleadoCamion.setText(camion.refEmpleado?.value)

        //Botón Editar
        binding.botonEditar.setOnClickListener(){
            val action = CamionDetalleDirections.actionCamionDetalleToUpdateCamion(camionModel)
            binding.root.findNavController().navigate(action)
        }

        //Botón Eliminar
        binding.botonEliminar.setOnClickListener(){
            removeCamion()
            val action = CamionDetalleDirections.actionCamionDetalleToCamiones()
            binding.root.findNavController().navigate(action)
        }
        
        camionDetalleVM.deleteCamionResult.observe(viewLifecycleOwner, {result ->
            if(result){
                Toast.makeText(thiscontext, "EXITO", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(thiscontext, "FAIL", Toast.LENGTH_SHORT).show()
            }
        })
        
        return binding.root
    }

    private fun removeCamion() {
        val camion = CamionModel(camionModel.id.split(":")[1])
        val camionDeteleRequest = DeleteRequestModel()
        camionDeteleRequest.addCamion(camion)
        camionDetalleVM.deleteCamion(camionDeteleRequest)
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            CamionDetalle().apply {
                arguments = Bundle().apply {
                }
            }
    }

}