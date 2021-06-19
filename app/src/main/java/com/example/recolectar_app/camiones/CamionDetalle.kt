package com.example.recolectar_app.camiones
import DeleteCamionRequest
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.recolectar_app.RequestHandler
import com.example.recolectar_app.databinding.FragmentCamionDetalleBinding
import com.google.gson.Gson
import org.json.JSONObject

class CamionDetalle : Fragment() {
    private val TAG = "CamionDetalle"
    private var url = "http://46.17.108.122:1026/v2/op/update"
    private var _binding: FragmentCamionDetalleBinding? = null
    private val binding get() = _binding!!
    private lateinit var camion : Camion
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
        val requestHandler = RequestHandler.getInstance(thiscontext)
        val args = arguments?.let { CamionDetalleArgs.fromBundle(it) }
        camion = args?.camion!!
        binding.textIdCamion.setText(camion.id)
        binding.textPatenteCamion.setText(camion.vehiclePlateIdentifier?.value)
        binding.textCargaCamion.setText(camion.cargoWeight?.value.toString())
        binding.textEstadoCamion.setText(camion.serviceStatus?.value)

//        binding.textTipoCamion.setText(camion.vehicleType?.value)
//        binding.textZonaCamion.setText(camion.refZona?.value)
//        binding.textEmpleadoCamion.setText(camion.refEmpleado?.value)

        //Botón Editar
        binding.botonEditar.setOnClickListener(){
            val action = CamionDetalleDirections.actionCamionDetalleToUpdateCamion(camion)
            binding.root.findNavController().navigate(action)
        }

        //Botón Eliminar
        binding.botonEliminar.setOnClickListener(){
            removeCamion(requestHandler)
            val action = CamionDetalleDirections.actionCamionDetalleToCamiones()
            binding.root.findNavController().navigate(action)

        }
        return binding.root
    }

    private fun removeCamion(requestHandler: RequestHandler) {
        val gson = Gson()
        val camion = Camion(camion.id.split(":")[1])
        val deleteObject = DeleteCamionRequest()
        deleteObject.addEntitie(camion)
        val jsonDeleteObject = gson.toJson(deleteObject)
        val jsonObject = JSONObject(jsonDeleteObject)
        requestHandler.deleteRequest(url,jsonObject,{},{})
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CamionDetalle().apply {
                arguments = Bundle().apply {
                }
            }
    }

}