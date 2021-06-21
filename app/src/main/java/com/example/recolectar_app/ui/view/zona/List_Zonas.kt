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
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recolectar_app.model.zona.ZonaListAdapter
import com.example.recolectar_app.databinding.FragmentListZonasBinding
import com.example.recolectar_app.model.zona.ZonaModel
import com.example.testmvvm.ui.viewModel.ListZonaVM
import java.lang.Exception


class List_Zonas : Fragment() {
    private val TAG = "Zonas Adm Frag"
    var url = "http://46.17.108.122:1026/v2/entities/?type=Zona"
    private var urlContenedoresAsignados = "http://46.17.108.122:1026/v2/entities/?type=WasteContainer"
    lateinit var thiscontext : Context

    private var _binding: FragmentListZonasBinding? = null
    private val binding get() = _binding!!
    private val listZonaViewModel : ListZonaVM by viewModels()
    private val _zonas = ArrayList<ZonaModel>()

    companion object {
        fun newInstance() = List_Zonas()
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

        listZonaViewModel.onCreate()

        listZonaViewModel.zonas.observe(viewLifecycleOwner, { zonas ->
            _zonas.removeAll(_zonas)
            _zonas.addAll(zonas)
            binding.recZonas.adapter!!.notifyDataSetChanged()
        })
        listZonaViewModel.isLoading.observe(viewLifecycleOwner, { progressBar ->
            binding.loading.isVisible = progressBar
        })

//        val requestHandler = RequestHandler.getInstance(thiscontext)
//        getData(requestHandler)

        binding.btnAgregarZona.setOnClickListener() {
            val action = List_ZonasDirections.actionListZonasToAltaZona()
            binding.root.findNavController().navigate(action)
        }

        binding.searchButton.setOnClickListener(){
            if(!TextUtils.isEmpty(binding.searchEditText.text.toString())){
                val string = "?q=nombre=="+binding.searchEditText.text.toString()
                listZonaViewModel.buscarZona(string)
            }else{
                listZonaViewModel.onCreate()
            }
        }
        return binding.root
    }


//    fun getData(requestHandler:RequestHandler){
//        val gson = Gson()
//        requestHandler.getArrayRequest(url,
//            { response ->
//                for (i in 0 until response.length()) {
//                    val zona : ZonaModel = gson.fromJson(response.getJSONObject(i).toString(),ZonaModel::class.java)
//                    zonas.add(zona)
//
//                }
//                getContenedoresAsignados(requestHandler,zonas)
//
//                binding.recZonas.setHasFixedSize(true)
//                binding.recZonas.layoutManager = LinearLayoutManager(context)
//                binding.recZonas.adapter = ZonaListAdapter(zonas);
//
//            },
//            { error ->
//                Toast.makeText(this@List_Zonas.requireContext(), "error" + error, Toast.LENGTH_LONG).show()
//            }
//        ,null)
//    }

//    fun getContenedoresAsignados(requestHandler: RequestHandler, zonas : MutableList<ZonaModel>){
//        val gson = Gson()
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