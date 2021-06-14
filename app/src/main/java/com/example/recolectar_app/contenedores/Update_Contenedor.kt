package com.example.recolectar_app.contenedores

import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.navigation.findNavController
import com.example.recolectar_app.PatchContenedorObject
import com.example.recolectar_app.R
import com.example.recolectar_app.RequestHandler
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import com.google.gson.Gson
import org.json.JSONObject


class Update_Contenedor : Fragment() {
    private val TAG = "Update Contenedor"
    private var url = "http://46.17.108.122:1026/v2/op/update"
    private lateinit var v: View
    private lateinit var contenedor: Contenedor
    lateinit var thiscontext : Context
    lateinit var tiet_id : TextView
    lateinit var tiet_latitud : TextInputEditText
    lateinit var tiet_longitud : TextInputEditText
    lateinit var tiet_estado : TextInputEditText
    lateinit var tiet_ruta : TextInputEditText
    lateinit var tiet_camion : TextInputEditText
    lateinit var tiet_zona : TextInputEditText
    lateinit var tiet_temp : TextInputEditText
    lateinit var tiet_ultima_visita : TextInputEditText
    lateinit var tiet_proxima_visita : TextInputEditText
    lateinit var tiet_llenado : TextInputEditText
    lateinit var btn_edit : FloatingActionButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    @RequiresApi(Build.VERSION_CODES.N_MR1)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        v= inflater.inflate(R.layout.fragment_update_contenedor, container, false)
        if (container != null) {
            thiscontext = container.context
        };
        val requestHandler = RequestHandler.getInstance(thiscontext)
        val args = arguments?.let { ContenedorDetalleArgs.fromBundle(it) }
        contenedor = args?.contenedor!!
        tiet_id = v.findViewById(R.id.edittext_contenedor_id)
        tiet_id.text = contenedor.id!!.split(":")[1]
        tiet_camion = v.findViewById(R.id.edittext_contenedor_camion)
//        tiet_camion.hint = contenedor.refVehicle?.value
        tiet_estado = v.findViewById(R.id.edittext_contenedor_estado)
//        tiet_estado.hint = contenedor.status.value
        tiet_latitud = v.findViewById(R.id.edittext_contenedor_latitud)
//        tiet_latitud.hint = contenedor.location.value.coordinates[0].toString()
        tiet_longitud = v.findViewById(R.id.edittext_contenedor_longitud)
//        tiet_longitud.hint = contenedor.location.value.coordinates[1].toString()
        tiet_ruta = v.findViewById(R.id.edittext_contenedor_ruta)
//        tiet_ruta.hint = contenedor.refRuta?.value
        tiet_zona = v.findViewById(R.id.edittext_contenedor_zona)
//        tiet_zona.hint = contenedor.refZona?.value
        tiet_temp = v.findViewById(R.id.edittext_contenedor_temperatura)
//        tiet_temp.hint = contenedor.temperature?.value.toString()
        tiet_llenado = v.findViewById(R.id.edittext_contenedor_llenado)
//        tiet_llenado.hint = "${contenedor.fillingLevel.value.toString().split(".")[1]}%"
        tiet_ultima_visita = v.findViewById(R.id.edittext_contenedor_ultima_visita)
//        tiet_ultima_visita.hint = contenedor.dateLastEmptying?.value
        tiet_proxima_visita = v.findViewById(R.id.edittext_contenedor_proxima_visita)
//        tiet_proxima_visita.hint = contenedor.nextActuationDeadline?.value

        btn_edit = v.findViewById(R.id.boton_confirmar_editar_contenedor)
        btn_edit.setOnClickListener(){
            editContenedor(requestHandler)
            val action = Update_ContenedorDirections.actionUpdateContenedorToContenedores()
            v.findNavController().navigate(action)
        }
        return v
    }


    private fun editContenedor(requestHandler: RequestHandler) {
        val gson = Gson()
        val contEdit = Contenedor(contenedor.id!!.split(":")[1])
        if(tiet_estado.text.toString() != contenedor.status.value && tiet_estado.text.toString() != "")
            Toast.makeText(thiscontext, "${tiet_estado.text}", Toast.LENGTH_SHORT).show()
            contEdit.setStatus(tiet_estado.text.toString())
//        if(tiet_camion.text.toString() != contenedor.refVehicle?.value?.split(":")?.get(1) ?: "")
//            contEdit.setRefVehicle(tiet_camion.text.toString())
        if(tiet_latitud.text.toString() != "" &&
            tiet_longitud.text.toString() != ""){
            val latlong : MutableList<Double> = arrayListOf()
            latlong.add(tiet_latitud.text.toString().toDouble())
            latlong.add(tiet_longitud.text.toString().toDouble())
            contEdit.setLocation(latlong)
        }
        if(tiet_llenado.text.toString() != contenedor.fillingLevel.value.toString() && tiet_llenado.text.toString() != "")
            contEdit.setFillingLevel(tiet_llenado.text.toString().toDouble())
//        if(tiet_proxima_visita.text.toString() != contenedor.nextActuationDeadline?.value ?: "")
//            contEdit.setStatus(tiet_proxima_visita.text.toString())
//        if(tiet_ultima_visita.text.toString() != contenedor.dateLastEmptying?.value ?: "")
//            contEdit.setStatus(tiet_ultima_visita.text.toString())
        if(tiet_temp.text.toString() != contenedor.temperature?.value.toString() && tiet_temp.text.toString() != "")
            contEdit.setTemperature(tiet_temp.text.toString().toDouble())
//        if(tiet_zona.text.toString() != contenedor.refZona?.value?.split(":")?.get(1) ?: "")
//            contEdit.setRefZona(tiet_zona.text.toString())
//        if(tiet_ruta.text.toString() != contenedor.refRuta?.value?.split(":")?.get(1) ?: "")
//            contEdit.setRefRuta(tiet_zona.text.toString())
        val patchObject = PatchContenedorObject()
        patchObject.addEntitie(contEdit)
        val jsonPatchObject = gson.toJson(patchObject)
        val jsonObject = JSONObject(jsonPatchObject)
        Toast.makeText(thiscontext, "$jsonObject", Toast.LENGTH_SHORT).show()
        requestHandler.patchRequest(url,jsonObject,{},{})
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Update_Contenedor().apply {
                arguments = Bundle().apply {

                }
            }
    }
}