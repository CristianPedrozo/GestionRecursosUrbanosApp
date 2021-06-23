package com.example.recolectar_app.ui.viewModel.contenedor

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recolectar_app.domain.contenedoresRequests.DeleteContenedorUseCase
import com.example.recolectar_app.model.DeleteRequestModel
import kotlinx.coroutines.launch

class ContenedorDetalleVM: ViewModel()  {
    val deleteContenedorResult = MutableLiveData<Boolean>()
    val isLoading = MutableLiveData<Boolean>()
    val deleteContenedorUseCase = DeleteContenedorUseCase()

    fun deleteContenedor(deleteRequestModel: DeleteRequestModel){
        viewModelScope.launch {
            isLoading.postValue(true)
            val result = deleteContenedorUseCase(deleteRequestModel)
            deleteContenedorResult.postValue(result)
            isLoading.postValue(false)
        }
    }
}