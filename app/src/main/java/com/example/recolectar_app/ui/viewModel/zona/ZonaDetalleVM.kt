package com.example.recolectar_app.ui.viewModel.zona

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recolectar_app.domain.zonasRequests.DeleteZonaUseCase
import com.example.recolectar_app.model.zona.DeleteZonaRequestModel
import kotlinx.coroutines.launch

class ZonaDetalleVM: ViewModel(){
    val deleteZonaData = MutableLiveData<Boolean>()
    val isLoading = MutableLiveData<Boolean>()
    val deleteZonaUseCase = DeleteZonaUseCase()

    fun deleteZona(deleteZonaRequestModel: DeleteZonaRequestModel){
        viewModelScope.launch {
            isLoading.postValue(true)
            val result = deleteZonaUseCase(deleteZonaRequestModel)
            deleteZonaData.postValue(result)
            isLoading.postValue(false)
        }
    }
}