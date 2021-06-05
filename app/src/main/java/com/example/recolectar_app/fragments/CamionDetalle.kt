package com.example.recolectar_app.fragments
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
import com.example.recolectar_app.R
import com.example.recolectar_app.RequestHandler
import com.example.recolectar_app.camiones.Camion
import com.google.gson.Gson

class CamionDetalle : Fragment() {
    private val TAG = "CamionDetalle"
    private var url = "http://46.17.108.122:1026/v2/entities/?type=Vehicle&id="
    private lateinit var v: View
    private lateinit var id: String
    lateinit var thiscontext : Context
    lateinit var text_id_camion: TextView
    lateinit var text_patente_camion: TextView
    lateinit var text_tipo_camion:TextView
    lateinit var text_estado_camion:TextView
    //lateinit var text_camion_zona:TextView
    lateinit var text_empleado_camion:TextView

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
        text_tipo_camion=v.findViewById(R.id.text_tipo_camion)
        //text_camion_zona=v.findViewById(R.id.text_zona)
        text_empleado_camion=v.findViewById(R.id.text_empleado_camion)
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
                text_tipo_camion.setText(camion.vehicleType.value)
                text_estado_camion.setText(camion.serviceStatus.value)
                //
                //text_camion_tipo.setText(camion.type)
                //text_camion_estado.setText(camion.status.value)
                //text_camion_zona.setText(camion.refZona.value)
                //text_empleado_camion.text= camion.refEmpleado.toString()

            }, {print("prueba error")})
        queue.add(jsonArrayRequest)
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