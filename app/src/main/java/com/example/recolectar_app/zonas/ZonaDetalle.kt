package com.example.recolectar_app.zonas

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import com.example.recolectar_app.PatchRequestObject
import com.example.recolectar_app.R
import com.example.recolectar_app.RequestHandler
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import org.json.JSONObject


class ZonaDetalle : Fragment() {
    private val TAG = "ZonaDetalle"
    private var url = "http://46.17.108.122:1026/v2/op/update"
    private lateinit var v : View
    private lateinit var zona: Zona
    private lateinit var btn_edit_zona : FloatingActionButton
    private lateinit var btn_remove_zona : FloatingActionButton
    private lateinit var tv_id_zona : TextView
    private lateinit var tv_refVehicle_zona : TextView
    private lateinit var tv_contenedores_zona : TextView
    lateinit var thiscontext : Context
    lateinit var id : String
    lateinit var refVehicle : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        v = inflater.inflate(R.layout.fragment_zona_detalle, container, false)
        if (container != null) {
            thiscontext = container.context
        };
        val requestHandler = RequestHandler.getInstance(thiscontext)
        val args = arguments?.let { ZonaDetalleArgs.fromBundle(it) }
        zona = args?.zona!!
        btn_edit_zona = v.findViewById(R.id.boton_editar_zona)
        btn_remove_zona = v.findViewById(R.id.boton_eliminar_zona)
        tv_id_zona = v.findViewById(R.id.text_id_detalle_zona)
        tv_id_zona.text = zona.id!!.split(":")[1]
        tv_refVehicle_zona = v.findViewById(R.id.text_camion_detalle_zona)
        tv_refVehicle_zona.text = zona.refVehicle?.value!!.split(":")[1]
        tv_contenedores_zona = v.findViewById(R.id.text_contenedores_detalle_zona)
        tv_contenedores_zona.text = zona.contenedores.value.size.toString()

        btn_edit_zona.setOnClickListener {
            val action = ZonaDetalleDirections.actionZonaDetalleToUpdateZona(zona)
            v.findNavController().navigate(action)
        }
        btn_remove_zona.setOnClickListener{
            removeZona(requestHandler)
            val action = ZonaDetalleDirections.actionZonaDetalleToZonas()
            v.findNavController().navigate(action)
        }
        return v
    }

    private fun removeZona(requestHandler: RequestHandler) {
        val gson = Gson()
        val zona = Zona(id)
        val deleteObject = DeleteZonaRequest()
        deleteObject.addEntitie(zona)
        val jsonDeleteObject = gson.toJson(deleteObject)
        val jsonObject = JSONObject(jsonDeleteObject)
        requestHandler.deleteRequest(url,jsonObject,{},{})
    }




    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ZonaDetalle().apply {
                arguments = Bundle().apply {

                }
            }
    }


}