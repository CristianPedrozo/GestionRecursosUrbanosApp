package com.example.recolectar_app.zonas

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.recolectar_app.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import org.json.JSONObject


class ZonaDetalle : Fragment() {
    private var url = "http://46.17.108.122:1026/v2/entities"
    private lateinit var v : View
    private lateinit var btn_edit_zona : FloatingActionButton
    private lateinit var et_id_zona : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_zona_detalle, container, false)
        btn_edit_zona = v.findViewById(R.id.btn_edit_zona)
        et_id_zona = v.findViewById(R.id.txt_id_edit_zona)
        btn_edit_zona.setOnClickListener {
            editZona()
        }
        return v
    }

    private fun editZona() {
        var gson = Gson()
        val queue = Volley.newRequestQueue(activity)
        val jsonObject = JSONObject()
        jsonObject.put("id",et_id_zona)
        jsonObject.put("refVehicle",Zona.RefVehicle("Relationship","vehicle:1"))
        jsonObject.put("type","Zona")
        val jsonObjectRequest = JsonObjectRequest(Request.Method.PATCH, url, jsonObject,
            {

            },
            {

            })
        queue.add(jsonObjectRequest)
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