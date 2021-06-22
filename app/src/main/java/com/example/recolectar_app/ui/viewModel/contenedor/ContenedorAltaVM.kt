package com.example.recolectar_app.ui.viewModel.contenedor

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recolectar_app.domain.contenedoresRequests.PostContenedorUseCase
import com.example.recolectar_app.model.contenedor.ContenedorModel
import kotlinx.coroutines.launch

class ContenedorAltaVM: ViewModel()  {
    val altaContenedorData = MutableLiveData<Boolean>()
    val isLoading = MutableLiveData<Boolean>()
    val postContenedorUseCase = PostContenedorUseCase()

    fun crearContenedor(contenedor: ContenedorModel){
        viewModelScope.launch {
            isLoading.postValue(true)
            val result = postContenedorUseCase(contenedor)
            altaContenedorData.postValue(result)
            isLoading.postValue(false)
        }

    }
}