package com.example.recolectar_app.ui.viewModel.camion

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recolectar_app.domain.camionesRequests.GetCamionByIdUseCase
import com.example.recolectar_app.domain.camionesRequests.UpdateCamionUseCase
import com.example.recolectar_app.model.UpdateRequestModel
import com.example.recolectar_app.model.camion.CamionModel
import kotlinx.coroutines.launch

class CamionUpdateVM: ViewModel() {
    val updateCamionResult = MutableLiveData<Boolean>()
    val camionSelectedData = MutableLiveData<CamionModel>()
    val isLoading = MutableLiveData<Boolean>()
    val updateCamionUseCase = UpdateCamionUseCase()
    val getCamionByIdUseCase = GetCamionByIdUseCase()


    fun updateCamion(updateRequestModel : UpdateRequestModel){
        viewModelScope.launch {
            isLoading.postValue(true)
            val result = updateCamionUseCase(updateRequestModel)
            updateCamionResult.postValue(result)
            isLoading.postValue(false)
        }
    }

    fun getCamionById(camionId : String){
        viewModelScope.launch {
            isLoading.postValue(true)
            val camion = getCamionByIdUseCase(camionId)[0]
            camionSelectedData.postValue(camion)
            isLoading.postValue(false)
        }
    }

}

