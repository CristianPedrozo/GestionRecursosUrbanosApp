package com.example.recolectar_app.zonas

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.recolectar_app.R


class item_zona : Fragment() {
    private lateinit var v: View
//    lateinit var idZona : TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_item_zona, container, false)
//        idZona = v.findViewById(R.id.txt_id_itemZona)
//        idZona.text = idZona.text.split(":")[1]
        return v
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            item_zona().apply {
                arguments = Bundle().apply {

                }
            }
    }
}