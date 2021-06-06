package com.example.recolectar_app.fragments
import DeleteCamionRequest
import android.app.DownloadManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.recolectar_app.PatchRequestObject
import com.example.recolectar_app.R
import com.example.recolectar_app.RequestHandler
import com.example.recolectar_app.camiones.Camion
import com.example.recolectar_app.zonas.Zona
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
        id = args?.id.toString()
        text_id_camion=v.findViewById(R.id.text_id_camion)
        text_patente_camion=v.findViewById(R.id.text_patente_camion)
        text_estado_camion=v.findViewById(R.id.text_estado_camion)
        text_carga_camion=v.findViewById(R.id.text_carga_camion)
        //text_tipo_camion=v.findViewById(R.id.text_tipo_camion)
        //text_camion_zona=v.findViewById(R.id.text_zona)
        //text_empleado_camion=v.findViewById(R.id.text_empleado_camion)

        //Botón Editar
        btn_editar = v.findViewById(R.id.boton_editar)
        btn_editar.setOnClickListener(){
            val action = CamionDetalleDirections.actionCamionDetalleToAltaCamion()
            v.findNavController().navigate(action)
        }
        //Botón Eliminar
        btn_eliminar = v.findViewById(R.id.boton_eliminar)
        btn_eliminar.setOnClickListener(){
            removeCamion(id)
            val action = CamionDetalleDirections.actionCamionDetalleToCamiones()
            v.findNavController().navigate(action)

        }

        return v
    }
    override fun onStart() {
        super.onStart()
        var gson = Gson()
        val queue = Volley.newRequestQueue(activity)
        val url_camion=url + id
        val jsonArrayRequest = JsonArrayRequest(url_camion,
            { response ->
                Log.d("Response Prueba", response.toString())

                val camion : Camion = gson.fromJson(response.getJSONObject(0).toString(),
                    Camion::class.java)
                text_id_camion.setText(camion.id)
                text_patente_camion.setText(camion.vehiclePlateIdentifier.value)
                //text_tipo_camion.setText(camion.vehicleType.value)
                text_carga_camion.setText(camion.cargoWeight.value.toString())
                text_estado_camion.setText(camion.serviceStatus.value)
                //
                //text_camion_tipo.setText(camion.type)
                //text_camion_estado.setText(camion.status.value)
                //text_camion_zona.setText(camion.refZona.value)
                //text_empleado_camion.text= camion.refEmpleado.toString()

            }, {print("prueba error")})
        queue.add(jsonArrayRequest)
    }



    private fun removeCamion(id:String/*requestHandler: RequestHandler*/) {
        /*
        val gson = Gson()
        val camion = Camion2(id)
        val deleteObject = DeleteCamionRequest()
        deleteObject.addEntitie(camion)
        val jsonDeleteObject = gson.toJson(deleteObject)
        val jsonObject = JSONObject(jsonDeleteObject)
        requestHandler.deleteRequest(urlUpdate,jsonObject,{},{})

         */
        // var gson = Gson()
        val queue = Volley.newRequestQueue(activity)
        val url_delete_camion=urlDelete + id
        val stringRequest = StringRequest(Request.Method.DELETE, url_delete_camion,
            Response.Listener<String> { response ->
                // Do something with the response
            },
            Response.ErrorListener { error ->
                // Handle error
                Log.d("error delete Vane", error.toString())
            })
        queue.add(stringRequest)
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