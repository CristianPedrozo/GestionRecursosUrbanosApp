package com.example.recolectar_app.contenedores

import DeleteContenedorRequest
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.findNavController
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.recolectar_app.PatchContenedorObject
import com.example.recolectar_app.PatchRequestObject
import com.example.recolectar_app.R
import com.example.recolectar_app.RequestHandler
import com.example.recolectar_app.camiones.Camion
import com.example.recolectar_app.zonas.Zona
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import org.json.JSONObject

class ContenedorDetalle : Fragment() {
    private val TAG = "ContenedorDetalle"
    private var url = "http://46.17.108.122:1026/v2/op/update"
    //private var urlUpdate = "http://46.17.108.122:1026/v2/op/update"
    private lateinit var v: View
    private lateinit var id: String
    private lateinit var status : String
    private lateinit var btn_edit_contenedor : FloatingActionButton
    private lateinit var btn_remove_contenedor : FloatingActionButton
    private lateinit var et_id_contenedor : TextView
    lateinit var thiscontext : Context
    lateinit var text_contenedor_id : TextView
    lateinit var text_contenedor_tipo: TextView
    lateinit var text_contenedor_latitud: TextView
    lateinit var text_contenedor_longitud: TextView
    lateinit var text_contenedor_estado: TextView
    lateinit var text_contenedor_ruta: TextView
    lateinit var text_contenedor_vehiculo: TextView
    lateinit var text_contenedor_temperatura: TextView
    lateinit var text_contenedor_zona: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

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
        id = args?.idContenedor.toString()
        status = args?.statusContenedor.toString()
        text_contenedor_id  = v.findViewById(R.id.text_id);
        text_contenedor_id.text = id
        text_contenedor_tipo = v.findViewById(R.id.text_tipo);
        text_contenedor_latitud = v.findViewById(R.id.text_latitud)
        text_contenedor_longitud = v.findViewById(R.id.text_longitud)
        text_contenedor_estado= v.findViewById(R.id.text_estado)
        text_contenedor_estado.text = status
        text_contenedor_ruta= v.findViewById(R.id.text_ruta)
        text_contenedor_vehiculo = v.findViewById(R.id.text_camion)
        text_contenedor_temperatura = v.findViewById(R.id.text_temperatura)
        text_contenedor_zona=v.findViewById(R.id.text_zona)

        btn_edit_contenedor = v.findViewById(R.id.boton_editar_contenedor)
        btn_edit_contenedor.setOnClickListener(){
            val action = ContenedorDetalleDirections.actionContenedorDetalleToUpdateContenedor(id,status)
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
        val contenedor = Contenedor(id)
        val deleteObject = DeleteContenedorRequest()
        deleteObject.addEntitie(contenedor)
        val jsonDeleteObject = gson.toJson(deleteObject)
        val jsonObject = JSONObject(jsonDeleteObject)
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