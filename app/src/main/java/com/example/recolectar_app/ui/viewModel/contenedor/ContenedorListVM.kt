package com.example.recolectar_app.ui.viewModel.contenedor

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recolectar_app.domain.contenedoresRequests.GetContenedorByIdUseCase
import com.example.recolectar_app.domain.contenedoresRequests.GetContenedoresByZonaUseCase
import com.example.recolectar_app.domain.contenedoresRequests.GetContenedoresUseCase
import com.example.recolectar_app.model.contenedor.ContenedorModel
import kotlinx.coroutines.launch

class ContenedorListVM : ViewModel(){
    val _contenedores = MutableLiveData<List<ContenedorModel>>()
    val isLoading = MutableLiveData<Boolean>()
    val getContenedoresUseCase = GetContenedoresUseCase()
    val getContenedorById = GetContenedorByIdUseCase()
    val getContenedoresByZona = GetContenedoresByZonaUseCase()

    fun onCreate(){
        viewModelScope.launch {
            isLoading.postValue(true)
            val contenedores = getContenedoresUseCase()
            _contenedores.postValue(contenedores)
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
    
    fun buscarContenedorPorZona(zonaId:String){
        viewModelScope.launch {
            isLoading.postValue(true)
            val contendoresByZona = getContenedoresByZona(zonaId)
            _contenedores.postValue(contendoresByZona)
            isLoading.postValue(false)
        }
    }

}