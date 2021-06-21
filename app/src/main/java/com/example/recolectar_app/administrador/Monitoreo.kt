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

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [fragment_administrador_monitoreo.newInstance] factory method to
 * create an instance of this fragment.
 */
/*
class Monitoreo : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_administrador_monitoreo, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment fragment_administrador_monitoreo.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Monitoreo().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}*/

class Monitoreo : Fragment() {
    lateinit var v: View
    lateinit var btn_buscarZona: FloatingActionButton
    lateinit var btn_configuracion: FloatingActionButton
    lateinit var mMap: GoogleMap
    lateinit var contexto :Context

/*
    private val callback = OnMapReadyCallback { googleMap ->
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        val sydney = LatLng(-34.0, 151.0)
        googleMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }

 */

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

    fun crearRuta() {

        //val url = "http://46.17.108.122:1026/v2/entities/?type=WasteContainer&options=keyValues&limit=1000"
        val url = "http://46.17.108.122:1026/v2/entities/?q=refZona==zona:7&type=WasteContainer"
        val jsonArrayRequest = JsonObjectRequest(Request.Method.GET, url, null, { response ->
            /*
            UtilidadesMaps.obtenerCoordenadasRuta(response)
            UtilidadesMaps.obtenerInstrucciones(response)
             */

            cargarMarkers(response as JSONArray,mMap)
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
            var coordenadas = ((response[i] as JSONObject) ["location"] as JSONObject)["coordinates"] as JSONArray
            Log.d("Coordenadas Prueba",coordenadas.toString())
            var lat = coordenadas[0] as Double
            var lng = coordenadas[1] as Double
            var locacion = LatLng(lat,lng)
            mMap.addMarker(MarkerOptions().position(locacion))
            contenedores.add(locacion)
        }
    }

    private fun updateMap() {
        val inicio =LatLng(-34.591283, -58.414742)
        val cameraPosition = CameraPosition.Builder().target(inicio).zoom(18f).build()
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
        mMap.addMarker(MarkerOptions().position(inicio))
        crearRuta()
    }
/*
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map_monitoreo) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

 */
}