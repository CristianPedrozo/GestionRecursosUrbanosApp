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
import com.example.recolectar_app.R
import com.example.recolectar_app.RequestHandler
import com.example.recolectar_app.adapters.ContenedorListAdapter
import com.example.recolectar_app.contenedores.Contenedor
import com.example.recolectar_app.databinding.FragmentAdministradorContenedoresBinding
import com.example.recolectar_app.databinding.FragmentListContenedoresBinding
import com.example.recolectar_app.databinding.FragmentUsuariosDatosBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import java.lang.Exception


class Contenedores : Fragment() {
    var url = "http://46.17.108.122:1026/v2/entities/?type=WasteContainer&limit=100"
    lateinit var thiscontext : Context
    private val TAG = "Contenedores Adm Frag"
    private var _binding: FragmentListContenedoresBinding? = null
    private val binding get() = _binding!!
    var contenedores : MutableList<Contenedor> = ArrayList()

    companion object {
        fun newInstance() = Contenedores()
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
        val requestHandler = RequestHandler.getInstance(thiscontext)
        getData(requestHandler)


        binding.botonAgregar.setOnClickListener() {
            val action = ContenedoresDirections.actionContenedoresToAltaContenedor()
            binding.root.findNavController().navigate(action)
        }
        return binding.root
    }

    fun getData(requestHandler: RequestHandler){
        val gson = Gson()
        requestHandler.getArrayRequest(url,
            { response ->
                for (i in 0 until response.length()) {
                    val contenedor : Contenedor = gson.fromJson(response.getJSONObject(i).toString(), Contenedor::class.java)
                    contenedores.add(contenedor)
                }
                binding.recContenedores.setHasFixedSize(true)

                binding.recContenedores.layoutManager =  LinearLayoutManager(context)

                binding.recContenedores.adapter = ContenedorListAdapter(contenedores);
            },
            { error ->
                Toast.makeText(this@Contenedores.requireContext(), "error" + error, Toast.LENGTH_LONG).show()
            }
            ,null)
    }

}