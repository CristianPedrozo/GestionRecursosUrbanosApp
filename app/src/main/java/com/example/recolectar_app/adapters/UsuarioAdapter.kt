package com.example.recolectar_app

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.recolectar_app.administrador.UsuariosDirections
import com.example.recolectar_app.databinding.FragmentItemUsuariosBinding
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
        val binding = FragmentItemUsuariosBinding.bind(view)
        fun bind(usuario:Usuario, context: Context){

            var estado = "No esta trabajando"
            if(usuario.estaActivo == true)
                estado = "Esta trabajando"

            binding.nombre.text = usuario.razonSocial
            binding.jefe.text = estado
            binding.distrito.text = usuario.zona
            binding.ivAvatar.loadUrl(usuario.photo)
            itemView.setOnClickListener {
                Navigation.findNavController(binding.root).navigate(
                    UsuariosDirections.actionUsuariosToDatos(
                        usuario.usuario as String
                ))
            }
        }
        fun ImageView.loadUrl(url: String?) {
            Picasso.get().load(url).into(this)
        }
    }
}