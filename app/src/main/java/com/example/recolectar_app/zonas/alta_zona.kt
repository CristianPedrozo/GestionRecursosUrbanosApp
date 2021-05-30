package com.example.recolectar_app.zonas

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import com.example.recolectar_app.R
import com.example.recolectar_app.RequestHandler
import com.example.recolectar_app.administrador.ZonasDirections
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import org.json.JSONObject


class alta_zona : Fragment() {

    var url = "http://46.17.108.122:1026/v2/entities/"
    lateinit var thiscontext : Context
    lateinit var v : View
    lateinit var editText_Id_Zona : EditText
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
        editText_Id_Zona = v.findViewById(R.id.editText_Id_zona)
        btn_alta_zona = v.findViewById(R.id.btn_alta_zona)
        btn_alta_zona.setOnClickListener {
            addZona(requestHandler)
        }
        return v
    }

    private fun addZona(requestHandler : RequestHandler) {
        val gson = Gson()
        val zona = Zona(editText_Id_Zona.text.toString(),
            Zona.RefVehicle("Relationship","vehicle:1"),
            Zona::class.java.simpleName.toString())
        val string = gson.toJson(zona)
        val jsonObject = JSONObject(string)
//        Toast.makeText(thiscontext, "obj $jsonObject", Toast.LENGTH_LONG).show()
        requestHandler.postRequest(
            url,
            { response ->
                Toast.makeText(thiscontext, "response alta" + response, Toast.LENGTH_LONG).show()
            },
            { error ->
                Toast.makeText(thiscontext, "error alta" + error, Toast.LENGTH_LONG).show()
            },
            jsonObject
        )
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