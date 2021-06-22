package com.example.recolectar_app.ui.view.camion
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
import com.example.recolectar_app.RequestHandler
import com.example.recolectar_app.model.camion.CamionListAdapter
import com.example.recolectar_app.model.camion.CamionModel
import com.example.recolectar_app.databinding.FragmentListCamionesBinding
import com.google.gson.Gson
import java.lang.Exception

class CamionesList : Fragment() {
    var url = "http://46.17.108.122:1026/v2/entities/?type=Vehicle"
    lateinit var thiscontext : Context
    private val TAG = "Contenedores Adm Frag"
    private var _binding: FragmentListCamionesBinding? = null
    private val binding get() = _binding!!
    var camiones: MutableList<CamionModel> = ArrayList()

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
        val requestHandler = RequestHandler.getInstance(thiscontext)
        getData(requestHandler)

        binding.botonAgregar.setOnClickListener(){

            val action = CamionesListDirections.actionCamionesToAltaCamion()
            binding.root.findNavController().navigate(action)
        }
        return binding.root
    }


    fun getData(requestHandler: RequestHandler){
        val gson = Gson()
        requestHandler.getArrayRequest(url,
            { response ->
                for (i in 0 until response.length()) {
                    val camionModel : CamionModel = gson.fromJson(response.getJSONObject(i).toString(), CamionModel::class.java)
                    camiones.add(camionModel)
                }
                binding.recCamiones.setHasFixedSize(true)

                binding.recCamiones.layoutManager = LinearLayoutManager(context)

                binding.recCamiones.adapter = CamionListAdapter(camiones)
            },
            { error ->
                Toast.makeText(this@CamionesList.requireContext(), "error" + error, Toast.LENGTH_LONG).show()
            }
            ,null)
    }
}
