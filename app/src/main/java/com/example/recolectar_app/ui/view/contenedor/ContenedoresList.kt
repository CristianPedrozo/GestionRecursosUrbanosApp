package com.example.recolectar_app.ui.view.contenedor

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
import com.example.recolectar_app.model.contenedor.ContenedorListAdapter
import com.example.recolectar_app.model.contenedor.ContenedorModel
import com.example.recolectar_app.databinding.FragmentListContenedoresBinding
import com.example.recolectar_app.ui.viewModel.contenedor.ContenedorListVM
import java.lang.Exception


class ContenedoresList : Fragment() {
    var url = "http://46.17.108.122:1026/v2/entities/?type=WasteContainer&limit=100"
    lateinit var thiscontext : Context
    private val TAG = "Contenedores Adm Frag"
    private var _binding: FragmentListContenedoresBinding? = null
    private val binding get() = _binding!!
    private val contenedorListVM : ContenedorListVM by viewModels()
    private val _contenedores = ArrayList<ContenedorModel>()

    companion object {
        fun newInstance() = ContenedoresList()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        try {
            _binding = FragmentListContenedoresBinding.inflate(layoutInflater,container,false)
        }catch (e: Exception){
            Log.e(TAG,"onCreateView",e)
        }
        if (container != null) {
            thiscontext = container.context
        };

        binding.recContenedores.setHasFixedSize(true)
        binding.recContenedores.layoutManager =  LinearLayoutManager(context)
        binding.recContenedores.adapter = ContenedorListAdapter(_contenedores);

        contenedorListVM.onCreate()

        contenedorListVM._contenedores.observe(viewLifecycleOwner, { contenedores ->
            _contenedores.removeAll(_contenedores)
            _contenedores.addAll(contenedores)
            binding.recContenedores.adapter!!.notifyDataSetChanged()
        })
        contenedorListVM.isLoading.observe(viewLifecycleOwner, { progressBar ->
            binding.loading.isVisible = progressBar
        })

        binding.btnAgregarContenedor.setOnClickListener {
            val action = ContenedoresListDirections.actionContenedoresToAltaContenedor()
            binding.root.findNavController().navigate(action)
        }

        binding.searchButton.setOnClickListener{
            if(!TextUtils.isEmpty(binding.searchEditText.text.toString())){
                val idContenedor = binding.searchEditText.text.toString()
                val string = "?id=wastecontainer:$idContenedor"
                contenedorListVM.buscarContenedorPorId(string)
            }else{
                contenedorListVM.onCreate()
            }
        }

        binding.searchByZonaButton.setOnClickListener{
            if(!TextUtils.isEmpty(binding.searchByZonaEditText.text.toString())){
                val idZona = binding.searchByZonaEditText.text.toString()
                val string = "?type=WasteContainer&limit=1000&q=refZona==zona:$idZona"
                contenedorListVM.buscarContenedorPorZona(string)
            }else{
                contenedorListVM.onCreate()
            }
        }
        return binding.root
    }


}