package com.example.recolectar_app.empleado

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.recolectar_app.R
import com.example.recolectar_app.Usuario
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase


class Datos : Fragment() {
    private lateinit var v: View
    private lateinit var datos : TextView
    private lateinit var auth: FirebaseAuth

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
        val button = v.findViewById<Button>(R.id.btnAgregarUsuario)
        // Initialize Firebase Auth
        button.setOnClickListener {
            agregarUsuario()
        }
        // Initialize Firebase Auth
        auth = Firebase.auth
        var email = getUserInstance()
        if(email != null)
            obtenerUsuarioFirebase(email.toString())

        return v

    }

    fun obtenerDatos(): Usuario {
        val razonSocial = v.findViewById<EditText>(R.id.editTextRazonSocial).text.toString()
        val usuario = v.findViewById<EditText>(R.id.editTextUsuario).text.toString()
        val zona = v.findViewById<EditText>(R.id.editTextZona).text.toString()
        val email = v.findViewById<EditText>(R.id.editTextEmail).text.toString()
        val horarioEntrada = v.findViewById<EditText>(R.id.editTextHorarioEntrada).text.toString()
        val horarioSalida = v.findViewById<EditText>(R.id.editTextHorarioSalida).text.toString()
        val esAdmin = v.findViewById<CheckBox>(R.id.checkBoxEsAdmin).isChecked()
        return Usuario(razonSocial, usuario ,email, zona,horarioEntrada,horarioSalida,esAdmin,"")
    }

    fun validarDatos():Boolean{
        var usuario = obtenerDatos()
        var contrasenia1 = v.findViewById<EditText>(R.id.editTextContraseñaAdmin1).text.toString()
        var contrasenia2 = v.findViewById<EditText>(R.id.editTextContraseñaAdmin1).text.toString()

        if(contrasenia1 != contrasenia2)
            return false
        if(usuario.razonSocial.toString().isEmpty())
            return false
        if(usuario.zona.toString().isEmpty())
            return false
        if(usuario.horarioEntrada.toString().isEmpty())
            return false
        if(usuario.horarioSalida.toString().isEmpty())
            return false
        if(usuario.usuario.toString().isEmpty())
            return false

        return true
    }

    fun guardarUsuarioFirebase(usuario: Usuario){
        db.collection("usuarios").document(usuario.usuario).set(
            hashMapOf(
                "zona" to usuario.zona,
                "esAdmin" to  usuario.esAdmin,
                "horarioEntrada" to usuario.horarioEntrada,
                "horarioSalida" to usuario.horarioSalida,
                "email" to usuario.email,
                "razonSocial" to usuario.razonSocial
            )
        ).addOnSuccessListener{
            /* if(email != null){
                 Toast.makeText(this, "Usuario editado con exito", Toast.LENGTH_SHORT).show()
             }else{
                 Toast.makeText(this, "Usuario agregado con exito", Toast.LENGTH_SHORT).show()
             }*/
        }.addOnFailureListener{
            /* if(email != null){
                 Toast.makeText(this, "Error al editar el usuario", Toast.LENGTH_SHORT).show()
             }else{
                 Toast.makeText(this, "Error al agregar el usuario", Toast.LENGTH_SHORT).show()
             }*/
        }
    }
    fun agregarUsuario(){
        var usuario = obtenerDatos()
        if(validarDatos()){
            guardarUsuarioFirebase(usuario)
            actualizarContraseña()
        }
        else{
            Toast.makeText(v.context,"Datos invalidos, no se pudo guardar el usuario", Toast.LENGTH_LONG ).show()
        }
    }

    fun actualizarContraseña(){
        var contrasenia1 = v.findViewById<EditText>(R.id.editTextContraseñaAdmin1).text.toString()
        var contrasenia2 = v.findViewById<EditText>(R.id.editTextContraseñaAdmin1).text.toString()

        if(contrasenia1 == null && contrasenia2 == null) {
            if (contrasenia1.length < 8 || contrasenia2.length < 8) {
                //
            }

            val user = Firebase.auth.currentUser

            user!!.updatePassword(contrasenia1)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                    }
                }
        }
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
        v.findViewById<EditText>(R.id.editTextUsuario).setText(usuario.usuario)
        v.findViewById<EditText>(R.id.editTextZona).setText(usuario.zona)
        v.findViewById<EditText>(R.id.editTextEmail).setText(usuario.email)
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