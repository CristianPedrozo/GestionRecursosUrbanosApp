package com.example.recolectar_app.adapters
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recolectar_app.R
import com.example.recolectar_app.holders.ZonaHolder
import com.example.recolectar_app.zonas.Zona

class ZonaListAdapter(
    private var zonaList: MutableList<Zona>,
    val listener: (Zona) -> Unit
) : RecyclerView.Adapter<ZonaHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ZonaHolder {
        val view =  LayoutInflater.from(parent.context).inflate(R.layout.fragment_item_zona,parent,false)
        return (ZonaHolder(view))
    }

    companion object {

        private val TAG = "ZonaListAdapter"
    }

    override fun getItemCount(): Int {

        return zonaList.size
    }

    fun setData(newData: ArrayList<Zona>) {
        this.zonaList = newData
        this.notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ZonaHolder, position: Int) {
        holder.bind(zonaList[position],listener)
    }
}