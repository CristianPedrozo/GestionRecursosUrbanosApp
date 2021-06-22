package com.example.recolectar_app.ui.view.zona

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recolectar_app.model.zona.ZonaListAdapter
import com.example.recolectar_app.databinding.FragmentListZonasBinding
import com.example.recolectar_app.model.zona.ZonaModel
import com.example.recolectar_app.ui.viewModel.zona.ZonaListVM
import java.lang.Exception


class ZonasList : Fragment() {
    private val TAG = "Zonas Adm Frag"
    var url = "http://46.17.108.122:1026/v2/entities/?type=Zona"
    private var urlContenedoresAsignados = "http://46.17.108.122:1026/v2/entities/?type=WasteContainer"
    lateinit var thiscontext : Context

    private var _binding: FragmentListZonasBinding? = null
    private val binding get() = _binding!!
    private val zonaListViewModel : ZonaListVM by viewModels()
    private val _zonas = ArrayList<ZonaModel>()

    companion object {
        fun newInstance() = ZonasList()
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

        binding.recZonas.setHasFixedSize(true)
        binding.recZonas.layoutManager = LinearLayoutManager(context)
        binding.recZonas.adapter = ZonaListAdapter(_zonas);

        zonaListViewModel.onCreate()

        zonaListViewModel.zonasList.observe(viewLifecycleOwner, { zonas ->
            _zonas.removeAll(_zonas)
            _zonas.addAll(zonas)
            binding.recZonas.adapter!!.notifyDataSetChanged()
        })
        zonaListViewModel.isLoading.observe(viewLifecycleOwner, { progressBar ->
            binding.loading.isVisible = progressBar
        })



        binding.btnAgregarZona.setOnClickListener() {
            val action = ZonasListDirections.actionListZonasToAltaZona()
            binding.root.findNavController().navigate(action)
        }

        binding.searchButton.setOnClickListener(){
            if(!TextUtils.isEmpty(binding.searchEditText.text.toString())){
                val nombre_zona = binding.searchEditText.text.toString()[0].uppercase()+binding.searchEditText.text.toString().substring(1)
                val string = "?q=nombre==$nombre_zona"
                zonaListViewModel.buscarZona(string)
            }else{
                zonaListViewModel.onCreate()
            }
        }
        return binding.root
    }




////    fun getContenedoresAsignados(requestHandler: RequestHandler, zonas : MutableList<ZonaModel>){
////        val gso = Gson()
//        val nZonas = zonas
//        requestHandler.getArrayRequest(urlContenedoresAsignados,
//            { response ->
//                for(i in 0 until response.length()){
//                    val contenedor : Contenedor = gson.fromJson(response.getJSONObject(i).toString(),Contenedor::class.java)
//                    for(k in 0 until nZonas.size){
//                        if(contenedor.refZona?.value == nZonas[k].id){
//                            nZonas[k].contenedores.addContenedor(contenedor)
//                        }
//                    }
//                }
//                binding.recZonas.adapter?.notifyDataSetChanged()
//            },{},null)
//
//
//    }

}