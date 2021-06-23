package com.example.recolectar_app.administrador

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.recolectar_app.R
import com.example.recolectar_app.camiones.alta_camionDirections
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.JsonObject
import org.json.JSONObject

class configuration : Fragment() {
    lateinit var v: View
    lateinit var umbral_nuevo:TextView
    lateinit var umbral_actual:TextView
    var umbral: String =""
    lateinit var btn_actualizar_umbral: FloatingActionButton
    lateinit var btn_cancelar: FloatingActionButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v= inflater.inflate(R.layout.fragment_configuration, container, false)

        //Carga de umbral actual
        umbral_actual = v.findViewById(R.id.Text_Umbral_Actual)
        obtenerUmbralMinimo()
        //Para cargar el nuevo umbral
        umbral_nuevo = v.findViewById(R.id.editText_Umbral_Minimo)

        //Botón para actualizar Umbral
        btn_actualizar_umbral= v.findViewById(R.id.boton_agregar)
        btn_actualizar_umbral.setOnClickListener(){
            if(validarCampos()){
                actualizarUmbralMinimo()
                val action= configurationDirections.actionConfigurationToMonitoreo()
                v.findNavController().navigate(action)}
        }

        //Botón Cancelar
        btn_cancelar = v.findViewById(R.id.boton_cancelar)
        btn_cancelar.setOnClickListener(){
            val action= configurationDirections.actionConfigurationToMonitoreo()
            v.findNavController().navigate(action)
        }
        return v
    }

    fun obtenerUmbralMinimo(){
        val request = Volley.newRequestQueue(requireContext())
        val url = "https://recolectar-api.herokuapp.com/api/contenedor/getminimo"

        val jsonObjectRequest= JsonObjectRequest(url,null,
            { response ->
            umbral = response.getDouble("umbral_minimo").toString()
                umbral_actual.setText(umbral)
            Log.d("Umbral actual", umbral)
        }, { error -> error.printStackTrace() }
        )

        request.add(jsonObjectRequest)
    }

    fun actualizarUmbralMinimo(){
        val request = Volley.newRequestQueue(requireContext())
        val url = "https://recolectar-api.herokuapp.com/api/contenedor/setminimo"
        val jsonObject =JSONObject()
        val minimo: Double

        minimo = umbral_nuevo.text.toString().toDouble()
        jsonObject.put("minimo",minimo)

        val jsonObjectRequest= JsonObjectRequest(url,jsonObject,
            { response ->
                Log.d("Umbral nuevo", minimo.toString())
            }, { error -> error.printStackTrace() }
        )

        request.add(jsonObjectRequest)
    }

    private fun validarCampos(): Boolean {
        return if (umbral_nuevo.text.toString().isEmpty()) {
            umbral_nuevo.error = "El campo es requerido"
            false
        }else true
    }
}

