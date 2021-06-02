package com.example.recolectar_app.empleado

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.recolectar_app.R
import com.example.recolectar_app.entities.User
import org.json.JSONObject


class Datos : Fragment() {
    private lateinit var v: View
    var url = "http://46.17.108.122:1026/v2/entities/empleado:1?options=keyValues"
//    var url = "http://46.17.108.122:1026/v2/entities/?type=Empleado"
    private lateinit var datos : TextView




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
<<<<<<< HEAD
        v = inflater.inflate(R.layout.fragment_empleado_datos, container, false)
        datos = v.findViewById(R.id.txtdatos)
        val queue = Volley.newRequestQueue(activity)

        val jsonObjectRequest = JsonObjectRequest(url, null,
            { response ->
                datos.text = "La respuesta es: ${response}" },
            { response ->
                datos.text = "La respuesta es: ${response}"}
        )
        queue.add(jsonObjectRequest)
        return v
=======

        return inflater.inflate(R.layout.fragment_empleado_datos, container, false)

>>>>>>> 25a7edf7d3fed5b9632d69996f8a59a0e678f799
    }


    companion object {

        @JvmStatic
        fun newInstance(param1: String) =
            Datos().apply {
                arguments = Bundle().apply {

                }
            }
    }


}