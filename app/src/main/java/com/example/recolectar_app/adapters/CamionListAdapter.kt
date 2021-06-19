package com.example.recolectar_app.adapters
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recolectar_app.R
import com.example.recolectar_app.camiones.Camion
import com.example.recolectar_app.holders.CamionHolder

class CamionListAdapter ( private var camionList: MutableList<Camion>,val listener: (Camion) -> Unit) : RecyclerView.Adapter<CamionHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CamionHolder {
        val view =  LayoutInflater.from(parent.context).inflate(R.layout.fragment_item_camion,parent,false)
        return (CamionHolder(view))
    }

    companion object {

        private val TAG = "CamionListAdapter"
    }

    override fun getItemCount(): Int {

        return camionList.size
    }

    fun setData(newData: ArrayList<Camion>) {
        this.camionList = newData
        this.notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: CamionHolder, position: Int) {
        //Para carga de datos en el Card de cada camión
        holder.bind(camionList[position],listener)


    }
}