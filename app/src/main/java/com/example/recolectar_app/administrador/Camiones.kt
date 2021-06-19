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
import com.example.recolectar_app.adapters.CamionListAdapter
import com.example.recolectar_app.camiones.Camion
import com.example.recolectar_app.databinding.FragmentListCamionesBinding
import com.example.recolectar_app.databinding.FragmentListContenedoresBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import java.lang.Exception

class Camiones : Fragment() {
    var url = "http://46.17.108.122:1026/v2/entities/?type=Vehicle"
    lateinit var thiscontext : Context
    private val TAG = "Contenedores Adm Frag"
    private var _binding: FragmentListCamionesBinding? = null
    private val binding get() = _binding!!
    var camiones: MutableList<Camion> = ArrayList()

    companion object {
        fun newInstance() = Camiones()
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
        val requestHandler = RequestHandler.getInstance(thiscontext)
        getData(requestHandler)

        binding.botonAgregar.setOnClickListener(){

            val action = CamionesDirections.actionCamionesToAltaCamion()
            binding.root.findNavController().navigate(action)
        }
        return binding.root
    }


    fun getData(requestHandler: RequestHandler){
        val gson = Gson()
        requestHandler.getArrayRequest(url,
            { response ->
                for (i in 0 until response.length()) {
                    val camion : Camion = gson.fromJson(response.getJSONObject(i).toString(), Camion::class.java)
                    camiones.add(camion)
                }
                binding.recCamiones.setHasFixedSize(true)

                binding.recCamiones.layoutManager = LinearLayoutManager(context)

                binding.recCamiones.adapter = CamionListAdapter(camiones)
            },
            { error ->
                Toast.makeText(this@Camiones.requireContext(), "error" + error, Toast.LENGTH_LONG).show()
            }
            ,null)
    }
}

