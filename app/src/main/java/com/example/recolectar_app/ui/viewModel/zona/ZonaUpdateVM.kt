package com.example.recolectar_app.ui.viewModel.zona

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recolectar_app.domain.zonasRequests.GetZonaByIdUseCase
import com.example.recolectar_app.domain.zonasRequests.UpdateZonaUseCase
import com.example.recolectar_app.model.UpdateRequestModel
import com.example.recolectar_app.model.zona.ZonaModel
import kotlinx.coroutines.launch

class ZonaUpdateVM : ViewModel() {
    val zonaUpdateResult = MutableLiveData<Boolean>()
    val zonaSelectedData = MutableLiveData<ZonaModel>()
    val isLoading = MutableLiveData<Boolean>()
    val updateZonaUseCase = UpdateZonaUseCase()
    val getZonaByIdUseCase = GetZonaByIdUseCase()

    fun updateZona(updateRequestModel: UpdateRequestModel){
        viewModelScope.launch {
            isLoading.postValue(true)
            val result = updateZonaUseCase(updateRequestModel)
            zonaUpdateResult.postValue(result)
            isLoading.postValue(false)
        }
    }

    fun getZonaById(id:String){
        viewModelScope.launch {
            isLoading.postValue(true)
            val zona = getZonaByIdUseCase(id)[0]
            zonaSelectedData.postValue(zona)
            isLoading.postValue(false)
        }
    }



}