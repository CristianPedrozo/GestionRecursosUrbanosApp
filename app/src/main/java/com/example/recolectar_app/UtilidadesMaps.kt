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
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.JsonRequest
import com.android.volley.toolbox.Volley
import com.example.recolectar_app.Objetos.Contenedor.Contenedor
import com.example.recolectar_app.Objetos.Instruccion
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList

object UtilidadesMaps {
    var routes: MutableList<MutableList<HashMap<String, String>>> = ArrayList() //guardo todos los puntos polyLine desencriptados
    var contenedores: MutableList<Contenedor> = ArrayList() //guardo todos los contenedores asignados al cliente
    var coordenadaRutas: MutableList<LatLng> = ArrayList() //guardo todas las coordenadas que se deber recolectar
    var instrucciones : MutableList<Instruccion> = ArrayList() //guardo todas las intrucciones para el viaje
    var lineOptions =PolylineOptions() //guardo todas las opciones para trajar la polyline en el mapa
    private var KEY_API="AIzaSyBdkQFmnElXImn5Po8QhlW2A4e8NZq3Vyw"
    private var URL_API="https://maps.googleapis.com/maps/api/directions/json?key="

    fun crearRuta( contexto:Context,inicio:LatLng,fin:LatLng) {
        obtenerContenedores()
        obtenerCoordenadasRuta()
        val url = crearUrlApi(inicio,fin)
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null, { response ->
            parsearRequest(response)
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
    private fun decodificarRuta (){
        var center: LatLng? = null
        // Agregamos todos los puntos en la ruta al objeto LineOptions
        for (i in 0 until routes.size) {
            val rr= routes.size
            // Obteniendo el detalle de la ruta
            val path: List<HashMap<String, String>> = routes[i]
            // Obteniendo todos los puntos y/o coordenadas de la ruta
            for (j in path.indices) {
                val point = path[j]
                val lat = point["lat"]!!.toDouble()
                val lng = point["lng"]!!.toDouble()
                val position = LatLng(lat, lng)
                if (center == null) {
                    //Obtengo la 1ra coordenada para centrar el mapa en la misma.
                    center = LatLng(lat, lng)
                }
                lineOptions.add(position)//agrergamos cada punto al lineOptions
            }
            //Definimos el grosor de las Polilíneas
            lineOptions.width(20f)
            //Definimos el color de la Polilíneas
            lineOptions.color(Color.BLACK)
        }
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

        for (i in 0 until listaContenedores.size){
            var con: Contenedor= Contenedor()
            con.location.value.coordinates.add(listaContenedores[i].latitude.toDouble())
            con.location.value.coordinates.add(listaContenedores[i].longitude.toDouble())
            contenedores.add(con)
        }
    }
    fun obtenerCoordenadasRuta(){
        for (i in 0 until contenedores.size){
            coordenadaRutas.add(LatLng(contenedores[i].location.value.coordinates[0],contenedores[i].location.value.coordinates[1]))
        }
    }
    private fun parsearRequest (response:JSONObject){
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
                        var polyline = ""
                        polyline = ((jSteps[k] as JSONObject)["polyline"] as JSONObject)["points"] as String
                        val list = decodePoly(polyline)
                        for (l in list.indices) {
                            val hm = HashMap<String, String>()
                            hm["lat"] = list[l].latitude.toString()
                            hm["lng"] = list[l].longitude.toString()
                            path.add(hm)
                        }
                    }
                }
                print(path)
                routes.add(path)
                decodificarRuta()
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        } catch (e: Exception) {
        }
    }
    private fun crearUrlApi(inicio: LatLng,fin: LatLng):String{
        var coordenada=""
        for (i in 0 until coordenadaRutas.size){
            coordenada = coordenada + "|" + coordenadaRutas[i].latitude.toString()+","+coordenadaRutas[i].longitude.toString()
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
    fun actualizarIntruccion(t1:TextView,t2:TextView,iv_actual:ImageView,iv_sig:ImageView,ubicacionActual:LatLng,contexto:Context){
        if (instrucciones[0].estoyCerca(ubicacionActual)) {
            t1.text= Html.fromHtml(UtilidadesMaps.instrucciones[1].instruccion)
            t2.text= Html.fromHtml(UtilidadesMaps.instrucciones[2].instruccion)
            seleccionarImagen(instrucciones[1].accion,iv_actual,contexto)
            seleccionarImagen(instrucciones[2].accion,iv_sig,contexto)
            instrucciones.removeAt(0)
        }
        if (t1.text==""&&t2.text==""){
            t1.text= Html.fromHtml(UtilidadesMaps.instrucciones[0].instruccion)
            t2.text= Html.fromHtml(UtilidadesMaps.instrucciones[1].instruccion)
            seleccionarImagen(instrucciones[0].accion,iv_actual,contexto)
            seleccionarImagen(instrucciones[1].accion,iv_sig,contexto)
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
