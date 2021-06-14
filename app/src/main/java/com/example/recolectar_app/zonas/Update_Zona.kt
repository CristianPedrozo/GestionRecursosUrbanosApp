package com.example.recolectar_app.zonas

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.recolectar_app.PatchRequestObject
import com.example.recolectar_app.R
import com.example.recolectar_app.RequestHandler
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import com.google.gson.Gson
import org.json.JSONObject


class Update_Zona : Fragment() {
    private val TAG = "Update Zona"
    private var url = "http://46.17.108.122:1026/v2/op/update"
    private lateinit var v: View
    private lateinit var zona: Zona
    lateinit var tiet_id_zona : TextInputEditText
    lateinit var tiet_contenedores_zona : TextInputEditText
    lateinit var tiet_camion_zona : TextInputEditText
    lateinit var thiscontext : Context
    private lateinit var btn_edit : FloatingActionButton
    private lateinit var btn_cancelar: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        v= inflater.inflate(R.layout.fragment_update_zona, container, false)
        if (container != null) {
            thiscontext = container.context
        }
        val requestHandler = RequestHandler.getInstance(thiscontext)
        val args = arguments?.let { ZonaDetalleArgs.fromBundle(it) }
        zona = args?.zona!!
        tiet_id_zona = v.findViewById(R.id.text_id_edit_zona)
        tiet_id_zona.hint = "Zona n°: ${zona.id!!.split(":")[1]}"
        tiet_contenedores_zona = v.findViewById(R.id.text_contenedores_edit_zona)
        tiet_contenedores_zona.hint = "Contenedores: ${zona.contenedores.value.size}"
        tiet_camion_zona = v.findViewById(R.id.text_camion_edit_zona)
        btn_cancelar = v.findViewById(R.id.boton_cancelar_edit_zona)
        btn_cancelar.setOnClickListener {
            val action = Update_ZonaDirections.actionUpdateZonaToZonas()
            v.findNavController().navigate(action)
        }
        btn_edit = v.findViewById(R.id.boton_confirmar_editar_zona)
        btn_edit.setOnClickListener {
            editZona(requestHandler)
            val action = Update_ZonaDirections.actionUpdateZonaToZonas()
            v.findNavController().navigate(action)
        }
        return v
    }


    private fun editZona(requestHandler: RequestHandler) {
        val gson = Gson()
        val zona = Zona(zona.id!!.split(":")[1])
        zona.setRefVehicleValue(tiet_camion_zona.text.toString())
        val patchObject = PatchRequestObject()
        patchObject.addEntitie(zona)
        val jsonPatchObject = gson.toJson(patchObject)
        val jsonObject = JSONObject(jsonPatchObject)
        requestHandler.patchRequest(url,jsonObject,{},{})
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            Update_Zona().apply {
                arguments = Bundle().apply {

                }
            }
    }
}