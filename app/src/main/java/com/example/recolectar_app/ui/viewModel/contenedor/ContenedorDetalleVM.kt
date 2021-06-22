package com.example.recolectar_app.ui.viewModel.contenedor

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recolectar_app.domain.contenedoresRequests.DeleteContenedorUseCase
import com.example.recolectar_app.model.contenedor.DeleteContenedorRequestModel
import kotlinx.coroutines.launch

class ContenedorDetalleVM: ViewModel()  {
    val deleteContenedorData = MutableLiveData<Boolean>()
    val isLoading = MutableLiveData<Boolean>()
    val deleteContenedorUseCase = DeleteContenedorUseCase()

    fun deleteContenedor(deleteContenedorRequestModel: DeleteContenedorRequestModel){
        viewModelScope.launch {
            isLoading.postValue(true)
            val result = deleteContenedorUseCase(deleteContenedorRequestModel)
            deleteContenedorData.postValue(result)
            isLoading.postValue(false)
        }
    }
}