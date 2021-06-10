package com.example.recolectar_app.camiones
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
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.recolectar_app.PatchContenedorObject
import com.example.recolectar_app.R
import com.example.recolectar_app.RequestHandler
import com.example.recolectar_app.contenedores.Contenedor
import com.example.recolectar_app.contenedores.Update_Contenedor
import com.example.recolectar_app.contenedores.Update_ContenedorDirections
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import org.json.JSONObject

class Update_camion : Fragment() {
    private val TAG = "Update Camion"
    //private var url = "http://46.17.108.122:1026/v2/entities/?type=Vehicle&id="
    // var urlDelete = "http://46.17.108.122:1026/v2/entities/"
    private var url = "http://46.17.108.122:1026/v2/op/update"

    private lateinit var v: View
    private lateinit var id: String
    private lateinit var status : String
    private lateinit var patente : String
    private lateinit var cargo : String
    lateinit var thiscontext : Context
    lateinit var text_id_camion: TextView
    lateinit var text_patente_camion: TextView
    lateinit var text_carga_camion: TextView
    //lateinit var text_tipo_camion:TextView
    lateinit var text_estado_camion: TextView
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
        v= inflater.inflate(R.layout.fragment_update_camion, container, false)
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

        btn_editar = v.findViewById(R.id.boton_confirmar_editar_camion)
        btn_editar.setOnClickListener(){
            editCamion(requestHandler)
            val action = Update_camionDirections.actionUpdateCamionToCamiones()
            v.findNavController().navigate(action)
        }
        return v
    }
    private fun editCamion(requestHandler: RequestHandler) {
        val gson = Gson()
        //estado = tiet_estado.text.toString()
        //Toast.makeText(thiscontext, estado, Toast.LENGTH_SHORT).show()
        val camion = Camion(id)
        //camion.setStatus(estado)
        val patchObject = PatchCamionObject()
        patchObject.addEntitie(camion)
        val jsonPatchObject = gson.toJson(patchObject)
        val jsonObject = JSONObject(jsonPatchObject)
        Toast.makeText(thiscontext, "$jsonObject", Toast.LENGTH_LONG).show()
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
}