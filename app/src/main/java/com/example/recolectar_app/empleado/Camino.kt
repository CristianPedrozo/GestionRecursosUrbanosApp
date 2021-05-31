package com.example.recolectar_app.empleado


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.example.recolectar_app.R
import com.example.recolectar_app.UtilidadesMaps
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import java.util.*



class Camino : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var mMap: GoogleMap



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
        val rootView= inflater.inflate(R.layout.fragment_empleado_camino, container, false)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync { googleMap ->
            mMap = googleMap
            updateMap()
        }
        val contexto = requireContext().applicationContext
        UtilidadesMaps.crearRuta(contexto,LatLng(-34.591283, -58.414742),LatLng(-34.595566, -58.397494))
        val btnIniciar=rootView.findViewById<Button>(R.id.btn_Inicio)
        val tvInstruccion=rootView.findViewById<TextView>(R.id.tv_Intruccion)
        val tvSigInstruccion=rootView.findViewById<TextView>(R.id.tv_SigInstruccion)
        val ivActual=rootView.findViewById<ImageView>(R.id.iv_actual)
        val ivSig=rootView.findViewById<ImageView>(R.id.iv_Sig)
        val cvInstruccion=rootView.findViewById<CardView>(R.id.cv_Instrucciones)
        btnIniciar.setOnClickListener{
            UtilidadesMaps.agregarMarkers(mMap)
            mMap.addPolyline(UtilidadesMaps.lineOptions)
            UtilidadesMaps.actualizarIntruccion(tvInstruccion,tvSigInstruccion,ivActual,ivSig,LatLng(-34.591283, -58.414742),contexto)
            cvInstruccion.visibility=View.VISIBLE
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

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Camino().apply {
                arguments = Bundle().apply {

                }
            }
    }
}

