package com.example.recolectar_app.mapa.models

import android.content.Context
import android.graphics.Color
import android.location.Location
import android.text.Html
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.recolectar_app.R
import com.example.recolectar_app.data.CamionRepository
import com.example.recolectar_app.databinding.FragmentAdministradorUsuariosBinding
import com.example.recolectar_app.domain.camionesRequests.GetCamionesByZonaUseCase
import com.example.recolectar_app.mapa.Instruccion
import com.example.recolectar_app.model.camion.CamionModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.google.gson.Gson
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.util.*


class datosRuta:ViewModel() {
    var datosruta= MutableLiveData<JSONObject> ()
    var ruta = JSONObject()
    var ubicacionContenedores:MutableList<LatLng> = ArrayList()
    var coordenadasDecodificadas: MutableList<LatLng> = ArrayList()
    var instrucciones : MutableList<Instruccion> = ArrayList()
    var instruccion=MutableLiveData<Instruccion>()
    var SigInstruccion=MutableLiveData<Instruccion>()
    var contenedorActual = MutableLiveData<ContenedorDato>()
    lateinit var auxInstruccion:Instruccion
    lateinit var auxSigInstruccion:Instruccion
    var kmTotales = 0
    var controlRecoleccion=MutableLiveData<Boolean>()
    val getCamionesByZonaUseCase = GetCamionesByZonaUseCase()
    val _camiones = MutableLiveData<List<CamionModel>>()


    fun obtenerRuta(URL:String,contexto:Context){
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, URL, null, { response ->
            datosruta.postValue(response)
            ruta=response
            instrucciones=obtenerInstrucciones(response)
            ubicacionContenedores=obtenerUbicacionContenedores(response)
            kmTotales= response["kmTotales"] as Int
            cargarInstrucciones()
            Log.d("asd",ruta.toString())
        }, { error ->
            Toast.makeText(contexto, "No se puede conectar $error", Toast.LENGTH_LONG).show()
            println(error.toString())
            Log.d("ERROR: ", error.toString())
        }
        )
        jsonObjectRequest.retryPolicy = DefaultRetryPolicy(
            20000,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )
        val request = Volley.newRequestQueue(contexto)
        request.add(jsonObjectRequest)
    }
    fun cargarInstrucciones (){
        instruccion.postValue(instrucciones[0])
        auxInstruccion=instrucciones[0]
        SigInstruccion.postValue(instrucciones[1])
        auxSigInstruccion=instrucciones[1]
        instrucciones.removeAt(0)
        instrucciones.removeAt(0)
    }
    fun actualizarInstrucciones(){
        instruccion.postValue(auxSigInstruccion)
        auxInstruccion = auxSigInstruccion
        SigInstruccion.postValue(instrucciones[0])
        auxSigInstruccion=instrucciones[0]
        instrucciones.removeAt(0)
    }
    fun obtenerInstrucciones(response:JSONObject): MutableList<Instruccion> {
        var instrucciones:MutableList<Instruccion> = ArrayList()
        var instruccionesAux = response.getJSONArray("instrucciones")
        for (i in 0 until instruccionesAux.length()){
            var auxInstruccion: Instruccion = Instruccion()
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
        return instrucciones
    }
    fun obtenerUbicacionContenedores(response: JSONObject): MutableList<LatLng> {
        var ubicaciones:MutableList<LatLng> = ArrayList()
        var contenedorUbicacion = response.getJSONArray("ubicacionesContenedores")
        for (i in 0 until contenedorUbicacion.length()){
            var ubicacionAux = (contenedorUbicacion[i] as JSONObject)
            var latitudLongitud = LatLng(ubicacionAux["latitude"] as Double,ubicacionAux["longitude"] as Double)
            ubicaciones.add(latitudLongitud)
        }
        return ubicaciones
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
        for (i in 0 until ubicacionContenedores.size){
            mMap.addMarker(MarkerOptions().position(ubicacionContenedores[i]))
        }
    }

    fun buscarContenedor(ubicacion:LatLng): Boolean {
        var retorno=false
        var cont = 0
        var contenedorJson=ruta.getJSONArray("contenedores")
        while(!retorno&&cont != ubicacionContenedores.size&&ubicacionContenedores.size!=0){
            if(estoyCerca(ubicacion,ubicacionContenedores[cont])){
                retorno=true
                var auxContenedorActual=contenedorJson[cont] as JSONObject
                var contenedorDato:ContenedorDato= ContenedorDato(auxContenedorActual["id"] as String,
                    auxContenedorActual["fillingLevel"] as Double)
                contenedorActual.postValue(contenedorDato)
                ubicacionContenedores.removeAt(cont)
            }
            cont++
        }
        return retorno
    }
    fun buscarCamionByZona(zonaId : String){
        viewModelScope.launch {
            val camionesByZona = getCamionesByZonaUseCase(zonaId)
            _camiones.postValue(camionesByZona)
        }
    }
    fun estoyCerca(ubicacion:LatLng,fin:LatLng): Boolean {
        val locationA = Location("punto A")
        val locationB = Location("punto B")
        locationA.latitude=fin.latitude
        locationA.longitude=fin.longitude

        locationB.latitude=ubicacion.latitude
        locationB.longitude=ubicacion.longitude

        if(locationA.distanceTo(locationB)<10.0){
            return true
        }
        return false
    }
}
