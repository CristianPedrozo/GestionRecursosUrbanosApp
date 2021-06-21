package com.example.recolectar_app.administrador
import android.accessibilityservice.GestureDescription
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.navigation.findNavController
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.recolectar_app.R
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import org.json.JSONArray
import org.json.JSONObject

class SeleccionarZonaDialog : DialogFragment(){
    lateinit var combo_zonas: AutoCompleteTextView
    lateinit var btn_buscar: Button
    lateinit var v:View
    var zonas:MutableList<String> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var v: View = inflater.inflate(R.layout.seleccionar_zona,container, false)
        obtenerZonas()
        combo_zonas = v.findViewById(R.id.combo_zonas)
        val arrayAdapter = ArrayAdapter(requireContext(),android.R.layout.simple_list_item_1,zonas)
        combo_zonas.setAdapter(arrayAdapter)

        btn_buscar =v.findViewById(R.id.btn_buscar)
        /*
        btn_buscar.setOnClickListener() {
            val action = SeleccionarZonaDialogDirections.actionSeleccionarZonaDialogToMonitoreo()
            v.findNavController().navigate(action)
        }

         */
        return v
    }

    fun obtenerZonas() {
        val url = "http://46.17.108.122:1026/v2/entities/?type=Zona&options=keyValues&attrs=id&limit=100"
        val jsonArray = JsonArrayRequest(Request.Method.GET, url, null, { response ->
            generarArrayZonas(response)
            Log.d("Zonas", response.toString())
        }, { error ->
            Log.d("ERROR: ", error.toString())
        }
        )
        val request = Volley.newRequestQueue(requireContext())
        request.add(jsonArray)

    }

    fun generarArrayZonas(response:JSONArray){
        for (i in 0 until response.length()){
            var zona = (response[i] as JSONObject) ["id"] as String
            zonas.add(zona)
        }
    }
}