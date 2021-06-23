package com.example.recolectar_app.administrador
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.recolectar_app.R
import com.example.recolectar_app.Usuario
import com.example.recolectar_app.core.UsuarioGlobal
import com.example.recolectar_app.databinding.FragmentAdministradorMonitoreoBinding
import com.example.recolectar_app.databinding.FragmentUsuariosDatosBinding
import com.example.recolectar_app.empleado.Datos
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import java.lang.Exception

class Datos_Administrador : Fragment() {
    private val TAG = "Datos Adm Frag"
    private var _binding: FragmentUsuariosDatosBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    var estadoActualUsuario:Boolean? = false
    val db = FirebaseFirestore.getInstance()
    var esEdicion: Boolean = false
    private lateinit var thiscontext:Context
    override fun onCreate(savedInstanceState: Bundle?) {
        // Initialize Firebase Auth
        auth = Firebase.auth
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        try {
            _binding = FragmentUsuariosDatosBinding.inflate(layoutInflater,container,false)
        }catch (e: Exception){
            Log.e(TAG,"onCreateView",e)
        }
        val args = arguments?.let { Datos_AdministradorArgs.fromBundle(it) }
        // Initialize Firebase Auth
        binding.btnAgregarUsuario.setOnClickListener {
            agregarUsuario()
        }
        if (container != null) {
            thiscontext = container.context
        };
        val email = args?.email
        if(email != null && email != ""){
            esEdicion = true
            binding.titulo.text = "Editar Usuario"
            binding.btnAgregarUsuario.text = "Editar Usuario"
            obtenerUsuarioFirebase(email.toString())
        }

        return binding.root

    }

    fun agregarUsuario(){
        val usuario = Usuario(
            binding.editTextRazonSocial.text.toString(),
            binding.editTextUsuario.text.toString(),
            binding.editTextEmail.text.toString(),
            completarZona(binding.editTextZona.text.toString()),
            binding.editTextHorarioEntrada.text.toString(),
            binding.editTextHorarioSalida.text.toString(),
            binding.checkBoxEsAdmin.isChecked,
            estadoActualUsuario,
            ""
        )
        if(validarDatos(usuario)){
            usuario.usuario = asignaroCompletarUsuario(usuario.usuario)
            guardarUsuarioFirebase(usuario)
            guardarUsuarioAuth(usuario)
            redireccionarAUsuarios()
        }
        else{
            Toast.makeText(thiscontext,"Datos invalidos, no se pudo guardar el usuario", Toast.LENGTH_LONG ).show()
        }
    }

    fun completarZona(zona:String?):String?{
        var zonaAux:String? = null
        if(zona!= null && !zona.contains("zona:")){
            zonaAux = "zona:$zona"
        }
        return zonaAux
    }
    fun guardarUsuarioAuth(usuario:Usuario){
        var contraseña = binding.editTextContraseAAdmin1.text.toString()
        if(contraseña != ""){
            auth.createUserWithEmailAndPassword(usuario.usuario, contraseña)
                .addOnCompleteListener( ) { task ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                    } else {
                        //hacer otra cosa en error
                    }
                }
        }
    }

    fun validarDatos(usuario: Usuario):Boolean{
        val contrasenia1 = binding.editTextContraseAAdmin1.text.toString()
        val contrasenia2 = binding.editTextContraseAAdmin2.text.toString()

        if(usuario.email != "" && !usuario.email?.contains("@")!!)
            return false
        if(usuario.usuario.contains(" "))
            return false
        if(contrasenia1 != contrasenia2)
            return false
        if(esEdicion){
            if(contrasenia1 != contrasenia2)
                return false
        }
        else{
            if(contrasenia1 == "" || contrasenia1.contains(" "))
                return false
            if(contrasenia2 == "" || contrasenia2.contains(" "))
                return false
        }
        if(usuario.razonSocial.toString().isEmpty())
            return false
        if(usuario.zona.toString().isEmpty())
            return false
        if(usuario.horarioEntrada.toString().isEmpty())
            return false
        if(usuario.horarioSalida.toString().isEmpty())
            return false
        if(usuario.usuario.isEmpty())
            return false

        return true
    }

    fun obtenerUsuarioFirebase(usuario:String?){
        if(usuario != null){
            usuario.toString()
            db.collection("usuarios").document(usuario)
                .get()
                .addOnSuccessListener {document->
                    val usuario = Usuario(
                        document.getString("razonSocial"),
                        usuario,
                        document.getString("email"),
                        document.getString("zona"),
                        document.getString("horarioEntrada"),
                        document.getString("horarioSalida"),
                        document.getBoolean("esAdmin"),
                        document.getBoolean("estaActivo"),
                        "https://www.uclg-planning.org/sites/default/files/styles/featured_home_left/public/no-user-image-square.jpg?itok=PANMBJF-")
                    estadoActualUsuario = usuario.estaActivo
                    cargarCampos(usuario)
                }
        }
    }

    fun redireccionarAUsuarios(){
        val action = Datos_AdministradorDirections.datosToUsuario()
        binding.root.findNavController().navigate(action)
    }

    fun asignaroCompletarUsuario(usuario: String):String{
        var usuarioAsigned = ""
        if(!usuario.contains("@")){
            usuarioAsigned = "$usuario@fiware.com.ar"
        }
        else{
            usuarioAsigned = usuario
        }
        return usuarioAsigned
    }

    fun guardarUsuarioFirebase(usuario: Usuario){
        db.collection("usuarios").document(usuario.usuario).set(
            hashMapOf(
                "zona" to usuario.zona,
                "esAdmin" to  usuario.esAdmin,
                "horarioEntrada" to usuario.horarioEntrada,
                "horarioSalida" to usuario.horarioSalida,
                "email" to usuario.email,
                "estaActivo" to estadoActualUsuario,
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


    fun obtenerDatos(): Usuario {
        val razonSocial = binding.editTextRazonSocial.text.toString()
        val usuario = binding.editTextUsuario.text.toString()
        val zona = binding.editTextZona.text.toString()
        val email = binding.editTextEmail.text.toString()
        val horarioEntrada = binding.editTextHorarioEntrada.text.toString()
        val horarioSalida = binding.editTextHorarioSalida.text.toString()
        val esAdmin = binding.checkBoxEsAdmin.isChecked()
        return Usuario(razonSocial, usuario ,email, zona,horarioEntrada,horarioSalida,esAdmin,estadoActualUsuario,"")
    }

    fun cargarCampos(usuario : Usuario){
        binding.editTextRazonSocial.setText(usuario.razonSocial)
        binding.editTextEmail.setText(usuario.email)
        var usuarioLimpio= ""
        var index = usuario.usuario.indexOf('@')
        if(index > 0 && usuario.usuario.contains("fiware"))
            usuarioLimpio = usuario.usuario.substring(0,index)
        else
            usuarioLimpio = usuario.usuario
        binding.editTextUsuario.setText(usuarioLimpio)
        binding.editTextZona.setText(usuario.zona)
        binding.editTextHorarioEntrada.setText(usuario.horarioEntrada)
        binding.editTextHorarioSalida.setText(usuario.horarioSalida)
        binding.checkBoxEsAdmin.isChecked = if(usuario.esAdmin != true) false else true
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