package com.example.recolectar_app.empleado

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.recolectar_app.Usuario
import com.example.recolectar_app.databinding.FragmentEmpleadoDatosBinding
import com.example.recolectar_app.databinding.FragmentUsuariosDatosBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase


class Datos : Fragment() {
    private var _binding: FragmentEmpleadoDatosBinding? = null
    private var _bindingUsr : FragmentUsuariosDatosBinding? = null
    private val binding get() = _binding!!
    private val bindingUsr get() = _bindingUsr!!
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
    ): View {
        _binding = FragmentEmpleadoDatosBinding.inflate(layoutInflater,container,false)

        // Initialize Firebase Auth
        binding.btnAgregarUsuario.setOnClickListener {
            agregarUsuario()
        }
        // Initialize Firebase Auth
        auth = Firebase.auth
        val email = getUserInstance()
        if(email != null)
            obtenerUsuarioFirebase(email.toString())

        return binding.root

    }


    fun validarDatos(usuario: Usuario):Boolean{
        val contrasenia1 = binding.editTextContraseAEmpleado1.text.toString()
        val contrasenia2 = binding.editTextContraseAEmpleado2.text.toString()

        if(contrasenia1 != contrasenia2)
            return false
        if(usuario.razonSocial.toString().isEmpty())
            return false
        if(usuario.distrito.toString().isEmpty())
            return false
        if(usuario.horarioEntrada.toString().isEmpty())
            return false
        if(usuario.horarioSalida.toString().isEmpty())
            return false
        if(usuario.jefe.toString().isEmpty())
            return false

        return true
    }

    fun guardarUsuarioFirebase(usuario: Usuario){
        db.collection("usuarios").document(usuario.email).set(
            hashMapOf(
                "distrito" to usuario.distrito,
                "esAdmin" to  usuario.esAdmin,
                "horarioEntrada" to usuario.horarioEntrada,
                "horarioSalida" to usuario.horarioSalida,
                "jefe" to usuario.jefe,
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
        val usuario = Usuario(
            binding.editTextRazonSocial.text.toString(),
            binding.editTextEmail.text.toString(),
            binding.editTextDistrito.text.toString(),
            binding.editTextJefe.text.toString(),
            binding.editTextHorarioEntrada.text.toString(),
            binding.editTextHorarioSalida.text.toString(),
            bindingUsr.checkBoxEsAdmin.isChecked,
            ""
            )
        if(validarDatos(usuario)){
            guardarUsuarioFirebase(usuario)
            actualizarPassword()
        }
        else{
            Toast.makeText(binding.root.context,"Datos invalidos, no se pudo guardar el usuario", Toast.LENGTH_LONG ).show()
        }
    }

    fun actualizarPassword(){
        val contrasenia1 = binding.editTextContraseAEmpleado1.text.toString()
        val contrasenia2 = binding.editTextContraseAEmpleado2.text.toString()

        if(contrasenia1 == "" && contrasenia2 == "") {
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
                    val usuario = Usuario(document.getString("razonSocial"), email, document.getString("distrito"), document.getString("jefe"), document.getString("horarioEntrada"),document.getString("horarioSalida"), document.getBoolean("esAdmin"),"https://www.uclg-planning.org/sites/default/files/styles/featured_home_left/public/no-user-image-square.jpg?itok=PANMBJF-")
                    cargarCampos(usuario)
                }
        }
    }

    fun cargarCampos(usuario : Usuario){
        binding.editTextRazonSocial.setText(usuario.razonSocial)
        binding.editTextEmail.setText(usuario.email)
        binding.editTextDistrito.setText(usuario.jefe)
        binding.editTextJefe.setText(usuario.distrito)
        binding.editTextHorarioEntrada.setText(usuario.horarioEntrada)
        binding.editTextHorarioSalida.setText(usuario.horarioSalida)
    }

    fun getUserInstance(): String?{
        var email:String? = null
        val user : FirebaseUser? = FirebaseAuth.getInstance().currentUser
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