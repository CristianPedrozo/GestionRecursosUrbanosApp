package com.example.recolectar_app

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.JsonRequest
import com.android.volley.toolbox.Volley
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PolylineOptions
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList

object UtilidadesMaps {
    var routes: MutableList<MutableList<HashMap<String, String>>> = ArrayList()
    var coordenadasRuta: ArrayList<LatLng> = ArrayList()
    var lineOptions =PolylineOptions()
    private var KEY_API="&key=AIzaSyBdkQFmnElXImn5Po8QhlW2A4e8NZq3Vyw"
    
    fun webServiceObtenerRuta( contexto:Context,KEY_API:String ) {
        val url = ("https://maps.googleapis.com/maps/api/directions/json?" +
                "origin=-34.592678, -58.411280&destination=-34.592755, -58.419770" +
                "&waypoints=optimize:true|-34.594495, -58.412546|-34.595071, -58.411865|-34.593251, -58.409965|-34.595668, -58.408229|-34.597581, -58.409176|-34.598019, -58.411532|-34.597868, -58.415430|-34.597076, -58.418840" +
                "&key=AIzaSyBdkQFmnElXImn5Po8QhlW2A4e8NZq3Vyw")
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null, Response.Listener { response -> //Este método PARSEA el JSONObject que retorna del API de Rutas de Google devolviendo
            //una lista del lista de HashMap Strings con el listado de Coordenadas de Lat y Long,
            //con la cual se podrá dibujar pollinas que describan la ruta entre 2 puntos.
            //parse(response)
            var jRoutes: JSONArray? = null
            var jLegs: JSONArray? = null
            var jSteps: JSONArray? = null
            try {
                jRoutes = response.getJSONArray("routes")
                /** Traversing all routes  */
                for (i in 0 until jRoutes.length()) {
                    jLegs = (jRoutes[i] as JSONObject).getJSONArray("legs")
                    val path: MutableList<HashMap<String, String>> = ArrayList()
                    /** Traversing all legs  */
                    for (j in 0 until jLegs.length()) {
                        jSteps = (jLegs[j] as JSONObject).getJSONArray("steps")
                        /** Traversing all steps  */
                        /** Traversing all steps  */
                        for (k in 0 until jSteps.length()) {
                            var polyline = ""
                            polyline = ((jSteps[k] as JSONObject)["polyline"] as JSONObject)["points"] as String
                            val list = decodePoly(polyline)
                            /** Traversing all points  */
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
        }, Response.ErrorListener { error ->
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
}
