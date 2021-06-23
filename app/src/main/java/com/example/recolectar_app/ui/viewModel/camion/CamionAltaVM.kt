package com.example.recolectar_app.ui.viewModel.camion

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recolectar_app.domain.camionesRequests.PostCamionUseCase
import com.example.recolectar_app.model.camion.CamionModel
import kotlinx.coroutines.launch

class CamionAltaVM: ViewModel() {
    val altaCamionResult = MutableLiveData<Boolean>()
    val isLoading = MutableLiveData<Boolean>()
    val postCamionUseCase = PostCamionUseCase()

    fun crearCamion(camion: CamionModel){
        viewModelScope.launch {
            isLoading.postValue(true)
            val result = postCamionUseCase(camion)
            altaCamionResult.postValue(result)
            isLoading.postValue(false)
        }
    }
}