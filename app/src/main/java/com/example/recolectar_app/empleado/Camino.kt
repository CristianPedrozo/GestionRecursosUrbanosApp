package com.example.recolectar_app.empleado


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.recolectar_app.R
import com.example.recolectar_app.UtilidadesMaps
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


/**
 * A simple [Fragment] subclass.
 * Use the [Camino.newInstance] factory method to
 * create an instance of this fragment.
 */
class Camino : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var mMap: GoogleMap

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
        val rootView= inflater.inflate(R.layout.fragment_empleado_camino, container, false)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync { googleMap ->
            mMap = googleMap
            updateMap()
        }
        val contexto = requireContext().applicationContext
        UtilidadesMaps.crearRuta(contexto,LatLng(-34.591283, -58.414742),LatLng(-34.595566, -58.397494))
        val btnIniciar=rootView.findViewById<Button>(R.id.btn_Inicio)
        btnIniciar.setOnClickListener{
            UtilidadesMaps.agregarMarkers(mMap)
            mMap.addPolyline(UtilidadesMaps.lineOptions)

        }
        return rootView
    }

    private fun updateMap() {
        val inicio = LatLng(-34.592678, -58.411280)
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
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Camino().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

}

