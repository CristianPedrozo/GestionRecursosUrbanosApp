package com.example.recolectar_app.camiones

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.recolectar_app.Objetos.Contenedor.Contenedor
import com.example.recolectar_app.R
import com.example.recolectar_app.RequestHandler
import com.example.recolectar_app.fragments.CamionDetalleArgs
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import org.json.JSONObject

class Update_camion : Fragment() {
    private val TAG = "CamionDetalle"
    private var url = "http://46.17.108.122:1026/v2/entities/?type=Vehicle&id="
    private var urlDelete = "http://46.17.108.122:1026/v2/entities/"
    private var urlUpdate = "http://46.17.108.122:1026/v2/op/update"

    private lateinit var v: View
    private lateinit var id: String
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
        id = args?.id.toString()
        text_id_camion=v.findViewById(R.id.text_id_camion)
        text_patente_camion=v.findViewById(R.id.text_patente_camion)
        text_estado_camion=v.findViewById(R.id.text_estado_camion)
        text_carga_camion=v.findViewById(R.id.text_carga_camion)

        return v
    }

    override fun onStart() {
        super.onStart()
        var gson = Gson()
        val queue = Volley.newRequestQueue(activity)
        val url_contenedor=url + id
        val jsonArrayRequest = JsonArrayRequest(url_contenedor,
            { response ->
                Log.d("Response Prueba", response.toString())

                val contenedor : Contenedor = gson.fromJson(response.getJSONObject(0).toString(),
                    Contenedor::class.java)
                Log.d("Contenedor", contenedor.toString())
                /*
                text_contenedor_id.setText(contenedor.id)
                text_contenedor_tipo.setText(contenedor.type)
                text_contenedor_estado.setText(contenedor.status.value)
                text_contenedor_latitud.setText(contenedor.location.value.coordinates[0].toString())
                text_contenedor_longitud.setText(contenedor.location.value.coordinates[1].toString())
                text_contenedor_ruta.setText(contenedor.refRuta.value)
                text_contenedor_vehiculo.setText(contenedor.refVehicle.value)
                text_contenedor_temperatura.setText(contenedor.temperature.value.toString())
                text_contenedor_zona.setText(contenedor.refZona.value)

                 */
            }, {print("prueba error")})
        queue.add(jsonArrayRequest)
    }
/*
    private fun addCamion(requestHandler : RequestHandler) {
        val gson = Gson()

        //Armo el objeto camión para darlo de alta
        val camion = Camion(Camion.CargoWeight(carga.text.toString().toInt()),id.text.toString(),Camion.ServiceStatus(estado),Camion.VehiclePlateIdentifier(patente.text.toString()),Camion.VehicleType("lorry"))

        //camion.setRefEmpleadoValue(empleado.toString())
        val string = gson.toJson(camion)
        Log.d("POST camion", string.toString() )
        val jsonObject = JSONObject(string)
        requestHandler.postRequest(url,{},{},jsonObject)
    }
    
 */
}