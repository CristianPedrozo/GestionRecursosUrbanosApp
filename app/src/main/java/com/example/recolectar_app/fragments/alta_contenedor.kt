package com.example.recolectar_app.fragments
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.TextView
import android.widget.Toast
import com.example.recolectar_app.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.util.regex.Pattern

class alta_contenedor : Fragment() {
    lateinit var v:View
    lateinit var codigo:TextInputLayout
    lateinit var latitud: TextInputLayout
    lateinit var longitud: TextInputLayout
    lateinit var autoCompleteTextView: AutoCompleteTextView
    lateinit var autoCompleteTextView_tipo: AutoCompleteTextView
    lateinit var botonCrear: FloatingActionButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v= inflater.inflate(R.layout.fragment_alta_contenedor, container, false)
        //Carga Combo Estados
        val estados = resources.getStringArray(R.array.estados_contenedor)
        val arrayAdapter = ArrayAdapter(requireContext(),R.layout.combo_formulario,estados)
        autoCompleteTextView = v.findViewById(R.id.autoCompleteTextView)
        autoCompleteTextView.setAdapter(arrayAdapter)

        //Carga Combo Tipo
        val tipos = resources.getStringArray(R.array.tipos_residuos)
        val arrayAdapterTipo = ArrayAdapter(requireContext(),R.layout.combo_formulario,tipos)
        autoCompleteTextView_tipo = v.findViewById(R.id.autoCompleteTextView_tipo)
        autoCompleteTextView_tipo.setAdapter(arrayAdapterTipo)

        //Validar campos para el alta de un contenedor
        botonCrear=v.findViewById(R.id.boton_agregar)
        botonCrear.setOnClickListener{validarCampos()}

        return v
    }

    private fun validarCampos(){
        val result = arrayOf(validarCodigo(),validarLatitud(),validarLongitud())
        if(false in result){
            return
        }
    }

    private fun validarCodigo():Boolean {
        codigo = v.findViewById(R.id.editText_Codigo)
        return if (!codigo.toString().isEmpty()) {
            codigo.error = "El campo es requerido"
            false
        }else{
            true
        }
    }

    private fun validarLatitud():Boolean{
        latitud = v.findViewById(R.id.editText_Latitud)
        val latitudRegex = Pattern.compile("/^(-?[1-8]?d(?:.d{1,18})?|90(?:.0{1,18})?)\$/")

        return if (!latitud.toString().isEmpty()) {
            latitud.error = "El campo es requerido"
            false
        }else if(!latitudRegex.matcher(latitud.toString()).matches()){
            latitud.error = "Formato longitud incorrecta"
            false
        }else{
            true
        }
    }

    private fun validarLongitud():Boolean{
        longitud = v.findViewById(R.id.editText_Longitud)
        val longitudRegex = Pattern.compile("/^(-?(?:1[0-7]|[1-9])?d(?:.d{1,18})?|180(?:.0{1,18})?)\$/")

        return if (!longitud.toString().isEmpty()) {
            longitud.error = "El campo es requerido"
            false
        }else if(!longitudRegex.matcher(longitud.toString()).matches()){
            longitud.error = "Formato longitud incorrecta"
            false
        }else{
            true
        }
    }
}