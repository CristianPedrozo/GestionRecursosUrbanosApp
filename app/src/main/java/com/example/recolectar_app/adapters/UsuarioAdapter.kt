package com.example.recolectar_app

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.recolectar_app.administrador.ContenedoresDirections
import com.example.recolectar_app.administrador.UsuariosDirections
import com.squareup.picasso.Picasso

class UsuarioAdapter : RecyclerView.Adapter<UsuarioAdapter.UsuarioHolder>() {
    var users: MutableList<Usuario>  = ArrayList()
    lateinit var context:Context
    fun UsuarioAdapter(superheros : MutableList<Usuario>, context: Context){
        this.users = superheros
        this.context = context
    }
    override fun onBindViewHolder(holder: UsuarioHolder, position: Int) {
        val item = users[position]
        holder.bind(item, context)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsuarioHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return UsuarioHolder(layoutInflater.inflate(R.layout.fragment_item_usuarios, parent, false))
    }
    override fun getItemCount(): Int {
        return users.size
    }
    class UsuarioHolder(view: View) : RecyclerView.ViewHolder(view) {
        val vista = view
        val nombre = view.findViewById(R.id.nombre) as TextView
        val jefe = view.findViewById(R.id.jefe) as TextView
        val distrito = view.findViewById(R.id.distrito) as TextView
        val avatar = view.findViewById(R.id.ivAvatar) as ImageView
        fun bind(usuario:Usuario, context: Context){
            nombre.text = usuario.razonSocial
            jefe.text = usuario.jefe
            distrito.text = usuario.distrito
            avatar.loadUrl(usuario.photo)
            itemView.setOnClickListener {
                Navigation.findNavController(vista).navigate(
                    UsuariosDirections.actionUsuariosToDatos(
                        usuario.email as String
                ))
            }
        }
        fun ImageView.loadUrl(url: String?) {
            Picasso.get().load(url).into(this)
        }
    }
}