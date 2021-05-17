package com.example.recolectar_app

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class UsuarioAdapter : RecyclerView.Adapter<UsuarioAdapter.UsuarioHolder>() {
    var superheros: MutableList<Usuario>  = ArrayList()
    lateinit var context:Context
    fun UsuarioAdapter(superheros : MutableList<Usuario>, context: Context){
        this.superheros = superheros
        this.context = context
    }
    override fun onBindViewHolder(holder: UsuarioHolder, position: Int) {
        val item = superheros[position]
        holder.bind(item, context)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsuarioHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return UsuarioHolder(layoutInflater.inflate(R.layout.item_usuario, parent, false))
    }
    override fun getItemCount(): Int {
        return superheros.size
    }
    class UsuarioHolder(view: View) : RecyclerView.ViewHolder(view) {
        val superheroName = view.findViewById(R.id.tvSuperhero) as TextView
        val realName = view.findViewById(R.id.tvRealName) as TextView
        val publisher = view.findViewById(R.id.tvPublisher) as TextView
        val avatar = view.findViewById(R.id.ivAvatar) as ImageView
        fun bind(superhero:Usuario, context: Context){
            superheroName.text = superhero.superhero
            realName.text = superhero.realName
            publisher.text = superhero.publisher
            avatar.loadUrl(superhero.photo)
            itemView.setOnClickListener(View.OnClickListener { Toast.makeText(context, superhero.superhero, Toast.LENGTH_SHORT).show() })
        }
        fun ImageView.loadUrl(url: String) {
            Picasso.get().load(url).into(this)
        }
    }
}