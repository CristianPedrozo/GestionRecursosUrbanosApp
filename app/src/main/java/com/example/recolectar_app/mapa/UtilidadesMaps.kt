package com.example.recolectar_app.mapa

import android.content.Context
import android.graphics.Color
import android.location.Location
import android.text.Html
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.recolectar_app.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.google.gson.Gson
import org.json.JSONObject
import java.util.*
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin


object UtilidadesMaps {
    lateinit var ruta:JSONObject
    var coordenadasDecodificadas: MutableList<LatLng> = ArrayList()
    var instrucciones : MutableList<Instruccion> = ArrayList()
    var gson:Gson=Gson()


    fun obtenerRuta(URL:String,contexto:Context){
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, URL, null, { response ->
            ruta=response
            obtenerInstrucciones()
            Log.d("asd",ruta.toString())
        }, { error ->
            Toast.makeText(contexto, "No se puede conectar $error", Toast.LENGTH_LONG).show()
            println(error.toString())
            Log.d("ERROR: ", error.toString())
        }
        )
        jsonObjectRequest.retryPolicy = DefaultRetryPolicy(
            10000,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )
        val request = Volley.newRequestQueue(contexto)
        request.add(jsonObjectRequest)
    }
    fun obtenerInstrucciones(){
        var instruccionesAux = ruta.getJSONArray("instrucciones")
        for (i in 0 until instruccionesAux.length()){
            var auxInstruccion:Instruccion= Instruccion()
            var aux = instruccionesAux[i] as JSONObject
            var latInicio = (aux["inicio"] as JSONObject)["latitude"] as Double
            var lngInicio = (aux["inicio"] as JSONObject)["longitude"] as Double
            auxInstruccion.inicio = LatLng(latInicio,lngInicio)

            var latFin = (aux["fin"] as JSONObject)["latitude"] as Double
            var lngFin = (aux["fin"] as JSONObject)["longitude"] as Double
            auxInstruccion.fin = LatLng(latFin,lngFin)

            auxInstruccion.accion = aux["accion"] as String
            auxInstruccion.instruccion = aux["intruccion"] as String

            auxInstruccion.inclinacionMapa=auxInstruccion.calculoInclinacion()
            var tt1 = instrucciones
            instrucciones.add(auxInstruccion)
        }

    }
    fun dibujarMapa(mMap:GoogleMap){
        var lineOptions =PolylineOptions()
        var coordenadas = ruta.getJSONArray("coordenadaDecodificada")
        for (i in 0 until coordenadas.length()){
            var lat = (coordenadas[i] as JSONObject)["latitude"] as Double
            var lng = (coordenadas[i] as JSONObject)["longitude"] as Double
            var locacion = LatLng(lat,lng)
            lineOptions.add(locacion)
            coordenadasDecodificadas.add(locacion)
        }
        lineOptions.width(20f)
        lineOptions.color(Color.BLACK)
        mMap.addPolyline(lineOptions)
    }
    fun cargarMarkers(mMap:GoogleMap){
        var ubicacionContenedores = ruta.getJSONArray("ubicacionesContenedores")
        for (i in 0 until ubicacionContenedores.length()){
            var lat = (ubicacionContenedores[i] as JSONObject)["latitude"] as Double
            var lng = (ubicacionContenedores[i] as JSONObject)["longitud"] as Double
            mMap.addMarker(MarkerOptions().position(LatLng(lat,lng)))
        }
    }
    fun actualizarIntruccion(t1: TextView, t2: TextView, iv_actual: ImageView, iv_sig: ImageView, ubicacionActual:LatLng, contexto:Context, mMap: GoogleMap){
        if (instrucciones[0].estoyCerca(ubicacionActual)) {
            var tt = instrucciones
            t1.text= Html.fromHtml(instrucciones[1].instruccion)
            t2.text= Html.fromHtml(instrucciones[2].instruccion)
            seleccionarImagen(instrucciones[1].accion,iv_actual,contexto)
            seleccionarImagen(instrucciones[2].accion,iv_sig,contexto)
            val cameraPosition = CameraPosition.Builder()
                .target(instrucciones[1].inicio)
                .zoom(45f) // Sets the zoom
                .bearing(instrucciones[1].inclinacionMapa.toFloat())
                .tilt(85f) // Sets the tilt of the camera to 30 degrees
                .build()
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
            instrucciones.removeAt(0)
        }else{
            if (t1.text==""&&t2.text==""){
                t1.text= Html.fromHtml(instrucciones[0].instruccion)
                t2.text= Html.fromHtml(instrucciones[1].instruccion)
                seleccionarImagen(instrucciones[0].accion,iv_actual,contexto)
                seleccionarImagen(instrucciones[1].accion,iv_sig,contexto)
                val cameraPosition = CameraPosition.Builder()
                    .target(instrucciones[0].inicio) // Sets the center of the map to Mountain View
                    .zoom(45f) // Sets the zoom
                    .bearing(instrucciones[0].inclinacionMapa.toFloat()) // Sets the orientation of the camera to east
                    .tilt(85f) // Sets the tilt of the camera to 30 degrees
                    .build() // Creates a CameraPosition from the builder
                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
            }else{
                mMap.animateCamera(CameraUpdateFactory.newLatLng(ubicacionActual))
            }
        }

    }
    private fun seleccionarImagen(accion:String, iv: ImageView, contexto: Context){
        when (accion) {
            "turn-left" -> iv.setImageDrawable(
                ContextCompat.getDrawable(contexto,
                R.drawable.izquierda
            ))
            "turn-right" -> iv.setImageDrawable(
                ContextCompat.getDrawable(contexto,
                R.drawable.derecha
            ))
            else -> { // Note the block
                iv.setImageDrawable(ContextCompat.getDrawable(contexto, R.drawable.derecho))
            }
        }
    }
}
