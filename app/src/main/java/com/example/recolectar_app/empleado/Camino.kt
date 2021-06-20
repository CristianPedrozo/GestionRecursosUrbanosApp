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
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.util.*

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
        val contexto = requireContext().applicationContext
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(contexto)
        context?.let { obtenerUbicacionActual(it) }
        val rootView= inflater.inflate(R.layout.fragment_empleado_camino, container, false)
        var mapFragment = childFragmentManager.findFragmentById(R.id.map_monitoreo) as SupportMapFragment
        mapFragment.getMapAsync { googleMap ->
            mMap = googleMap
            updateMap(contexto)
        }
        val btnIniciar=rootView.findViewById<Button>(R.id.btn_Inicio)
        val tvInstruccion=rootView.findViewById<TextView>(R.id.tv_Intruccion)
        val tvSigInstruccion=rootView.findViewById<TextView>(R.id.tv_SigInstruccion)
        val ivActual=rootView.findViewById<ImageView>(R.id.iv_actual)
        val ivSig=rootView.findViewById<ImageView>(R.id.iv_Sig)
        val cvInstruccion=rootView.findViewById<CardView>(R.id.cv_Instrucciones)
        btnIniciar.setOnClickListener{
            cvInstruccion.visibility=View.VISIBLE
            UtilidadesMaps.dibujarMapa(mMap)
            ActualizarMapa(tvInstruccion,tvSigInstruccion,ivActual,ivSig,contexto)
        }
        return rootView
    }


    private fun updateMap(contexto:Context) {
        UtilidadesMaps.obtenerRuta("http://192.168.0.37:3000/api/ruta/obtenerruta?id=zona:7&inicio={ \"latitude\":-34.585232,\"longitude\":-58.4039075}&fin={\"latitude\":-34.585232,\"longitude\":-58.4039075}"
            ,contexto)
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
        private const val LOCATION_PERMISSION_REQUEST_CODE=1
        @JvmStatic
        fun newInstance() =
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
                mMap.animateCamera(CameraUpdateFactory.newLatLng(ubicacionActual))
                mMap.addMarker(MarkerOptions().position(ubicacionActual))
                auxContador++
                handler.postDelayed(this, 1500)
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
                //UtilidadesMaps.crearRuta(contexto,ubicacionActual,ubicacionActual)
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(ubicacionActual,15f))
                mMap.isMyLocationEnabled=true
            }
        }
    }
}

