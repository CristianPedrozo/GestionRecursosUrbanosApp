package com.example.recolectar_app.contenedores

import DeleteContenedorRequest
import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.findNavController
import com.example.recolectar_app.R
import com.example.recolectar_app.RequestHandler
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import org.json.JSONObject

class ContenedorDetalle : Fragment() {
    private val TAG = "ContenedorDetalle"
    private var url = "http://46.17.108.122:1026/v2/op/update"
    private lateinit var v: View
    private lateinit var btn_edit_contenedor : FloatingActionButton
    private lateinit var btn_remove_contenedor : FloatingActionButton
    private lateinit var contenedor: Contenedor
    lateinit var thiscontext : Context
    private lateinit var text_contenedor_id : TextView
    private lateinit var text_contenedor_tipo: TextView
    private lateinit var text_contenedor_latitud: TextView
    private lateinit var text_contenedor_longitud: TextView
    private lateinit var text_contenedor_estado: TextView
    private lateinit var text_contenedor_ruta: TextView
    private lateinit var text_contenedor_vehiculo: TextView
    private lateinit var text_contenedor_temperatura: TextView
    private lateinit var text_contenedor_zona: TextView
    private lateinit var text_contenedor_llenado : TextView
    private lateinit var text_contenedor_proxima_visita: TextView
    private lateinit var text_contenedor_ultima_visita: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        v= inflater.inflate(R.layout.fragment_contenedor_detalle, container, false)
        if (container != null) {
            thiscontext = container.context
        };
        val requestHandler = RequestHandler.getInstance(thiscontext)
        val args = arguments?.let { ContenedorDetalleArgs.fromBundle(it) }
        contenedor = args?.contenedor!!
        text_contenedor_id  = v.findViewById(R.id.text_id);
        text_contenedor_id.text = contenedor.id!!.split(":")[1]
        text_contenedor_tipo = v.findViewById(R.id.text_tipo);
        text_contenedor_tipo.text = contenedor.wasteType.value
        text_contenedor_latitud = v.findViewById(R.id.text_latitud)
        text_contenedor_latitud.text = contenedor.location.value.coordinates[0].toString()
        text_contenedor_longitud = v.findViewById(R.id.text_longitud)
        text_contenedor_longitud.text = contenedor.location.value.coordinates[1].toString()
        text_contenedor_estado= v.findViewById(R.id.text_estado)
        text_contenedor_estado.text = contenedor.status.value
        text_contenedor_ruta= v.findViewById(R.id.text_ruta)
        text_contenedor_ruta.text = contenedor.refRuta?.value
        text_contenedor_vehiculo = v.findViewById(R.id.text_camion)
        text_contenedor_vehiculo.text = contenedor.refVehicle?.value
        text_contenedor_temperatura = v.findViewById(R.id.text_temperatura)
        text_contenedor_temperatura.text = contenedor.temperature?.value.toString()
        text_contenedor_zona=v.findViewById(R.id.text_zona)
        text_contenedor_zona.text = contenedor.refZona?.value
        text_contenedor_proxima_visita=v.findViewById(R.id.text_proxima_visita)
        text_contenedor_proxima_visita.text = contenedor.nextActuationDeadline?.value
        text_contenedor_ultima_visita=v.findViewById(R.id.text_ultima_visita)
        text_contenedor_ultima_visita.text = contenedor.dateLastEmptying?.value
        text_contenedor_llenado=v.findViewById(R.id.text_llenado)
        text_contenedor_llenado.text = "${contenedor.fillingLevel.value.toString().split(".")[1]}%"

        btn_edit_contenedor = v.findViewById(R.id.boton_editar_contenedor)
        btn_edit_contenedor.setOnClickListener(){
            val action = ContenedorDetalleDirections.actionContenedorDetalleToUpdateContenedor(contenedor)
            v.findNavController().navigate(action)
        }
        btn_remove_contenedor = v.findViewById(R.id.boton_remover_contenedor)
        btn_remove_contenedor.setOnClickListener(){
            removeContenedor(requestHandler)
            val action = ContenedorDetalleDirections.actionContenedorDetalleToContenedores()
            v.findNavController().navigate(action)
        }
        return v
    }

    private fun removeContenedor(requestHandler: RequestHandler) {
        val gson = Gson()
        val contenedor = Contenedor(contenedor.id!!.split(":")[1])
        val deleteObject = DeleteContenedorRequest()
        deleteObject.addEntitie(contenedor)
        val jsonDeleteObject = gson.toJson(deleteObject)
        val jsonObject = JSONObject(jsonDeleteObject)
        Toast.makeText(thiscontext, "$jsonObject", Toast.LENGTH_SHORT).show()
        requestHandler.deleteRequest(url,jsonObject,{},{})
    }


    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ContenedorDetalle().apply {
                arguments = Bundle().apply {

                }
            }
    }
}