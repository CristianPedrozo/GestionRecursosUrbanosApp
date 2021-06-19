package com.example.recolectar_app.empleado


import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.example.recolectar_app.R
import com.example.recolectar_app.mapa.UtilidadesMaps
import com.example.recolectar_app.mapa.UtilidadesOrdenamiento
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.util.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private var auxContador=0



class Camino : Fragment() {
    private lateinit var mMap: GoogleMap
    var handler: Handler = Handler()
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var ultimaUbicacion: Location


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val rootView= inflater.inflate(R.layout.fragment_empleado_camino, container, false)
        var mapFragment = childFragmentManager.findFragmentById(R.id.map_monitoreo) as SupportMapFragment
        mapFragment.getMapAsync { googleMap ->
            mMap = googleMap
            updateMap()
        }
        val contexto = requireContext().applicationContext

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(contexto)
        context?.let { obtenerUbicacionActual(it) }
        val btnIniciar=rootView.findViewById<Button>(R.id.btn_Inicio)
        val btnAux=rootView.findViewById<Button>(R.id.btnAUX)
        val tvInstruccion=rootView.findViewById<TextView>(R.id.tv_Intruccion)
        val tvSigInstruccion=rootView.findViewById<TextView>(R.id.tv_SigInstruccion)
        val ivActual=rootView.findViewById<ImageView>(R.id.iv_actual)
        val ivSig=rootView.findViewById<ImageView>(R.id.iv_Sig)
        val cvInstruccion=rootView.findViewById<CardView>(R.id.cv_Instrucciones)
        btnAux.setOnClickListener {
            context?.let { it1 ->
                UtilidadesOrdenamiento.realizarOrden(LatLng(ultimaUbicacion.latitude,ultimaUbicacion.longitude),
                    it1
                )
            }
        }
        btnIniciar.setOnClickListener{
           context?.let { it1 -> UtilidadesMaps.crearRuta(it1,LatLng(ultimaUbicacion.latitude,ultimaUbicacion.longitude), LatLng(ultimaUbicacion.latitude,ultimaUbicacion.longitude)) }
           UtilidadesMaps.agregarMarkers(mMap)
           mMap.addPolyline(UtilidadesMaps.lineOptions)
           UtilidadesMaps.actualizarIntruccion(tvInstruccion,tvSigInstruccion,ivActual,ivSig,LatLng(ultimaUbicacion.latitude,ultimaUbicacion.longitude),contexto,mMap)
           cvInstruccion.visibility=View.VISIBLE
           context?.let { it1 ->
               ActualizarMapa(tvInstruccion,tvSigInstruccion,ivActual,ivSig,
                  it1
               )}
        }
        return rootView
    }


    private fun updateMap() {
        val inicio =LatLng(-34.591283, -58.414742)
        val cameraPosition = CameraPosition.Builder()
            .target(inicio)
            .zoom(18f)
            //.bearing(45f)
            //.tilt(90f)
            .build()
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))

    }

    companion object {

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Camino.
         */
        private const val LOCATION_PERMISSION_REQUEST_CODE=1
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Camino().apply {
                arguments = Bundle().apply {

                }
            }
    }

   fun ActualizarMapa(t1:TextView,t2:TextView,iv_actual:ImageView,iv_sig:ImageView/*,ubicacionActual:LatLng*/,contexto: Context) {
        handler.postDelayed(object : Runnable {
            override fun run() {
                // función a ejecutar
                var ubicacionActual= UtilidadesMaps.coordenadasDecodificadas[auxContador]
                UtilidadesMaps.actualizarIntruccion(t1,t2,iv_actual,iv_sig,ubicacionActual,contexto,mMap)
               // mMap.animateCamera(CameraUpdateFactory.newLatLng(ubicacionActual))
                mMap.addMarker(MarkerOptions().position(ubicacionActual))
                auxContador++
                handler.postDelayed(this, 3000)
            }
        }, 3000)
    }
    private fun obtenerUbicacionActual(contexto : Context){
        if(ActivityCompat.checkSelfPermission(contexto,android.Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(contexto as Activity, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),LOCATION_PERMISSION_REQUEST_CODE)
            return
        }
        fusedLocationProviderClient.lastLocation.addOnSuccessListener {location->
            if (location!=null){
                ultimaUbicacion=location
                val ubicacionActual=LatLng(location.latitude,location.longitude)
                UtilidadesMaps.crearRuta(contexto,ubicacionActual,ubicacionActual)
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(ubicacionActual,15f))
                mMap.isMyLocationEnabled=true
            }
        }
    }
}

