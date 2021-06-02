package com.example.recolectar_app

import android.content.Context
import android.graphics.Color
import android.text.Html
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.recolectar_app.Objetos.Contenedor.Contenedor
import com.example.recolectar_app.Objetos.Instruccion
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList


object UtilidadesMaps {
    var coordenadasDecodificadas: MutableList<LatLng> = ArrayList() //guardo todas las ubicaciones decodificadas para simular ubicacion
    var contenedores: MutableList<Contenedor> = ArrayList() //guardo todos los contenedores asignados al cliente
    var coordenadasContenedores: MutableList<LatLng> = ArrayList() //guardo todas las coordenadas que se deber recolectar
    var instrucciones : MutableList<Instruccion> = ArrayList() //guardo todas las intrucciones para el viaje
    var lineOptions =PolylineOptions() //guardo todas las opciones para trajar la polyline en el mapa
    private var KEY_API="AIzaSyBdkQFmnElXImn5Po8QhlW2A4e8NZq3Vyw"
    private var URL_API="https://maps.googleapis.com/maps/api/directions/json?key="

    fun crearRuta( contexto:Context,inicio:LatLng,fin:LatLng) {
        obtenerContenedores()
        obtenerCoordenadasContenedores()
        val url = crearUrlApi(inicio,fin)
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null, { response ->
            obtenerCoordenadasRuta(response)
            obtenerInstrucciones(response)
        }, { error ->
            Toast.makeText(contexto, "No se puede conectar $error", Toast.LENGTH_LONG).show()
            println()
            Log.d("ERROR: ", error.toString())
        }
        )
        val request = Volley.newRequestQueue(contexto)
        request.add(jsonObjectRequest)
    }
    private fun decodePoly(encoded: String): List<LatLng> {
        val poly: MutableList<LatLng> = ArrayList()
        var index = 0
        val len = encoded.length
        var lat = 0
        var lng = 0
        while (index < len) {
            var b: Int
            var shift = 0
            var result = 0
            do {
                b = encoded[index++].toInt() - 63
                result = result or (b and 0x1f shl shift)
                shift += 5
            } while (b >= 0x20)
            val dlat = if (result and 1 != 0) (result shr 1).inv() else result shr 1
            lat += dlat
            shift = 0
            result = 0
            do {
                b = encoded[index++].toInt() - 63
                result = result or (b and 0x1f shl shift)
                shift += 5
            } while (b >= 0x20)
            val dlng = if (result and 1 != 0) (result shr 1).inv() else result shr 1
            lng += dlng
            val p = LatLng(lat.toDouble() / 1E5,
                lng.toDouble() / 1E5)
            poly.add(p)
        }
        return poly
    }
    fun obtenerContenedores(){
        //esta funcion consultara todos los contenedores asignados al empleado logeado
        var listaContenedores:MutableList<LatLng> = ArrayList()
        listaContenedores.add(LatLng(-34.592678, -58.411280))
        listaContenedores.add(LatLng(-34.594495, -58.412546))
        listaContenedores.add(LatLng(-34.595071, -58.411865))
        listaContenedores.add(LatLng(-34.593251, -58.409965))
        listaContenedores.add(LatLng(-34.595668, -58.408229))
        listaContenedores.add(LatLng(-34.597581, -58.409176))
        listaContenedores.add(LatLng(-34.598019, -58.411532))
        listaContenedores.add(LatLng(-34.597868, -58.415430))
        listaContenedores.add(LatLng(-34.597076, -58.418840))
        listaContenedores.add(LatLng(-34.592755, -58.419770))

//        for (i in 0 until listaContenedores.size){
//            var con: Contenedor= Contenedor()
//            con.location.value.coordinates.add(listaContenedores[i].latitude.toDouble())
//            con.location.value.coordinates.add(listaContenedores[i].longitude.toDouble())
//            contenedores.add(con)
//        }
    }
    fun obtenerCoordenadasContenedores(){
        for (i in 0 until contenedores.size){
            coordenadasContenedores.add(LatLng(contenedores[i].location.value.coordinates[0],contenedores[i].location.value.coordinates[1]))
        }
    }
    private fun obtenerCoordenadasRuta (response:JSONObject){
        var jRoutes: JSONArray? = null
        var jLegs: JSONArray? = null
        var jSteps: JSONArray? = null
        try {
            jRoutes = response.getJSONArray("routes")
            for (i in 0 until jRoutes.length()) {
                jLegs = (jRoutes[i] as JSONObject).getJSONArray("legs")
                for (j in 0 until jLegs.length()) {
                    jSteps = (jLegs[j] as JSONObject).getJSONArray("steps")
                    for (k in 0 until jSteps.length()) {
                        var polyline = ""
                        polyline = ((jSteps[k] as JSONObject)["polyline"] as JSONObject)["points"] as String
                        val list = decodePoly(polyline)
                        for (l in list.indices) {
                            coordenadasDecodificadas.add(LatLng(list[l].latitude,list[l].longitude))
                        }
                    }
                }
                print(coordenadasDecodificadas)
                lineOptions.addAll(coordenadasDecodificadas)
                lineOptions.width(20f)
                lineOptions.color(Color.BLACK)
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        } catch (e: Exception) {
        }
    }
    private fun crearUrlApi(inicio: LatLng,fin: LatLng):String{
        var coordenada=""
        for (i in 0 until coordenadasContenedores.size){
            coordenada = coordenada + "|" + coordenadasContenedores[i].latitude.toString()+","+coordenadasContenedores[i].longitude.toString()
        }

        var inicioFinCoordenada="&origin="+ inicio.latitude.toString() + "," + inicio.longitude.toString() +
                "&destination="+ fin.latitude.toString() + "," + fin.longitude.toString()

        return ("$URL_API$KEY_API&language=es-419$inicioFinCoordenada&waypoints=optimize:true$coordenada")
    }
    fun agregarMarkers (mMap:GoogleMap){
        for (i in 0 until contenedores.size){
            mMap.addMarker(MarkerOptions().position(LatLng(contenedores[i].location.value.coordinates[0],contenedores[i].location.value.coordinates[1])).title(contenedores[i].id.toString()))
        }
    }
    fun obtenerInstrucciones (response: JSONObject){
        var jRoutes: JSONArray? = null
        var jLegs: JSONArray? = null
        var jSteps: JSONArray? = null
        try {
            jRoutes = response.getJSONArray("routes")
            for (i in 0 until jRoutes.length()) {
                jLegs = (jRoutes[i] as JSONObject).getJSONArray("legs")
                val path: MutableList<HashMap<String, String>> = ArrayList()
                for (j in 0 until jLegs.length()) {
                    jSteps = (jLegs[j] as JSONObject).getJSONArray("steps")
                    for (k in 0 until jSteps.length()) {
                        var latInicio = ((jSteps[k] as JSONObject)["start_location"] as JSONObject)["lat"] as Double
                        var lngInicio = ((jSteps[k] as JSONObject)["start_location"] as JSONObject)["lng"] as Double
                        var inicio = LatLng(latInicio,lngInicio)

                        var latFin = ((jSteps[k] as JSONObject)["end_location"] as JSONObject)["lat"] as Double
                        var lngFin = ((jSteps[k] as JSONObject)["end_location"] as JSONObject)["lng"] as Double
                        var fin = LatLng(latFin,lngFin)

                        var instruccion = ((jSteps[k] as JSONObject)["html_instructions"]) as String
                        var accion=""
                        if(((jSteps[k] as JSONObject).has("maneuver")))
                            accion = (jSteps[k] as JSONObject)["maneuver"] as String
                        var auxInstruccion:Instruccion = Instruccion()

                        auxInstruccion.accion=accion
                        auxInstruccion.instruccion=instruccion
                        auxInstruccion.inicio=inicio
                        auxInstruccion.fin = fin
                        var aux=auxInstruccion.calculoInclinacion()
                        auxInstruccion.inclinacionMapa = aux

                        this.instrucciones.add(auxInstruccion)
                        print(auxInstruccion.toString())
                    }
                }
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        } catch (e: Exception) {
        }
    }
    fun actualizarIntruccion(t1:TextView,t2:TextView,iv_actual:ImageView,iv_sig:ImageView,ubicacionActual:LatLng,contexto:Context,mMap: GoogleMap){
        if (instrucciones[0].estoyCerca(ubicacionActual)) {
            t1.text= Html.fromHtml(UtilidadesMaps.instrucciones[1].instruccion)
            t2.text= Html.fromHtml(UtilidadesMaps.instrucciones[2].instruccion)
            seleccionarImagen(instrucciones[1].accion,iv_actual,contexto)
            seleccionarImagen(instrucciones[2].accion,iv_sig,contexto)
            val cameraPosition = CameraPosition.Builder()
                .target(instrucciones[1].inicio) // Sets the center of the map to Mountain View
                .zoom(45f) // Sets the zoom
                .bearing(instrucciones[1].inclinacionMapa.toFloat()) // Sets the orientation of the camera to east
                .tilt(85f) // Sets the tilt of the camera to 30 degrees
                .build() // Creates a CameraPosition from the builder
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
            instrucciones.removeAt(0)
        }else{
            if (t1.text==""&&t2.text==""){
                t1.text= Html.fromHtml(UtilidadesMaps.instrucciones[0].instruccion)
                t2.text= Html.fromHtml(UtilidadesMaps.instrucciones[1].instruccion)
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
    private fun seleccionarImagen(accion:String,iv:ImageView,contexto: Context){
        when (accion) {
            "turn-left" -> iv.setImageDrawable(ContextCompat.getDrawable(contexto,R.drawable.izquierda))
            "turn-right" -> iv.setImageDrawable(ContextCompat.getDrawable(contexto,R.drawable.derecha))
            else -> { // Note the block
                iv.setImageDrawable(ContextCompat.getDrawable(contexto,R.drawable.derecho))
            }
        }
    }
}
