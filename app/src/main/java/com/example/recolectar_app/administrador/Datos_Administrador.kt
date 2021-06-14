package com.example.recolectar_app.administrador
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.recolectar_app.R
import com.example.recolectar_app.Usuario
import com.example.recolectar_app.empleado.Datos
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class Datos_Administrador : Fragment() {
    private lateinit var v: View
    private lateinit var datos : TextView
    private lateinit var auth: FirebaseAuth

    val db = FirebaseFirestore.getInstance()
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
    ): View? {
        v = inflater.inflate(R.layout.fragment_usuarios_datos, container, false)
        val args = arguments?.let { Datos_AdministradorArgs.fromBundle(it) }
        val button = v.findViewById<Button>(R.id.btnAgregarUsuario)
        // Initialize Firebase Auth
        button.setOnClickListener {
            agregarUsuario()
        }
        var email = args?.email
        if(email != null && email != "")
            obtenerUsuarioFirebase(email.toString())

        return v

    }

    fun agregarUsuario(){
        var usuario = obtenerDatos()
        if(validarDatos()){
            guardarUsuarioFirebase(usuario)
            guardarUsuarioAuth(usuario)
        }
        else{
            Toast.makeText(v.context,"Datos invalidos, no se pudo guardar el usuario", Toast.LENGTH_LONG ).show()
        }
    }

    fun guardarUsuarioAuth(usuario:Usuario){
        val contrasenia = v.findViewById<EditText>(R.id.editTextContraseñaAdmin1).text.toString()
        var email = usuario.email

        auth.createUserWithEmailAndPassword(email, contrasenia)
            .addOnCompleteListener( ) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                } else {
                    //hacer otra cosa en error
                }
            }
    }

    fun validarDatos():Boolean{
        var usuario = obtenerDatos()
        var contrasenia1 = v.findViewById<EditText>(R.id.editTextContraseñaAdmin1).text.toString()
        var contrasenia2 = v.findViewById<EditText>(R.id.editTextContraseñaAdmin1).text.toString()

        if(contrasenia1 != contrasenia2)
            return false
        if(contrasenia1 == "" || contrasenia1 == null)
            return false
        if(contrasenia2 == "" || contrasenia2 == null)
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

    fun obtenerDatos(): Usuario {
        val razonSocial = v.findViewById<EditText>(R.id.editTextRazonSocial).text.toString()
        val email = v.findViewById<EditText>(R.id.editTextEmail).text.toString()
        val jefe = v.findViewById<EditText>(R.id.editTextJefe).text.toString()
        val distrito = v.findViewById<EditText>(R.id.editTextDistrito).text.toString()
        val horarioEntrada = v.findViewById<EditText>(R.id.editTextHorarioEntrada).text.toString()
        val horarioSalida = v.findViewById<EditText>(R.id.editTextHorarioSalida).text.toString()
        val esAdmin = v.findViewById<CheckBox>(R.id.checkBoxEsAdmin).isChecked()
        return Usuario(razonSocial, email ,distrito,jefe,horarioEntrada,horarioSalida,esAdmin,"")
    }

    fun cargarCampos(usuario : Usuario){
        v.findViewById<EditText>(R.id.editTextRazonSocial).setText(usuario.razonSocial)
        v.findViewById<EditText>(R.id.editTextEmail).setText(usuario.email)
        v.findViewById<EditText>(R.id.editTextJefe).setText(usuario.jefe)
        v.findViewById<EditText>(R.id.editTextDistrito).setText(usuario.distrito)
        v.findViewById<EditText>(R.id.editTextHorarioEntrada).setText(usuario.horarioEntrada)
        v.findViewById<EditText>(R.id.editTextHorarioSalida).setText(usuario.horarioSalida)
        v.findViewById<CheckBox>(R.id.checkBoxEsAdmin).isChecked = if(usuario.esAdmin != true) false else true
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