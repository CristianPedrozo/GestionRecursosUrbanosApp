package com.example.recolectar_app.model.zona
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recolectar_app.R

class ZonaListAdapter(
    private var zonaList: MutableList<ZonaModel>
) : RecyclerView.Adapter<ZonaHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ZonaHolder {
        val layoutInflater =  LayoutInflater.from(parent.context)
        return ZonaHolder(layoutInflater.inflate(R.layout.fragment_item_zona,parent,false))
    }

    override fun getItemCount(): Int {
        return zonaList.size
    }

    fun setData(newData: ArrayList<ZonaModel>) {
        this.zonaList = newData
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ZonaHolder, position: Int) {
        holder.bind(zonaList[position])
    }


}