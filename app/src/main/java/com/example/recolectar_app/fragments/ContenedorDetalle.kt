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
import com.example.recolectar_app.Objetos.Contenedor.Contenedor
import com.example.recolectar_app.R
import com.example.recolectar_app.RequestHandler
import com.example.recolectar_app.zonas.ZonaDetalleArgs
import com.google.android.material.textfield.TextInputEditText
import com.google.gson.Gson

class ContenedorDetalle : Fragment() {

    private lateinit var idContenedor: String
    private lateinit var v: View
    //var url = "http://46.17.108.122:1026/v2/entities/?type=WasteContainer&id=$id"
    var url = "http://46.17.108.122:1026/v2/entities/?type=WasteContainer&id=wastecontainer:1"
    //var url: String = url_base.plus(id)
    lateinit var text_contenedor_id : TextView
    lateinit var text_contenedor_tipo: TextView
    lateinit var text_contenedor_latitud: TextView
    lateinit var text_contenedor_longitud: TextView
    lateinit var text_contenedor_estado: TextView
    lateinit var text_contenedor_ruta: TextView
    lateinit var text_contenedor_vehiculo: TextView
    lateinit var text_contenedor_temperatura: TextView
    lateinit var text_contenedor_zona: TextView
    lateinit var thiscontext : Context

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v= inflater.inflate(R.layout.fragment_contenedor_detalle, container, false)
        if (container != null) {
            thiscontext = container.context
        };
        val requestHandler = RequestHandler.getInstance(thiscontext)
        val args = arguments?.let { ContenedorDetalleArgs.fromBundle(it) }
        idContenedor = args?.id.toString()
        text_contenedor_id  = v.findViewById(R.id.text_id);
        text_contenedor_tipo = v.findViewById(R.id.text_tipo);
        text_contenedor_latitud = v.findViewById(R.id.text_latitud)
        text_contenedor_longitud = v.findViewById(R.id.text_longitud)
        text_contenedor_estado= v.findViewById(R.id.text_estado)
        text_contenedor_ruta= v.findViewById(R.id.text_ruta)
        text_contenedor_vehiculo = v.findViewById(R.id.text_camion)
        text_contenedor_temperatura = v.findViewById(R.id.text_temperatura)
        text_contenedor_zona=v.findViewById(R.id.text_zona)

        return v
    }


    override fun onStart() {
        super.onStart()
        Log.d("ID Prueba", idContenedor)
        var gson = Gson()
        val queue = Volley.newRequestQueue(activity)
        val jsonArrayRequest = JsonArrayRequest(url,
            { response ->
                Log.d("Response Prueba", response.toString())

                val contenedor : Contenedor = gson.fromJson(response.getJSONObject(0).toString(),
                    Contenedor::class.java)
                Log.d("Contenedor", contenedor.toString())
                text_contenedor_id.setText(contenedor.id)
                text_contenedor_tipo.setText(contenedor.type)
                text_contenedor_estado.setText(contenedor.status.value)
                text_contenedor_latitud.setText(contenedor.location.value.coordinates[0].toString())
                text_contenedor_longitud.setText(contenedor.location.value.coordinates[1].toString())
                text_contenedor_ruta.setText(contenedor.refRuta.value)
                text_contenedor_vehiculo.setText(contenedor.refVehicle.value)
                text_contenedor_temperatura.setText(contenedor.temperature.value.toString())
                text_contenedor_zona.setText(contenedor.refZona.value)
            }, {print("prueba error")})
        queue.add(jsonArrayRequest)
    }

}