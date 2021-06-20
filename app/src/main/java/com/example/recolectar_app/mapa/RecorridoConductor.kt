package com.example.recolectar_app.mapa

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.recolectar_app.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class RecorridoConductor : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recorrido_conductor)
        val btnIniciar=findViewById<Button>(R.id.btn_Inicio)
        btnIniciar.setOnClickListener{
            mMap.addMarker(MarkerOptions().position(LatLng(-34.592678, -58.411280)))
            mMap.addMarker(MarkerOptions().position(LatLng(-34.594495, -58.412546)))
            mMap.addMarker(MarkerOptions().position(LatLng(-34.595071, -58.411865)))
            mMap.addMarker(MarkerOptions().position(LatLng(-34.593251, -58.409965)))
            mMap.addMarker(MarkerOptions().position(LatLng(-34.595668, -58.408229)))
            mMap.addMarker(MarkerOptions().position(LatLng(-34.597581, -58.409176)))
            mMap.addMarker(MarkerOptions().position(LatLng(-34.598019, -58.411532)))
            mMap.addMarker(MarkerOptions().position(LatLng(-34.597868, -58.415430)))
            mMap.addMarker(MarkerOptions().position(LatLng(-34.597076, -58.418840)))
            mMap.addMarker(MarkerOptions().position(LatLng(-34.592755, -58.419770)))
            mMap.addPolyline(UtilidadesMaps.lineOptions)

        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map_monitoreo) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap=googleMap
        val inicio = LatLng(-34.592678, -58.411280)
        val cameraPosition = CameraPosition.Builder()
                .target(inicio)
                .zoom(18f)
                //.bearing(45f)
                //.tilt(90f)
                .build()
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))

    }
}