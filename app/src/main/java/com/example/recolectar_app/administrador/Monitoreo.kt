package com.example.recolectar_app.administrador

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.recolectar_app.R
import com.example.recolectar_app.databinding.FragmentAdministradorMonitoreoBinding
import com.example.recolectar_app.databinding.FragmentAdministradorUsuariosBinding
import java.lang.Exception


class Monitoreo : Fragment() {
    private val TAG = "Monitoreo Frag"
    private var _binding: FragmentAdministradorMonitoreoBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        try {
            _binding = FragmentAdministradorMonitoreoBinding.inflate(layoutInflater,container,false)
        }catch (e:Exception){
            Log.e(TAG,"onCreateView",e)
        }

        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Monitoreo().apply {
                arguments = Bundle().apply {
                }
            }
    }
}