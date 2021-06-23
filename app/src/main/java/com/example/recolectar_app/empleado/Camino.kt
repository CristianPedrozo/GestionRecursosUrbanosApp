package com.example.recolectar_app.empleado


import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.Handler
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.recolectar_app.R
import com.example.recolectar_app.core.UsuarioGlobal
import com.example.recolectar_app.mapa.models.ContenedorDato
import com.example.recolectar_app.mapa.models.datosRuta
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import java.math.BigDecimal
import java.text.DecimalFormat
import java.util.*

class Camino : Fragment() {
    private lateinit var mMap: GoogleMap
    var handler: Handler = Handler()
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var ultimaUbicacion: Location
    val datosRuta: datosRuta by viewModels()
    var pausa = false
    var auxContador=0
    var finalizarViaje = true
    var inicio = LatLng(-34.591831, -58.415096)
    var fin = LatLng(-34.594102, -58.418722)
    var zona = UsuarioGlobal.zona


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }


    @SuppressLint("SetTextI18n")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        finalizarViaje=true
        datosRuta.borrarDatos()
        pausa=false
        auxContador=0
        val contexto = requireContext().applicationContext
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(contexto)
        //context?.let { obtenerUbicacionActual(it) }
        val rootView= inflater.inflate(R.layout.fragment_empleado_camino, container, false)
        var mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync { googleMap ->
            mMap = googleMap
            updateMap(contexto)
        }

        val tvInstruccion=rootView.findViewById<TextView>(R.id.tv_Intruccion)
        val tvSigInstruccion=rootView.findViewById<TextView>(R.id.tv_SigInstruccion)
        val ivActual=rootView.findViewById<ImageView>(R.id.iv_actual)
        val ivSig=rootView.findViewById<ImageView>(R.id.iv_Sig)
        val cvInstruccion=rootView.findViewById<CardView>(R.id.cv_Instrucciones)

        val tvZona = rootView.findViewById<TextView>(R.id.tv_Zona)
        val tvContenedores = rootView.findViewById<TextView>(R.id.tv_Contenedores)
        val tvDistancia = rootView.findViewById<TextView>(R.id.tv_Distancia)
        val cvResumen = rootView.findViewById<CardView>(R.id.cv_Resumen)
        val btnIniciar=rootView.findViewById<Button>(R.id.btn_Iniciar)

        val cvRecoleccion = rootView.findViewById<CardView>(R.id.cv_Recoleccion)
        val tvTituloRecoleccion = rootView.findViewById<TextView>(R.id.tv_TituloRecoleccion)
        val tvFillingLevel = rootView.findViewById<TextView>(R.id.tv_Llenado)
        val tvWarning = rootView.findViewById<TextView>(R.id.tv_Warning)
        val btnRecolectar = rootView.findViewById<Button>(R.id.btn_Recolectar)
        val btnFinalizar=rootView.findViewById<Button>(R.id.btn_FinalizarViaje)



        datosRuta.datosruta.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            datosRuta.dibujarMapa(mMap)
            datosRuta.cargarMarkers(mMap)
            datosRuta.obtenerZona("zona:10",contexto)
            tvZona.text="10"
            tvContenedores.text=datosRuta.ubicacionContenedores.size.toString()
            tvDistancia.text="${(datosRuta.kmTotales/1000).toDouble().format(2)}Km"
            cvResumen.visibility=View.VISIBLE
        })

        datosRuta.instruccion.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            tvInstruccion.text=Html.fromHtml(it.instruccion)
            it.seleccionarImagen(ivActual,contexto)

            val cameraPosition = CameraPosition.Builder()
                .target(it.inicio)
                .zoom(90f)
                .bearing(it.calculoInclinacion().toFloat())
                .tilt(80f)
                .build()
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
        })
        datosRuta.SigInstruccion.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            tvSigInstruccion.text=Html.fromHtml(it.instruccion)
            it.seleccionarImagen(ivSig,contexto)
        })


        datosRuta.vehiculoJSON.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            var m3contenedor=(datosRuta.contenedorActual.fillingLevel*0.76)/100
            tvTituloRecoleccion.text=datosRuta.contenedorActual.id
            tvFillingLevel.text = "${(datosRuta.contenedorActual.fillingLevel*100).toDouble().format(2)}%"
            if(datosRuta.tolvaFillLevel>0.9){
                tvWarning.text="Tolva llena! compactar antes de recolectar"
            }
            if(datosRuta.fillLevel+m3contenedor>=0.99){
                tvWarning.text="Vehiculo lleno, clickea en finalizar"
                btnRecolectar.isEnabled=false
            }
            cvRecoleccion.visibility=View.VISIBLE
        })
        btnRecolectar.setOnClickListener {
            var m3contenedor=(datosRuta.contenedorActual.fillingLevel*0.76)
            var porcentaje = ((m3contenedor*100)/20)/100
            var sum = datosRuta.fillLevel+porcentaje
            datosRuta.actualizarVehiculo(sum,contexto)
            datosRuta.actualizarContenedor(datosRuta.contenedorActual.id,contexto)
            cvRecoleccion.visibility=View.INVISIBLE
            tvWarning.text=""
            pausa=false
        }

        btnFinalizar.setOnClickListener {
            pausa=false
            cvRecoleccion.visibility=View.INVISIBLE
            datosRuta.obtenerRuta("https://recolectar-api.herokuapp.com/api/ruta/finalizarviaje?inicio={%20%22latitude%22:"+inicio.latitude+",%22longitude%22: "+inicio.longitude+"}&fin={%20%22latitude%22:"+fin.latitude+",%22longitude%22: "+fin.longitude+"}",contexto,finalizarViaje)
        }

        btnIniciar.setOnClickListener{
            cvResumen.visibility=View.INVISIBLE
            cvInstruccion.visibility=View.VISIBLE
            ActualizarMapa(contexto)
        }
        return rootView
    }


    private fun updateMap(contexto:Context) {
        datosRuta.obtenerRuta("https://recolectar-api.herokuapp.com/api/ruta/obtenerruta?id=zona:10&inicio={%20%22latitude%22:"+inicio.latitude+",%22longitude%22:"+inicio.longitude+"}&fin={%22latitude%22:"+fin.latitude+",%22longitude%22:"+fin.longitude+"}"
            ,contexto,finalizarViaje)
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

   fun ActualizarMapa(contexto: Context) {
        handler.postDelayed(object : Runnable {
            override fun run() {
                // función a ejecutar
                if (!pausa)
                {
                    var ubicacionActual =datosRuta.coordenadasDecodificadas[auxContador]
                    if(finalizarViaje)
                        if (datosRuta.buscarContenedor(ubicacionActual)) {
                            pausa = true
                            datosRuta.obtenerVehiculo(datosRuta.idVehiculo,contexto)
                        }
                    if (datosRuta.auxInstruccion.estoyCerca(ubicacionActual))
                        datosRuta.actualizarInstrucciones()
                    mMap.animateCamera(CameraUpdateFactory.newLatLng(ubicacionActual))
                    auxContador++
                }
                handler.postDelayed(this, 1500)
            }
        }, 3000)
    }
    fun Double.format(digits: Any) = "%.${digits}f".format(this)
    /*private fun obtenerUbicacionActual(contexto : Context){
        if(ActivityCompat.checkSelfPermission(contexto,android.Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(contexto as Activity, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),LOCATION_PERMISSION_REQUEST_CODE)
            return
        }
        fusedLocationProviderClient.lastLocation.addOnSuccessListener {location->
            if (location!=null){
                ultimaUbicacion=location
                val ubicacionActual=LatLng(location.latitude,location.longitude)
                //UtilidadesMaps.crearRuta(contexto,ubicacionActual,ubicacionActual)
            }
        }
    }*/
}

