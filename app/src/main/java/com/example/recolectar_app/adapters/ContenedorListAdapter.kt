package com.example.recolectar_app.adapters
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recolectar_app.R
import com.example.recolectar_app.contenedores.Contenedor
import com.example.recolectar_app.holders.ContenedorHolder

class ContenedorListAdapter (private var contenedorList: MutableList<Contenedor>) : RecyclerView.Adapter<ContenedorHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContenedorHolder {
        val view =  LayoutInflater.from(parent.context).inflate(R.layout.fragment_item_contenedor,parent,false)
        return (ContenedorHolder(view))
    }

    companion object {

        private val TAG = "ContenedorListAdapter"
    }

    override fun getItemCount(): Int {

        return contenedorList.size
    }

    fun setData(newData: ArrayList<Contenedor>) {
        this.contenedorList = newData
        this.notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ContenedorHolder, position: Int) {
        holder.bind(contenedorList[position])
    }
}