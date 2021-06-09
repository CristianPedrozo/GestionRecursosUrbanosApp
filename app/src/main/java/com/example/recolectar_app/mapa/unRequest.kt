package com.example.recolectar_app.mapa

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.gms.maps.model.LatLng
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject


class unRequest() {
    var URL=""
    var puntos: MutableList<PuntoDistancia> = ArrayList()
    var ptoMasCercano:PuntoDistancia=PuntoDistancia()
    var indexPtoMasCercano=0

    fun obtenerURLApi (inicio:LatLng){
        var auxURL=""
        for (i in 0 until puntos.size){
            auxURL=auxURL+puntos[i].punto.latitude+","+puntos[i].punto.longitude+"|"
        }
        auxURL ="""https://maps.googleapis.com/maps/api/distancematrix/json?units=metric&origins=${inicio.latitude},${inicio.longitude}&destinations=${auxURL}&key=AIzaSyBdkQFmnElXImn5Po8QhlW2A4e8NZq3Vyw"""
        URL=auxURL
    }
    fun obtenerDistancias(contexto: Context){
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, URL, null, { response ->
            parsearRequestGuardarDistancias(response)
            obtenerPtoMasCercano()
        }, { error ->
            Toast.makeText(contexto, "No se puede conectar $error", Toast.LENGTH_LONG).show()
            println()
            Log.d("ERROR: ", error.toString())
        }
        )
        val request = Volley.newRequestQueue(contexto)
        request.add(jsonObjectRequest)
    }
    private fun parsearRequestGuardarDistancias (response: JSONObject){
        var jRows: JSONArray? = null
        var jElements: JSONArray? = null
        var jDistance = 0
        try {
            jRows = response.getJSONArray("rows")
            for (i in 0 until jRows.length()) {
                jElements = (jRows[i] as JSONObject).getJSONArray("elements")
                for (j in 0 until jElements.length()) {
                    jDistance = ((jElements[j] as JSONObject)["distance"] as JSONObject)["value"] as Int
                    puntos[j].distancia=jDistance
                }
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        } catch (e: Exception) {
        }
    }
    fun obtenerPtoMasCercano (){
        var auxPtoMasCercano:PuntoDistancia=puntos[0]
        var auxIndexPtoMasCercano=0
        for(i in 0 until puntos.size){
            if(puntos[i].distancia<auxPtoMasCercano.distancia){
                auxPtoMasCercano=puntos[i]
                auxIndexPtoMasCercano=i
            }
        }
        ptoMasCercano=auxPtoMasCercano
        indexPtoMasCercano=auxIndexPtoMasCercano

        if(UtilidadesOrdenamiento.controlRequest== UtilidadesOrdenamiento.arrayDeRequest.size-1){
            UtilidadesOrdenamiento.obtenerPuntoMasCercano()
        }
        UtilidadesOrdenamiento.controlRequest++
    }
}