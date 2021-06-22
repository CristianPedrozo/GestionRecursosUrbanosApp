package com.example.recolectar_app.data.network

import com.example.recolectar_app.model.contenedor.ContenedorModel
import com.example.recolectar_app.core.RetrofitHelper
import com.example.recolectar_app.model.DeleteRequestModel
import com.example.recolectar_app.model.UpdateRequestModel
import com.example.recolectar_app.model.contenedor.DeleteContenedorRequestModel
import com.example.recolectar_app.model.contenedor.UpdateContenedorRequestModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ContenedorService {
    private val retrofit = RetrofitHelper.getRetrofit()

    suspend fun getAllContenedores() : List<ContenedorModel>{
        return withContext(Dispatchers.IO){
            val response = retrofit.create(FiwareApiClient::class.java).getAllContenedores()
            response.body() ?: emptyList()
        }
    }

    suspend fun getContenedorById(id:String) : List<ContenedorModel>{
        return withContext(Dispatchers.IO){
            val response = retrofit.create(FiwareApiClient::class.java).getContenedorById(id)
            print(response.body())
            response.body() ?: emptyList()
        }
    }

    suspend fun getContenedorByZona(zona:String) : List<ContenedorModel>{
        return withContext(Dispatchers.IO){
            val response = retrofit.create(FiwareApiClient::class.java).getContenedorByZona(zona)
            response.body() ?: emptyList()
        }
    }

    suspend fun postNewContenedor(contenedorModel: ContenedorModel) : Boolean{
        return withContext(Dispatchers.IO){
            val response = retrofit.create(FiwareApiClient::class.java).postNewContenedor(contenedorModel)
            response.isSuccessful
        }
    }

    suspend fun updateContenedor(updateRequestModel: UpdateRequestModel) : Boolean{
        return withContext(Dispatchers.IO){
            val response = retrofit.create(FiwareApiClient::class.java).updateEntitie(updateRequestModel)
            print(response)
            response.isSuccessful
        }
    }
//    {"actionType":"append",
//        "entities":[
//        {
//            "dateLastEmptying":{
//            "type":"DateTime",
//            "value":"2017-06-21T15:05:59.408Z" },
//            "fillingLevel":{
//            "type":"Number",
//            "value":0.73},
//            "id":"wastecontainer:40",
//            "location":{
//            "type":"geo:json",
//            "value":{
//            "coordinates":[-34.587449,-58.4080017],
//            "type":"Point"}
//                       },
//            "nextActuationDeadline":{
//            "type":"DateTime",
//            "value":""},
//            "refVehicle":{
//            "type":"Relationship",
//            "value":"vehicle:vehicle:"},
//            "refZona":{
//            "type":"Relationship",
//            "value":"zona:7"},
//            "status":{
//            "type":"Text",
//            "value":"ok"},
//            "temperature":{
//            "type":"Number",
//            "value":23.0},
//            "type":"WasteContainer"}
//        ]
//    }

    suspend fun deleteContenedor(deleteRequestModel: DeleteRequestModel) : Boolean{
        return withContext(Dispatchers.IO){
            val response = retrofit.create(FiwareApiClient::class.java).deleteEntitie(deleteRequestModel)
            response.isSuccessful
        }
    }
}