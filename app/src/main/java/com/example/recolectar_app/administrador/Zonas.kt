package com.example.recolectar_app.administrador

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recolectar_app.RequestHandler
import com.example.recolectar_app.adapters.ZonaListAdapter
import com.example.recolectar_app.contenedores.Contenedor
import com.example.recolectar_app.databinding.FragmentListZonasBinding
import com.example.recolectar_app.zonas.Zona
import com.google.gson.Gson
import java.lang.Exception


class Zonas : Fragment() {
    private val TAG = "Zonas Adm Frag"
    var url = "http://46.17.108.122:1026/v2/entities/?type=Zona"
    private var urlContenedoresAsignados = "http://46.17.108.122:1026/v2/entities/?type=WasteContainer"
    lateinit var thiscontext : Context

    private var _binding: FragmentListZonasBinding? = null
    private val binding get() = _binding!!
    var zonas : MutableList<Zona> = ArrayList()

    companion object {
        fun newInstance() = Zonas()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        try {
            _binding = FragmentListZonasBinding.inflate(layoutInflater,container,false)
        }catch (e:Exception){
            Log.e(TAG,"onCreateView",e)
        }

        if (container != null) {
            thiscontext = container.context
        };

        val requestHandler = RequestHandler.getInstance(thiscontext)
        getData(requestHandler)

        binding.btnAgregarZona.setOnClickListener() {
            val action = ZonasDirections.actionZonasToAltaZona()
            binding.root.findNavController().navigate(action)
        }
        return binding.root
    }


    fun getData(requestHandler:RequestHandler){
        val gson = Gson()
        requestHandler.getArrayRequest(url,
            { response ->
                for (i in 0 until response.length()) {
                    val zona : Zona = gson.fromJson(response.getJSONObject(i).toString(),Zona::class.java)
                    zonas.add(zona)

                }
                getContenedoresAsignados(requestHandler,zonas)

                binding.recZonas.setHasFixedSize(true)
                binding.recZonas.layoutManager = LinearLayoutManager(context)
                binding.recZonas.adapter = ZonaListAdapter(zonas);

            },
            { error ->
                Toast.makeText(this@Zonas.requireContext(), "error" + error, Toast.LENGTH_LONG).show()
            }
        ,null)
    }

    fun getContenedoresAsignados(requestHandler: RequestHandler, zonas : MutableList<Zona>){
        val gson = Gson()
        val nZonas = zonas
        requestHandler.getArrayRequest(urlContenedoresAsignados,
            { response ->
                for(i in 0 until response.length()){
                    val contenedor : Contenedor = gson.fromJson(response.getJSONObject(i).toString(),Contenedor::class.java)
                    for(k in 0 until nZonas.size){
                        if(contenedor.refZona?.value == nZonas[k].id){
                            nZonas[k].contenedores.addContenedor(contenedor)
                        }
                    }
                }
                binding.recZonas.adapter?.notifyDataSetChanged()
            },{},null)


    }

}