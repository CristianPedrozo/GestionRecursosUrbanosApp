package com.example.recolectar_app.adapters
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recolectar_app.R
import com.example.recolectar_app.entities.Camion
import com.example.recolectar_app.holders.CamionHolder

class CamionListAdapter ( private var camionList: MutableList<Camion>) : RecyclerView.Adapter<CamionHolder>() {

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

        holder.setName(camionList[position].patente)

//        Glide
//            .with(context)
//            .load("https://firebasestorage.googleapis.com/v0/b/firestoreexample-ec489.appspot.com/o/Fotos%2FGUERNICA.jpg?alt=media&token=001a8ffc-96c2-4aeb-9120-8d5099b3fa1c")
//
//            .centerInside()
//            .into(holder.getImageView());
//
        /*    holder.getCardLayout().setOnLongClickListener() {
                onItemClick(position)
            }*/

    }
}