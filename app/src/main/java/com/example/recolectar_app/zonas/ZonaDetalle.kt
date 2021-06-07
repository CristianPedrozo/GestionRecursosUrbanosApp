package com.example.recolectar_app.zonas

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
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
    private var url = "http://46.17.108.122:1026/v2/entities/"
    private var urlUpdate = "http://46.17.108.122:1026/v2/op/update"
    private lateinit var v : View
    private lateinit var btn_edit_zona : FloatingActionButton
    private lateinit var btn_remove_zona : FloatingActionButton
    private lateinit var et_id_zona : TextView
    private lateinit var et_refVehicle_zona : EditText
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
        id = args?.idZona.toString()
        refVehicle = args?.refVehicleZona.toString()
        btn_edit_zona = v.findViewById(R.id.btn_edit_zona)
        btn_remove_zona = v.findViewById(R.id.btn_remove_zona)
        et_id_zona = v.findViewById(R.id.txt_id_edit_zona)
        et_id_zona.text = id
        et_refVehicle_zona = v.findViewById(R.id.txt_refVehicle_edit_zona)
        et_refVehicle_zona.hint = "Coloca el id del camion"

        btn_edit_zona.setOnClickListener {
            editZona(requestHandler)
            val action = ZonaDetalleDirections.actionZonaDetalleToZonas()
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
        requestHandler.deleteRequest(urlUpdate,jsonObject,{},{})
    }


    private fun editZona(requestHandler: RequestHandler) {
        val gson = Gson()
        refVehicle = et_refVehicle_zona.text.toString()
        val zona = Zona(id)
        zona.setRefVehicleValue(refVehicle)
        val patchObject = PatchRequestObject()
        patchObject.addEntitie(zona)

        val jsonPatchObject = gson.toJson(patchObject)

        val jsonObject = JSONObject(jsonPatchObject)

        requestHandler.patchRequest(urlUpdate,jsonObject,{},{})
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