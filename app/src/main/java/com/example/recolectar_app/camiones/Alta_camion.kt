package com.example.recolectar_app.camiones

import android.content.Context
import android.os.Bundle
import android.text.InputType
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.EditText
import androidx.navigation.findNavController
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.recolectar_app.R
import com.example.recolectar_app.RequestHandler
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.gson.Gson
import org.json.JSONObject
import java.lang.Integer.parseInt

class alta_camion : Fragment() {
    var url = "http://46.17.108.122:1026/v2/entities/"
    lateinit var thiscontext : Context
    lateinit var v:View
    lateinit var autoCompleteTextView: AutoCompleteTextView
    lateinit var autoCompleteTextView_tipo: AutoCompleteTextView
    lateinit var id: TextInputEditText
    lateinit var tipo: TextInputEditText
    lateinit var patente: TextInputEditText
    lateinit var carga: TextInputEditText
    lateinit var estado: String
    lateinit var empleado: TextInputEditText
    lateinit var btn_alta_camion: FloatingActionButton
    lateinit var btn_cancelar: FloatingActionButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (container != null) {
            thiscontext = container.context
        };

        v= inflater.inflate(R.layout.fragment_alta_camion, container, false)

        //Carga Combo Estados
        val estados = resources.getStringArray(R.array.estados_camion)
        val arrayAdapter = ArrayAdapter(requireContext(),R.layout.combo_formulario,estados)
        autoCompleteTextView = v.findViewById(R.id.autoCompleteTextView_estado)
        autoCompleteTextView.setAdapter(arrayAdapter)
/*
        //Carga Combo Tipo
        val tipos = resources.getStringArray(R.array.tipos_residuos)
        val arrayAdapterTipo = ArrayAdapter(requireContext(),R.layout.combo_formulario,tipos)
        autoCompleteTextView_tipo = v.findViewById(R.id.autoCompleteTextView_tipo)
        autoCompleteTextView_tipo.setAdapter(arrayAdapterTipo)

 */

        //Botón Alta Camion
        var requestHandler = RequestHandler.getInstance(thiscontext)

        //Relaciono variables con elementos  del xml
        id= v.findViewById(R.id.editText_Id)
        patente= v.findViewById(R.id.editText_Patente)
        carga = v.findViewById(R.id.editText_Carga)
        autoCompleteTextView.setOnItemClickListener { parent, v, position, id ->
            estado= parent.getItemAtPosition(position).toString()
        }
        Log.d("Id camion", id.toString())

        //Botón para dar de Alta Camion
        btn_alta_camion= v.findViewById(R.id.boton_agregar)
        btn_alta_camion.setOnClickListener(){
            if(validarCampos()){
            addCamion(requestHandler)
            val action=alta_camionDirections.actionAltaCamionToCamiones()
            v.findNavController().navigate(action)}
        }

        //Botón Cancelar
        btn_cancelar = v.findViewById(R.id.boton_cancelar)
        btn_cancelar.setOnClickListener(){
            val action=alta_camionDirections.actionAltaCamionToCamiones()
            v.findNavController().navigate(action)
        }
        return v
    }


    private fun addCamion(requestHandler : RequestHandler) {
        val gson = Gson()

        //Armo el objeto camión para darlo de alta
        val camion = Camion(Camion.CargoWeight(carga.text.toString().toInt()),id.text.toString(),Camion.ServiceStatus(estado),Camion.VehiclePlateIdentifier(patente.text.toString()),Camion.VehicleType("lorry"))

        //camion.setRefEmpleadoValue(empleado.toString())
        val string = gson.toJson(camion)
        Log.d("POST camion", string.toString() )
        val jsonObject = JSONObject(string)
        requestHandler.postRequest(url,{},{},jsonObject)
    }


    override fun onStart() {
        super.onStart()
        var gson = Gson()
        val queue = Volley.newRequestQueue(activity)
        val url_camion=url + id
        /*
        val jsonArrayRequest = JsonArrayRequest(url_camion,
            { response ->
                Log.d("Response Prueba", response.toString())

                val camion : Camion = gson.fromJson(response.getJSONObject(0).toString(),
                    Camion::class.java)
                text_id_camion.setText(camion.id)
                text_patente_camion.setText(camion.vehiclePlateIdentifier.value)
                //text_tipo_camion.setText(camion.vehicleType.value)
                text_carga_camion.setText(camion.cargoWeight.value.toString())
                text_estado_camion.setText(camion.serviceStatus.value)
                //
                //text_camion_tipo.setText(camion.type)
                //text_camion_estado.setText(camion.status.value)
                //text_camion_zona.setText(camion.refZona.value)
                //text_empleado_camion.text= camion.refEmpleado.toString()

            }, {print("prueba error")})
        queue.add(jsonArrayRequest)

 */
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            alta_camion().apply {
                arguments = Bundle().apply {

                }
            }
    }
 //Validación de los Campos del Formulario para dar de Alta el Camión

    private fun validarCampos(): Boolean {
        var resultT = true
        val result = arrayOf(validarId(),validarPatente(),validarCarga())
        if(false in result){
            resultT =false
        }
        return resultT
    }


    private fun validarId():Boolean {
        id = v.findViewById(R.id.editText_Id)
        return if (id.text.toString().isEmpty()) {
            id.error = "El campo es requerido"
            false
        }else{
            true
        }
    }

    private fun validarPatente():Boolean {
        patente = v.findViewById(R.id.editText_Patente)
        return if (patente.text.toString().isEmpty()) {
            patente.error = "El campo es requerido"
            false
        }else{
            true
        }
    }
    private fun validarCarga():Boolean {
        carga = v.findViewById(R.id.editText_Carga)
        return if (carga.text.toString().isEmpty()) {
            carga.error = "El campo es requerido"
            false
        }else{
            true
        }
    }

}