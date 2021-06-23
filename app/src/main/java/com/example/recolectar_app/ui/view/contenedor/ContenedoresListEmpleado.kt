package com.example.recolectar_app.ui.view.contenedor

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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recolectar_app.core.UsuarioGlobal
import com.example.recolectar_app.databinding.ContenedoresListEmpleadoFragmentBinding
import com.example.recolectar_app.model.contenedor.ContenedorEmpleadoListAdapter
import com.example.recolectar_app.model.contenedor.ContenedorListAdapter
import com.example.recolectar_app.model.contenedor.ContenedorModel
import com.example.recolectar_app.model.zona.ZonaModel
import com.example.recolectar_app.ui.viewModel.contenedor.ContenedoresListEmpleadoVM
import java.lang.Exception

class ContenedoresListEmpleado : Fragment() {
    private val TAG = "Listado de contenedores del empleado"
    lateinit var thiscontext : Context
    private var _binding : ContenedoresListEmpleadoFragmentBinding? = null
    private val binding get() = _binding!!
    private val contenedoresListEmpleadoVM : ContenedoresListEmpleadoVM by viewModels()
    private val _contenedores = ArrayList<ContenedorModel>()
    private val email = "?type=Zona&q=empleado==${UsuarioGlobal.usuario.toString()}"
    private lateinit var _zonaId : String
    companion object {
        fun newInstance() = ContenedoresListEmpleado()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        try {
            _binding = ContenedoresListEmpleadoFragmentBinding.inflate(layoutInflater,container,false)
        }catch (e: Exception){
            Log.e(TAG,"onCreateView",e)
        }
        if (container != null) {
            thiscontext = container.context
        };

        binding.recContenedores.setHasFixedSize(true)
        binding.recContenedores.layoutManager =  LinearLayoutManager(context)
        binding.recContenedores.adapter = ContenedorEmpleadoListAdapter(_contenedores);

        contenedoresListEmpleadoVM.onCreate(email)
        contenedoresListEmpleadoVM._zonaId.observe(viewLifecycleOwner, {zonaId ->
            _zonaId = zonaId
            loadContenedores(zonaId)
        })

        binding.searchButton.setOnClickListener{
            if(!TextUtils.isEmpty(binding.searchEditText.text.toString())){
                val idContenedor = binding.searchEditText.text.toString()
                val string = "?id=wastecontainer:$idContenedor"
                contenedoresListEmpleadoVM.buscarContenedorPorId(string)
            }else{
                contenedoresListEmpleadoVM.onCreate(email)
            }
        }

        return binding.root
    }

    private fun loadContenedores(zonaId : String){
        contenedoresListEmpleadoVM._contenedores.observe(viewLifecycleOwner, { contenedores ->
            _contenedores.removeAll(_contenedores)
            _contenedores.addAll(contenedores)
            binding.recContenedores.adapter!!.notifyDataSetChanged()
        })
        contenedoresListEmpleadoVM.isLoading.observe(viewLifecycleOwner, { progressBar ->
            binding.loading.isVisible = progressBar
        })
        val req = "?type=WasteContainer&limit=1000&q=refZona==$zonaId"
        contenedoresListEmpleadoVM.buscarContenedorPorZona(req)
    }

}