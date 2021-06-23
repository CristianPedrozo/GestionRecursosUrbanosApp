package com.example.recolectar_app.model.contenedor

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recolectar_app.R

class ContenedorEmpleadoListAdapter(private var contenedorModelList: MutableList<ContenedorModel>) : RecyclerView.Adapter<ContenedorEmpleadoHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContenedorEmpleadoHolder {
        val layoutInflater =  LayoutInflater.from(parent.context)
        return ContenedorEmpleadoHolder(layoutInflater.inflate(R.layout.fragment_item_contenedor,parent,false))
    }

    override fun getItemCount(): Int {
        return contenedorModelList.size
    }

    fun setData(newData: ArrayList<ContenedorModel>) {
        this.contenedorModelList = newData
        this.notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ContenedorEmpleadoHolder, position: Int) {
        holder.bind(contenedorModelList[position])
    }
}