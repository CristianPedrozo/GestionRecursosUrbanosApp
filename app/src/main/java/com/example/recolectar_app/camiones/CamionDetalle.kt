package com.example.recolectar_app.camiones
import DeleteCamionRequest
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.findNavController
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.recolectar_app.R
import com.example.recolectar_app.RequestHandler
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import org.json.JSONObject

class CamionDetalle : Fragment() {
    private val TAG = "CamionDetalle"
    private var url = "http://46.17.108.122:1026/v2/entities/?type=Vehicle&id="
    private var urlDelete = "http://46.17.108.122:1026/v2/entities/"
    private var urlUpdate = "http://46.17.108.122:1026/v2/op/update"

    private lateinit var v: View
    private lateinit var id: String
    private lateinit var status : String
    private lateinit var patente : String
    private lateinit var cargo : String
    lateinit var thiscontext : Context
    lateinit var text_id_camion: TextView
    lateinit var text_patente_camion: TextView
    lateinit var text_carga_camion:TextView
    //lateinit var text_tipo_camion:TextView
    lateinit var text_estado_camion:TextView
    //lateinit var text_camion_zona:TextView
    //lateinit var text_empleado_camion:TextView
    lateinit var btn_editar: FloatingActionButton
    lateinit var btn_eliminar: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_camion_detalle, container, false)
        if (container != null) {
            thiscontext = container.context
        };
        val requestHandler = RequestHandler.getInstance(thiscontext)
        val args = arguments?.let { CamionDetalleArgs.fromBundle(it) }
        id = args?.idCamion.toString()
        status = args?.statusCamion.toString()
        patente = args?.patente.toString()
        cargo = args?.carga.toString()
        text_id_camion=v.findViewById(R.id.text_id_camion)
        text_id_camion.text = id
        text_patente_camion=v.findViewById(R.id.text_patente_camion)
        text_patente_camion.text = patente
        text_estado_camion=v.findViewById(R.id.text_estado_camion)
        text_estado_camion.text = status
        text_carga_camion=v.findViewById(R.id.text_carga_camion)
        text_carga_camion.text = cargo
        //text_tipo_camion=v.findViewById(R.id.text_tipo_camion)
        //text_camion_zona=v.findViewById(R.id.text_zona)
        //text_empleado_camion=v.findViewById(R.id.text_empleado_camion)

        //Botón Editar
        btn_editar = v.findViewById(R.id.boton_editar)
        btn_editar.setOnClickListener(){
            val action = CamionDetalleDirections.actionCamionDetalleToUpdateCamion(id,patente,cargo,status)
            v.findNavController().navigate(action)
        }
        //Botón Eliminar
        btn_eliminar = v.findViewById(R.id.boton_eliminar)
        btn_eliminar.setOnClickListener(){
            removeCamion(requestHandler)
            val action = CamionDetalleDirections.actionCamionDetalleToCamiones()
            v.findNavController().navigate(action)

        }
        return v
    }

    private fun removeCamion(requestHandler: RequestHandler) {
        val gson = Gson()
        Toast.makeText(thiscontext, id, Toast.LENGTH_SHORT).show()
        val camion = Camion(id)
        Toast.makeText(thiscontext, "$camion", Toast.LENGTH_LONG).show()
        val deleteObject = DeleteCamionRequest()
        deleteObject.addEntitie(camion)
        val jsonDeleteObject = gson.toJson(deleteObject)
        val jsonObject = JSONObject(jsonDeleteObject)
        Toast.makeText(thiscontext, "${jsonObject}", Toast.LENGTH_LONG).show()
        requestHandler.deleteRequest(urlUpdate,jsonObject,{},{})
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