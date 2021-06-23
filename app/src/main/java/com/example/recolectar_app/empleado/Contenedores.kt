package com.example.recolectar_app.empleado

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.recolectar_app.model.contenedor.ContenedorListAdapter
import com.example.recolectar_app.ui.view.contenedor.ContenedoresListAdmin
import com.example.recolectar_app.model.contenedor.ContenedorModel
import com.example.recolectar_app.databinding.FragmentEmpleadoContenedoresBinding
import com.google.gson.Gson

class Contenedores : Fragment() {
    private var _binding: FragmentEmpleadoContenedoresBinding? = null
    private val binding get() = _binding!!
    //esto tiene q ser variable dependiendo del usuario y su vehiculo
    var url = "http://46.17.108.122:1026/v2/entities/?type=WasteContainer&?q=refZona=="
    lateinit var thiscontext : Context
    lateinit var recContenedores : RecyclerView
    var contenedores : MutableList<ContenedorModel> = ArrayList()
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var contenedorListAdapter: ContenedorListAdapter

    companion object {
        fun newInstance() = ContenedoresListAdmin()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEmpleadoContenedoresBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        val gson = Gson()
        val queue = Volley.newRequestQueue(activity)

        val jsonArrayRequest = JsonArrayRequest(url,
            { response ->
                for (i in 0 until response.length()){
                    val contenedorModel : ContenedorModel = gson.fromJson(response.getJSONObject(i).toString(),
                        ContenedorModel::class.java)
                    contenedores.add(contenedorModel)
                }
                recContenedores.setHasFixedSize(true)
                linearLayoutManager = LinearLayoutManager(context)
                recContenedores.layoutManager = linearLayoutManager

                contenedorListAdapter = ContenedorListAdapter(contenedores);


                recContenedores.adapter = contenedorListAdapter
            }, {})
        queue.add(jsonArrayRequest)
    }
}