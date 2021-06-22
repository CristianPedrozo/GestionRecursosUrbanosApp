package com.example.recolectar_app.ui.view.camion
import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recolectar_app.model.camion.CamionListAdapter
import com.example.recolectar_app.model.camion.CamionModel
import com.example.recolectar_app.databinding.FragmentListCamionesBinding
import com.example.recolectar_app.ui.viewModel.camion.CamionListVM
import com.google.gson.Gson
import java.lang.Exception

class CamionesList : Fragment() {
    lateinit var thiscontext : Context
    private val TAG = "Contenedores Adm Frag"
    private var _binding: FragmentListCamionesBinding? = null
    private val binding get() = _binding!!
    private val camionListVM : CamionListVM by viewModels()
    private val _camiones = ArrayList<CamionModel>()

    companion object {
        fun newInstance() = CamionesList()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        try {
            _binding = FragmentListCamionesBinding.inflate(layoutInflater,container,false)
        }catch (e: Exception){
            Log.e(TAG,"onCreateView",e)
        }
        if (container != null) {
            thiscontext = container.context
        };
        binding.recCamiones.setHasFixedSize(true)
        binding.recCamiones.layoutManager = LinearLayoutManager(context)
        binding.recCamiones.adapter = CamionListAdapter(_camiones)

        camionListVM.onCreate()

        camionListVM._camiones.observe(viewLifecycleOwner, { camiones ->
            _camiones.removeAll(_camiones)
            _camiones.addAll(camiones)
            binding.recCamiones.adapter!!.notifyDataSetChanged()
        })
        camionListVM.isLoading.observe(viewLifecycleOwner, { progressBar ->
            binding.loading.isVisible = progressBar
        })

        binding.btnAgregarCamion.setOnClickListener(){

            val action = CamionesListDirections.actionCamionesToAltaCamion()
            binding.root.findNavController().navigate(action)
        }

        binding.searchButton.setOnClickListener{
            if(!TextUtils.isEmpty(binding.searchEditText.text.toString())){
                val idContenedor = binding.searchEditText.text.toString()
                val string = "?id=vehicle:$idContenedor"
                camionListVM.buscarCamionById(string)
            }else{
                camionListVM.onCreate()
            }
        }

        binding.searchByZonaButton.setOnClickListener{
            if(!TextUtils.isEmpty(binding.searchByZonaEditText.text.toString())){
                val idZona = binding.searchByZonaEditText.text.toString()
                val string = "?type=Vehicle&limit=1000&q=refZona==zona:$idZona"
                camionListVM.buscarCamionByZona(string)
            }else{
                camionListVM.onCreate()
            }
        }

        return binding.root
    }



}

