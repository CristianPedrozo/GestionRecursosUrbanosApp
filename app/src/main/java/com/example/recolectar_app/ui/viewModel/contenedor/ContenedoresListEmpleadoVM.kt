package com.example.recolectar_app.ui.viewModel.contenedor

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recolectar_app.domain.contenedoresRequests.GetContenedorByIdUseCase
import com.example.recolectar_app.domain.contenedoresRequests.GetContenedoresByZonaUseCase
import com.example.recolectar_app.domain.zonasRequests.GetZonaByEmailUseCase
import com.example.recolectar_app.model.contenedor.ContenedorModel
import kotlinx.coroutines.launch

class ContenedoresListEmpleadoVM : ViewModel() {
    val _contenedores = MutableLiveData<List<ContenedorModel>>()
    val _zonaId = MutableLiveData<String>()
    val isLoading = MutableLiveData<Boolean>()
    val getContenedorById = GetContenedorByIdUseCase()
    val getContenedoresByZona = GetContenedoresByZonaUseCase()
    val getZonaByEmail = GetZonaByEmailUseCase()

    fun onCreate(email:String){
        viewModelScope.launch {
            isLoading.postValue(true)
            val zonaId = getZonaByEmail(email)[0].id
            _zonaId.postValue(zonaId)
            isLoading.postValue(false)
        }
    }

    fun buscarContenedorPorId(id : String){
        viewModelScope.launch {
            isLoading.postValue(true)
            val contenedor = getContenedorById(id)
            _contenedores.postValue(contenedor)
            isLoading.postValue(false)
        }
    }

    fun buscarContenedorPorZona(req:String){
        viewModelScope.launch {
            isLoading.postValue(true)
            val contendoresByZona = getContenedoresByZona(req)
            _contenedores.postValue(contendoresByZona)
            isLoading.postValue(false)
        }
    }

}