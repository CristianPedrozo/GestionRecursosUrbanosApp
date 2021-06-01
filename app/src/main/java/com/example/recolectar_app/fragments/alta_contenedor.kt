package com.example.recolectar_app.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import com.example.recolectar_app.R

class alta_contenedor : Fragment() {
    lateinit var v:View
    lateinit var autoCompleteTextView: AutoCompleteTextView
    lateinit var autoCompleteTextView_tipo: AutoCompleteTextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v= inflater.inflate(R.layout.fragment_alta_contenedor, container, false)
        //Carga Combo Estados
        val estados = resources.getStringArray(R.array.estados_contenedor)
        val arrayAdapter = ArrayAdapter(requireContext(),R.layout.combo_formulario,estados)
        autoCompleteTextView = v.findViewById(R.id.autoCompleteTextView)
        autoCompleteTextView.setAdapter(arrayAdapter)
        //Carga Combo Tipo
        val tipos = resources.getStringArray(R.array.tipos_residuos)
        val arrayAdapterTipo = ArrayAdapter(requireContext(),R.layout.combo_formulario,tipos)
        autoCompleteTextView_tipo = v.findViewById(R.id.autoCompleteTextView_tipo)
        autoCompleteTextView_tipo.setAdapter(arrayAdapterTipo)
        return v
    }

}