package com.example.recolectar_app.model.contenedor
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recolectar_app.R

class ContenedorListAdapter (private var contenedorModelList: MutableList<ContenedorModel>) : RecyclerView.Adapter<ContenedorHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContenedorHolder {
        val layoutInflater =  LayoutInflater.from(parent.context)
        return ContenedorHolder(layoutInflater.inflate(R.layout.fragment_item_contenedor,parent,false))
    }

    override fun getItemCount(): Int {
        return contenedorModelList.size
    }

    fun setData(newData: ArrayList<ContenedorModel>) {
        this.contenedorModelList = newData
        this.notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ContenedorHolder, position: Int) {
        holder.bind(contenedorModelList[position])
    }
}