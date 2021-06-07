package com.example.recolectar_app.contenedores
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.navigation.findNavController
import com.example.recolectar_app.R
import com.example.recolectar_app.RequestHandler
import com.example.recolectar_app.zonas.alta_zonaDirections
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputLayout
import com.google.gson.Gson
import org.json.JSONObject
import java.util.regex.Pattern

class alta_contenedor : Fragment() {
    var url = "http://46.17.108.122:1026/v2/entities/"
    lateinit var thiscontext : Context
    lateinit var v:View
    lateinit var codigo:TextInputLayout
    lateinit var latitud: TextInputLayout
    lateinit var longitud: TextInputLayout
    lateinit var actv_estado: AutoCompleteTextView
    lateinit var estado : String
    lateinit var actv_tipo: AutoCompleteTextView
    lateinit var tipo : String
    lateinit var botonCrear: FloatingActionButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v= inflater.inflate(R.layout.fragment_alta_contenedor, container, false)
        if (container != null) {
            thiscontext = container.context
        };
        var requestHandler = RequestHandler.getInstance(thiscontext)
        //Carga Combo Estados
        val estados = resources.getStringArray(R.array.estados_contenedor)
        val arrayAdapterEstado = ArrayAdapter(requireContext(),R.layout.combo_formulario,estados)
        actv_estado = v.findViewById(R.id.autoCompleteTextView)
        actv_estado.setAdapter(arrayAdapterEstado)
        estado = arrayAdapterEstado.getItem(0).toString()
        actv_estado.setOnItemClickListener { parent, v, position, id ->
            estado= parent.getItemAtPosition(position).toString()
        }
        //Carga Combo Tipo
        val tipos = resources.getStringArray(R.array.tipos_residuos)
        val arrayAdapterTipo = ArrayAdapter(requireContext(),R.layout.combo_formulario,tipos)
        actv_tipo = v.findViewById(R.id.autoCompleteTextView_tipo)
        actv_tipo.setAdapter(arrayAdapterTipo)
        tipo = arrayAdapterTipo.getItem(0).toString()
        actv_tipo.setOnItemClickListener { parent, v, position, id ->
            tipo= parent.getItemAtPosition(position).toString()
        }

        //Validar campos para el alta de un contenedor
        botonCrear=v.findViewById(R.id.boton_agregar)
        botonCrear.setOnClickListener{
            validarCampos()
            addContenedor(requestHandler)
            val action = alta_contenedorDirections.actionAltaContenedorToContenedores()
            v.findNavController().navigate(action)
        }
        return v
    }

    private fun addContenedor(requestHandler : RequestHandler) {
        val gson = Gson()
        val contenedor = Contenedor(codigo.editText?.text.toString())
        var latlong : MutableList<Double> = arrayListOf()
        latlong.add(latitud.editText?.text.toString().toDouble())
        latlong.add(longitud.editText?.text.toString().toDouble())
        contenedor.setLocation(latlong)
        contenedor.setStatus(estado)
        Toast.makeText(thiscontext, "$contenedor", Toast.LENGTH_LONG).show()
        val string = gson.toJson(contenedor)
        val jsonObject = JSONObject(string)
        Toast.makeText(thiscontext, "$jsonObject", Toast.LENGTH_LONG).show()
        requestHandler.postRequest(url,{},{},jsonObject)
    }

    //Validación de los campos del Formulario ALTA CONTENEDOR
    private fun validarCampos(){
        val result = arrayOf(validarCodigo(),validarLatitud(),validarLongitud())
        if(false in result){
            return
        }
    }

    private fun validarCodigo():Boolean {
        codigo = v.findViewById(R.id.editText_Codigo)
        return if (codigo.editText.toString().isEmpty()) {
            codigo.error = "El campo es requerido"
            false
        }else{
            true
        }
    }

    private fun validarLatitud():Boolean{
        latitud = v.findViewById(R.id.editText_Latitud)
        val latitudRegex = Pattern.compile("^[-+]?([1-8]?\\d(\\.\\d+)?|90(\\.0+)?)")
        return if (latitud.editText?.text.toString().isEmpty()) {
            latitud.error = "El campo es requerido"
            false
        }else if(!latitudRegex.matcher(latitud.editText?.text.toString()).matches()){
            latitud.error = "Formato longitud incorrecta"
            false
        }else{
            true
        }
    }

    private fun validarLongitud():Boolean{
        longitud = v.findViewById(R.id.editText_Longitud)
        val longitudRegex = Pattern.compile("^[-+]?(180(\\.0+)?|((1[0-7]\\d)|([1-9]?\\d))(\\.\\d+)?)")

        return if (longitud.editText?.text.toString().isEmpty()) {
            longitud.error = "El campo es requerido"
            false
        }else if(!longitudRegex.matcher(longitud.editText?.text.toString()).matches()){
            longitud.error = "Formato longitud incorrecta"
            false
        }else{
            true
        }
    }
}