package com.example.recolectar_app.contenedores

import android.content.Context
import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.autofill.AutofillValue
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.findNavController
import com.example.recolectar_app.PatchContenedorObject
import com.example.recolectar_app.R
import com.example.recolectar_app.RequestHandler
import com.example.recolectar_app.camiones.CamionDetalleArgs
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import com.google.gson.Gson
import org.json.JSONObject


class Update_Contenedor : Fragment() {
    private val TAG = "Update Contenedor"
    private var url = "http://46.17.108.122:1026/v2/op/update"
    private lateinit var v: View
    lateinit var thiscontext : Context
    lateinit var id : String
    lateinit var estado : String
    lateinit var tiet_id : TextView
    lateinit var tiet_latitud : TextInputEditText
    lateinit var tiet_longitud : TextInputEditText
    lateinit var tiet_estado : TextInputEditText
    lateinit var tiet_ruta : TextInputEditText
    lateinit var tiet_camion : TextInputEditText
    lateinit var tiet_zona : TextInputEditText
    lateinit var tiet_temp : TextInputEditText
    lateinit var btn_edit : FloatingActionButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v= inflater.inflate(R.layout.fragment_update__contenedor, container, false)
        if (container != null) {
            thiscontext = container.context
        };
        val requestHandler = RequestHandler.getInstance(thiscontext)
        val args = arguments?.let { ContenedorDetalleArgs.fromBundle(it) }
        id = args?.idContenedor.toString()
        estado = args?.statusContenedor.toString()
        tiet_id = v.findViewById(R.id.edittext_contenedor_id)
        tiet_id.text = id
        tiet_camion = v.findViewById(R.id.edittext_contenedor_camion)
        tiet_estado = v.findViewById(R.id.edittext_contenedor_estado)
        tiet_estado.hint = estado
        tiet_latitud = v.findViewById(R.id.edittext_contenedor_latitud)
        tiet_longitud = v.findViewById(R.id.edittext_contenedor_longitud)
        tiet_ruta = v.findViewById(R.id.edittext_contenedor_ruta)
        tiet_zona = v.findViewById(R.id.edittext_contenedor_zona)
        tiet_temp = v.findViewById(R.id.edittext_contenedor_temperatura)

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
        estado = tiet_estado.text.toString()
        Toast.makeText(thiscontext, estado, Toast.LENGTH_SHORT).show()
        val contenedor = Contenedor(id)
        contenedor.setStatus(estado)
        val patchObject = PatchContenedorObject()
        patchObject.addEntitie(contenedor)
        val jsonPatchObject = gson.toJson(patchObject)
        val jsonObject = JSONObject(jsonPatchObject)
        Toast.makeText(thiscontext, "$jsonObject", Toast.LENGTH_LONG).show()
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