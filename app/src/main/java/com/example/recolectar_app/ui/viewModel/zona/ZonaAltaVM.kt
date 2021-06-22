package com.example.recolectar_app.ui.viewModel.zona

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recolectar_app.domain.zonasRequests.PostZonaUseCase
import com.example.recolectar_app.model.zona.ZonaModel
import kotlinx.coroutines.launch

class ZonaAltaVM : ViewModel() {
    val altaZonaData = MutableLiveData<Boolean>()
    val isLoading = MutableLiveData<Boolean>()
    val postZonaUseCase = PostZonaUseCase()

    fun crearZona(zona:ZonaModel){
        viewModelScope.launch {
            isLoading.postValue(true)
            val result = postZonaUseCase(zona)
            altaZonaData.postValue(result)
            isLoading.postValue(false)
        }

    }
}