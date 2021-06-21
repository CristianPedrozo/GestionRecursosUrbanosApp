package com.example.testmvvm.ui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recolectar_app.domain.GetZonaByNameUseCase
import com.example.recolectar_app.model.zona.ZonaModel
import com.example.testmvvm.domain.GetZonasUseCase
import kotlinx.coroutines.launch

class ListZonaVM : ViewModel() {
    val zonas = MutableLiveData<List<ZonaModel>>()
    var getZonasUseCase = GetZonasUseCase()
    var getZonaByNameUseCase = GetZonaByNameUseCase()
    val isLoading = MutableLiveData<Boolean>()

    fun onCreate() {
        viewModelScope.launch {
            isLoading.postValue(true)
            val result = getZonasUseCase()
            if(!result.isNullOrEmpty()){
                zonas.postValue(result)
                isLoading.postValue(false)
            }
        }
    }

    fun buscarZona(name : String){
        viewModelScope.launch {
//            isLoading.postValue(true)
            val zona = GetZonaByNameUseCase().invoke(name)
            print("asdasd"+zona)
            print(zonas)
            zonas.postValue(zona)

//            isLoading.postValue(false)
        }
    }

}