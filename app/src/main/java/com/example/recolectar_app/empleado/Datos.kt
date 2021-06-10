package com.example.recolectar_app.empleado

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.recolectar_app.R
import com.example.recolectar_app.Usuario
import com.example.recolectar_app.administrador.Datos_AdministradorArgs
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class Datos : Fragment() {
    private lateinit var v: View
    private lateinit var datos : TextView

    val db = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_empleado_datos, container, false)

        var email = getUserInstance()
        if(email != null)
            obtenerUsuarioFirebase(email.toString())

        return v

    }
    fun obtenerUsuarioFirebase(email:String?){
        if(email != null){
            email.toString()
            db.collection("usuarios").document(email)
                .get()
                .addOnSuccessListener {document->
                    var usuario = Usuario(document.getString("razonSocial"), email, document.getString("distrito"), document.getString("jefe"), document.getString("horarioEntrada"),document.getString("horarioSalida"), document.getBoolean("esAdmin"),"https://www.uclg-planning.org/sites/default/files/styles/featured_home_left/public/no-user-image-square.jpg?itok=PANMBJF-")
                    cargarCampos(usuario)
                }
        }
    }

    fun cargarCampos(usuario : Usuario){
        v.findViewById<EditText>(R.id.editTextRazonSocial).setText(usuario.razonSocial)
        v.findViewById<EditText>(R.id.editTextEmail).setText(usuario.email)
        v.findViewById<EditText>(R.id.editTextJefe).setText(usuario.jefe)
        v.findViewById<EditText>(R.id.editTextDistrito).setText(usuario.distrito)
        v.findViewById<EditText>(R.id.editTextHorarioEntrada).setText(usuario.horarioEntrada)
        v.findViewById<EditText>(R.id.editTextHorarioSalida).setText(usuario.horarioSalida)
    }

    fun getUserInstance(): String?{
        var email:String? = null
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null)
            email = user.email

        return email
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String) =
            Datos().apply {
                arguments = Bundle().apply {

                }
            }
    }


}