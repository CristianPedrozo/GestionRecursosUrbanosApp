package com.example.recolectar_app.zonas

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.navigation.findNavController
import com.example.recolectar_app.R
import com.example.recolectar_app.RequestHandler
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import org.json.JSONObject


class alta_zona : Fragment() {

    var url = "http://46.17.108.122:1026/v2/entities/"
    lateinit var thiscontext : Context
    lateinit var v : View
    lateinit var editText_id_alta_zona : EditText
    lateinit var editText_refVehicle_alta_zona : EditText
    lateinit var btn_alta_zona : FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (container != null) {
            thiscontext = container.context
        };

        var requestHandler = RequestHandler.getInstance(thiscontext)
        v= inflater.inflate(R.layout.fragment_alta_zona, container, false)
        editText_id_alta_zona = v.findViewById(R.id.editText_Id_alta_zona)
        editText_refVehicle_alta_zona = v.findViewById(R.id.editText_refVehicle_alta_zona)
        editText_id_alta_zona.hint = "Coloca un id"
        editText_refVehicle_alta_zona.hint = "Coloca el id del camion"
        btn_alta_zona = v.findViewById(R.id.btn_alta_zona)
        btn_alta_zona.setOnClickListener {
            addZona(requestHandler)
            val action = alta_zonaDirections.actionAltaZonaToZonas()
            v.findNavController().navigate(action)
        }
        return v
    }

    private fun addZona(requestHandler : RequestHandler) {
        val gson = Gson()
        val zona = Zona(editText_id_alta_zona.text.toString())
        zona.setRefVehicleValue(editText_refVehicle_alta_zona.text.toString())
        val string = gson.toJson(zona)
        val jsonObject = JSONObject(string)
        requestHandler.postRequest(url,{},{},jsonObject)
    }


    override fun onStart() {
        super.onStart()
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            alta_zona().apply {
                arguments = Bundle().apply {

                }
            }
    }
}