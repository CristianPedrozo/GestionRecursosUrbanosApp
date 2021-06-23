package com.example.recolectar_app.empleado

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.recolectar_app.ui.view.MainActivity
import com.example.recolectar_app.databinding.FragmentPerfilBinding
import com.example.recolectar_app.entities.HorarioRegistro
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.*


class perfil : Fragment() {
    private var _binding: FragmentPerfilBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    val db = FirebaseFirestore.getInstance()
    val TIPO_REGISTRO_ENTRADA = "Entrada"
    val TIPO_REGISTRO_SALIDA = "Salida"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPerfilBinding.inflate(layoutInflater,container,false)

        binding.btnLogOutEmpleado.setOnClickListener{
            logOut()
        }
        binding.btnEntrada.setOnClickListener{
            registrar(TIPO_REGISTRO_ENTRADA)
        }
        binding.btnSalida.setOnClickListener{
            registrar(TIPO_REGISTRO_SALIDA)
        }
        obtenerEntrada()
        obtenerSalida()
        return binding.root
    }

    fun obtenerFecha():String{
        val sdf = SimpleDateFormat("dd/MM/yyyy")
        return sdf.format(Date())
    }

    fun registrar(tipoRegistro: String){
        var usuario = getUserInstance()
        val rightNow: Calendar = Calendar.getInstance()
        val currentHourIn24Format: Int = rightNow.get(Calendar.HOUR_OF_DAY)
        val currentMinute: Int = rightNow.get(Calendar.MINUTE)
        val currentDate = obtenerFecha()

        db.collection("horarios_"+tipoRegistro).document(usuario?.email.toString()).set(
            hashMapOf(
                "horario" to  "$currentHourIn24Format:$currentMinute",
                "fecha" to currentDate,
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
        if(tipoRegistro == TIPO_REGISTRO_ENTRADA){
            binding.btnEntrada.isEnabled = false
            binding.btnSalida.isEnabled = true
            mostrarEntrada("$currentDate  $currentHourIn24Format:$currentMinute hs")
        }
        else{
            binding.btnSalida.isEnabled = false
            binding.btnEntrada.isEnabled = true
            mostrarSalida("$currentDate  $currentHourIn24Format:$currentMinute hs")
        }
    }

    fun mostrarEntrada(registro:String){
        if(registro.contains(obtenerFecha()))
            binding.viewEntradas.text = binding.viewEntradas.text.toString() + "\n"+ registro
    }

    fun mostrarSalida(registro:String){
        if(registro.contains(obtenerFecha()))
            binding.viewSalidas.text = binding.viewSalidas.text.toString() + "\n"+ registro
    }

    fun obtenerEntrada(){
        var usuarioActual = getUserInstance()
         db.collection("horarios_"+ TIPO_REGISTRO_ENTRADA).document(usuarioActual?.email.toString())
            .get()
            .addOnSuccessListener { result ->
                var fecha = result.getString("fecha")
                var hora = result.getString("horario")
                if(fecha == obtenerFecha()){
                    var registroEntrada = HorarioRegistro(
                        fecha,
                        hora)
                    mostrarEntrada(registroEntrada.fecha +" "+registroEntrada.horario + "hs")
                    deshabilitarBotonCorrespondiente("$fecha $hora")
                }
            }
    }
    fun deshabilitarBotonCorrespondiente(ultimaEntrada:String?){
        var usuarioActual = getUserInstance()
        db.collection("horarios_$TIPO_REGISTRO_SALIDA").document(usuarioActual?.email.toString())
            .get()
            .addOnSuccessListener { result ->
                var fecha = result.getString("fecha")
                var hora = result.getString("horario")
                if(fecha == obtenerFecha()){
                    val sdfEntrada = SimpleDateFormat("dd/MM/yyyy HH:mm")
                    val entradaDate = sdfEntrada.parse(ultimaEntrada)
                    val sdfSalida = SimpleDateFormat("dd/MM/yyyy HH:mm")
                    val salidaDate = sdfSalida.parse("$fecha $hora")
                        if(entradaDate > salidaDate)
                            binding.btnEntrada.isEnabled = false
                        else
                            binding.btnSalida.isEnabled = false
                }
            }
    }

    fun obtenerSalida(){
        var usuarioActual = getUserInstance()
        db.collection("horarios_"+ TIPO_REGISTRO_SALIDA).document(usuarioActual?.email.toString())
            .get()
            .addOnSuccessListener { result ->
                var fecha = result.getString("fecha")
                if(fecha == obtenerFecha()){
                   var registroSalida = HorarioRegistro(
                        fecha,
                        result.getString("horario"))
                    mostrarSalida(registroSalida.fecha +" "+ registroSalida.horario + "hs")
                }
            }
    }

    fun getUserInstance(): FirebaseUser?{
        return FirebaseAuth.getInstance().currentUser
    }

    fun logOut(){
        Firebase.auth.signOut()
        val intent = Intent(binding.root.context, MainActivity::class.java)
        startActivity(intent)
    }
}