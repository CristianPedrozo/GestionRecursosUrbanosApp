package com.example.recolectar_app.model.camion
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recolectar_app.R

class CamionListAdapter ( private var camionModelList: MutableList<CamionModel>) : RecyclerView.Adapter<CamionHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CamionHolder {
        val layoutInflater =  LayoutInflater.from(parent.context)
        return CamionHolder(layoutInflater.inflate(R.layout.fragment_item_camion,parent,false))
    }

    override fun getItemCount(): Int {

        return camionModelList.size
    }

    fun setData(newData: ArrayList<CamionModel>) {
        this.camionModelList = newData
        this.notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: CamionHolder, position: Int) {
        holder.bind(camionModelList[position])
    }
}