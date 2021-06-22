package com.example.testmvvm.ui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recolectar_app.domain.contenedoresRequests.GetContenedoresByZonaUseCase
import com.example.recolectar_app.domain.zonasRequests.GetZonaByNameUseCase
import com.example.recolectar_app.model.zona.ZonaModel
import com.example.recolectar_app.domain.zonasRequests.GetZonasUseCase
import com.example.recolectar_app.model.contenedor.ContenedorModel
import com.example.recolectar_app.model.zona.ZonaProvider
import kotlinx.coroutines.launch

class ZonaListVM : ViewModel() {
    val zonasList = MutableLiveData<List<ZonaModel>>()
    var getZonasUseCase = GetZonasUseCase()
    var getZonaByNameUseCase = GetZonaByNameUseCase()
    var getContenedoresByZonaUseCase = GetContenedoresByZonaUseCase()
    val isLoading = MutableLiveData<Boolean>()

    fun onCreate() {
        viewModelScope.launch {
            isLoading.postValue(true)
            val zonas = getZonasUseCase()
            if(!zonas.isNullOrEmpty()){
                getContenedoresAsignados()
                zonasList.postValue(zonas)
                isLoading.postValue(false)
            }
        }
    }

    suspend fun getContenedoresAsignados(){
        for(i in ZonaProvider.zonas.indices){
            val string = "?type=WasteContainer&limit=1000&q=refZona==${ZonaProvider.zonas[i].id}"
            val contenedores = getContenedoresByZonaUseCase(string)
            ZonaProvider.zonas[i].setContenedores(contenedores as ArrayList<ContenedorModel>)
            print(ZonaProvider.zonas[i].contenedores.value)
        }
    }

    fun buscarZona(name : String){
        viewModelScope.launch {
            isLoading.postValue(true)
            val zona = getZonaByNameUseCase(name)
            print(zonasList)
            zonasList.postValue(zona)
            isLoading.postValue(false)
        }
    }

}