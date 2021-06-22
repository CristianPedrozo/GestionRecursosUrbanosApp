package com.example.recolectar_app.ui.viewModel.zona

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recolectar_app.domain.zonasRequests.UpdateZonaUseCase
import com.example.recolectar_app.model.UpdateRequestModel
import com.example.recolectar_app.model.zona.UpdateZonaRequestModel
import kotlinx.coroutines.launch

class ZonaUpdateVM : ViewModel() {
    val zonaUpdateData = MutableLiveData<Boolean>()
    val isLoading = MutableLiveData<Boolean>()
    val updateZonaUseCase = UpdateZonaUseCase()

    fun updateZona(updateRequestModel: UpdateRequestModel){
        viewModelScope.launch {
            isLoading.postValue(true)
            val result = updateZonaUseCase(updateRequestModel)
            zonaUpdateData.postValue(result)
            isLoading.postValue(false)
        }
    }

}