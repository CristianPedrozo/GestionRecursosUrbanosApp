package com.example.recolectar_app.zonas

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.findNavController
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.recolectar_app.R
import com.example.recolectar_app.RequestHandler
import com.example.recolectar_app.adapters.ZonaListAdapter
import com.example.recolectar_app.administrador.Zonas
import com.example.recolectar_app.administrador.ZonasDirections
import com.example.recolectar_app.databinding.FragmentItemZonaBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import org.json.JSONObject


class ZonaDetalle : Fragment() {
    private var url = "http://46.17.108.122:1026/v2/entities"
    private lateinit var v : View
    private lateinit var btn_edit_zona : FloatingActionButton
    private lateinit var btn_remove_zona : FloatingActionButton
    private lateinit var et_id_zona : EditText
    lateinit var thiscontext : Context
    var id : String = ""


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
        val id = args?.idZona
//        Toast.makeText(thiscontext, "ID DESDE ZONADETALLE ${id}", Toast.LENGTH_SHORT).show()
        btn_edit_zona = v.findViewById(R.id.btn_edit_zona)
        btn_remove_zona = v.findViewById(R.id.btn_remove_zona)
        et_id_zona = v.findViewById(R.id.txt_id_edit_zona)
        btn_edit_zona.setOnClickListener {
            editZona(requestHandler)
        }
        btn_remove_zona.setOnClickListener{
            removeZona(requestHandler)
            val action = ZonaDetalleDirections.actionZonaDetalleToZonas()
            v.findNavController().navigate(action)
        }
        return v
    }

    private fun removeZona(requestHandler: RequestHandler) {
        Toast.makeText(thiscontext,"delete", Toast.LENGTH_LONG).show()
        requestHandler.deleteRequest("${url}${id}",
            { response ->
                Toast.makeText(thiscontext, "response delete" + response, Toast.LENGTH_LONG).show()
            },
            { error ->
                Toast.makeText(thiscontext, "error delete" + error, Toast.LENGTH_LONG).show()
            }
        )
    }


    private fun editZona(requestHandler : RequestHandler) {
        val gson = Gson()
        val zona = Zona(et_id_zona.text.toString(),
            Zona.RefVehicle("Relationship","vehicle:1"),
            Zona::class.java.simpleName.toString())
        val string = gson.toJson(zona)
        val jsonObject = JSONObject(string)
        requestHandler.patchRequest("${url}/${id}",{ response ->
            Toast.makeText(thiscontext, "response patch" + response, Toast.LENGTH_LONG).show()
            },
            { error ->
                Toast.makeText(thiscontext, "error patch" + error, Toast.LENGTH_LONG).show()
            },
            jsonObject
        )
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