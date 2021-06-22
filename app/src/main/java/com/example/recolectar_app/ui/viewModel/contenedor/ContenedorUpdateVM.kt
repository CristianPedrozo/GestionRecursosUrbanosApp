package com.example.recolectar_app.ui.viewModel.contenedor

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recolectar_app.domain.contenedoresRequests.GetContenedorByIdUseCase
import com.example.recolectar_app.domain.contenedoresRequests.UpdateContenedorUseCase
import com.example.recolectar_app.domain.zonasRequests.UpdateZonaUseCase
import com.example.recolectar_app.model.UpdateRequestModel
import com.example.recolectar_app.model.camion.UpdateCamionRequestModel
import com.example.recolectar_app.model.contenedor.ContenedorModel
import com.example.recolectar_app.model.contenedor.UpdateContenedorRequestModel
import com.example.recolectar_app.model.zona.UpdateZonaRequestModel
import kotlinx.coroutines.launch

class ContenedorUpdateVM : ViewModel() {
    val contenedorUpdateData = MutableLiveData<Boolean>()
    val contenedorSelectedData = MutableLiveData<ContenedorModel>()
    val isLoading = MutableLiveData<Boolean>()
    val updateContenedorUseCase = UpdateContenedorUseCase()
    val getContenedorByIdUseCase = GetContenedorByIdUseCase()

    fun updateContenedor(updateRequestModel: UpdateRequestModel){
        viewModelScope.launch {
            isLoading.postValue(true)
            val result = updateContenedorUseCase(updateRequestModel)
            contenedorUpdateData.postValue(result)
            isLoading.postValue(false)
        }
    }

    fun getContenedorById(contenedorId : String){
        viewModelScope.launch {
            isLoading.postValue(true)
            val contenedor = getContenedorByIdUseCase(contenedorId)[0]
            contenedorSelectedData.postValue(contenedor)
            isLoading.postValue(false)
        }
    }
}