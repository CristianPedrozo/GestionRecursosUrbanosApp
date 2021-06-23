package com.example.recolectar_app.ui.viewModel.camion

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recolectar_app.domain.camionesRequests.GetCamionByIdUseCase
import com.example.recolectar_app.domain.camionesRequests.GetCamionesByZonaUseCase
import com.example.recolectar_app.domain.camionesRequests.GetCamionesUseCase
import com.example.recolectar_app.model.camion.CamionModel
import com.example.recolectar_app.model.zona.ZonaModel
import kotlinx.coroutines.launch

class CamionListVM : ViewModel() {
    val _camiones = MutableLiveData<List<CamionModel>>()
    val isLoading = MutableLiveData<Boolean>()
    val getCamionesUseCase = GetCamionesUseCase()
    val getCamionByIdUseCase = GetCamionByIdUseCase()
    val getCamionesByZonaUseCase = GetCamionesByZonaUseCase()

    fun onCreate(){
        viewModelScope.launch {
            isLoading.postValue(true)
            val camiones = getCamionesUseCase()
            _camiones.postValue(camiones)
            isLoading.postValue(false)
        }
    }

    fun buscarCamionById(id:String){
        viewModelScope.launch {
            isLoading.postValue(true)
            val camion = getCamionByIdUseCase(id)
            _camiones.postValue(camion)
            isLoading.postValue(false)
        }
    }

    fun buscarCamionByZona(zonaId : String){
        viewModelScope.launch {
            isLoading.postValue(true)
            val camionesByZona = getCamionesByZonaUseCase(zonaId)
            _camiones.postValue(camionesByZona)
            isLoading.postValue(false)
        }
    }

}