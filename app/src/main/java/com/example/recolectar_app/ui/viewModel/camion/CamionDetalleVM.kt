package com.example.recolectar_app.ui.viewModel.camion

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recolectar_app.domain.camionesRequests.DeleteCamionUseCase
import com.example.recolectar_app.model.DeleteRequestModel
import kotlinx.coroutines.launch

class CamionDetalleVM: ViewModel() {
    val deleteCamionResult = MutableLiveData<Boolean>()
    val isLoading = MutableLiveData<Boolean>()
    val deleteCamionUseCase = DeleteCamionUseCase()

    fun deleteCamion(deleteRequestModel : DeleteRequestModel){
        viewModelScope.launch {
        isLoading.postValue(true)
        val result = deleteCamionUseCase(deleteRequestModel)
        deleteCamionResult.postValue(result)
        isLoading.postValue(false)
        }
    }
}