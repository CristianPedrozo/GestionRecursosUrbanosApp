package com.example.recolectar_app.administrador

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.recolectar_app.R
import com.example.recolectar_app.mapa.UtilidadesMaps
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.json.JSONArray
import org.json.JSONObject
import java.lang.NumberFormatException

class Monitoreo : Fragment() {
    lateinit var v: View
    lateinit var btn_buscarZona: FloatingActionButton
    lateinit var btn_configuracion: FloatingActionButton
    lateinit var mMap: GoogleMap
    lateinit var contexto :Context
    lateinit var zona_seleccionada: String
    var TAG ="MonitoreoFragment"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (container != null) {
            contexto = container.context
        };
        v = inflater.inflate(R.layout.fragment_administrador_monitoreo, container, false)

        var mapFragment = childFragmentManager.findFragmentById(R.id.map_monitoreo) as SupportMapFragment
        mapFragment.getMapAsync { googleMap -> mMap = googleMap
            updateMap()
            }
        //Botón Configuración
        btn_configuracion = v.findViewById(R.id.boton_configuracion)
        btn_configuracion.setOnClickListener(){
            val action= MonitoreoDirections.actionMonitoreoToConfiguration()
            v.findNavController().navigate(action)
        }

        //Botón Buscar Zona
        btn_buscarZona = v.findViewById(R.id.boton_buscarZona)
        btn_buscarZona.setOnClickListener(){
            var dialog= SeleccionarZonaDialog()
            dialog.show(getParentFragmentManager(), "Seleccionar Zona");
        }


        return v

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    fun mostrarContenedores() {
        observerZona()

        val url = "http://46.17.108.122:1026/v2/entities/?type=WasteContainer&options=keyValues&limit=60"
        //val url = "http://46.17.108.122:1026/v2/entities/?q=refZona==zona:2&type=WasteContainer&options=keyValues&attrs=id,location&limit=100"
        val jsonArrayRequest = JsonArrayRequest( url, { response ->

            cargarMarkers(response,mMap)
            Log.d("Coordenadas", response.toString())
        }, { error ->
            Toast.makeText(contexto, "No se puede conectar $error", Toast.LENGTH_LONG).show()
            println()
            Log.d("ERROR: ", error.toString())
        }
        )
        val request = Volley.newRequestQueue(contexto)
        request.add(jsonArrayRequest)
    }

    var contenedores:MutableList<LatLng> = ArrayList()

    fun cargarMarkers(response: JSONArray, mMap:GoogleMap){
        for (i in 0 until response.length()){
            Log.d("Response 0", response[0].toString())
            var coordenadas = ((response[i] as JSONObject) ["location"] as JSONObject)["coordinates"] as JSONArray
            Log.d("Coordenadas Prueba",coordenadas.toString())
            var lat = coordenadas[0] as Double
            var lng = coordenadas[1] as Double

            var locacion = LatLng(lat,lng)
            if(i==0){
                val cameraPosition = CameraPosition.Builder().target(locacion).zoom(18f).build()
                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
                mMap.addMarker(MarkerOptions().position(locacion))
            }
            mMap.addMarker(MarkerOptions().position(locacion))
            contenedores.add(locacion)
        }

    }


    private fun updateMap() {
        //val inicio =LatLng(-34.591283, -58.414742)
        mostrarContenedores()
        //val inicio = contenedores[0]
        /*
        val cameraPosition = CameraPosition.Builder().target(inicio).zoom(18f).build()
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
        mMap.addMarker(MarkerOptions().position(inicio))
           mostrarContenedores()
         */

    }

    fun observerZona(){
        findNavController()
            .currentBackStackEntry
            ?.savedStateHandle
            ?.getLiveData("Id_Zona", "")
            ?.observe(viewLifecycleOwner){
                result ->
                    print(result)
                    zona_seleccionada = result

            }
    }
}