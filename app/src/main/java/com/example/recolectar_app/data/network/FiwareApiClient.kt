package com.example.recolectar_app.data.network

import com.example.recolectar_app.model.DeleteRequestModel
import com.example.recolectar_app.model.UpdateRequestModel
import com.example.recolectar_app.model.camion.CamionModel
import com.example.recolectar_app.model.contenedor.ContenedorModel
import com.example.recolectar_app.model.zona.ZonaModel
import retrofit2.Response
import retrofit2.http.*

interface FiwareApiClient {
    //ZONAS

        //GET ZONAS
        @GET("/v2/entities/?type=Zona")
        suspend fun getAllZonas(): Response<List<ZonaModel>>
        //GET 1 ZONA POR NOMBRE
        @GET("/v2/entities/{zona_nombre}")
        suspend fun getZonaByName(@Path("zona_nombre") zona_nombre: String): Response<List<ZonaModel>>
        //GET 1 ZONA POR ID
        @GET("/v2/entities/{id}")
        suspend fun getZonaById(@Path("id") id: String): Response<List<ZonaModel>>
        // ALTA ZONA
        @POST("/v2/entities")
        suspend fun postNewZona(@Body params : ZonaModel) : Response<Unit>


   //CONTENEDORES

        //GET CONTENEDORES
        @GET("/v2/entities/?type=WasteContainer&limit=1000")
        suspend fun getAllContenedores(): Response<List<ContenedorModel>>
        //GET CONTENEDOR POR ZONA
        @GET("/v2/entities/{zona_id}")
        suspend fun getContenedorByZona(@Path("zona_id") zona_id : String): Response<List<ContenedorModel>>
        //GET CONTENDOR POR ID
        @GET("/v2/entities/{id}")
        suspend fun getContenedorById(@Path("id") id : String): Response<List<ContenedorModel>>
        // ALTA CONTENDOR
        @POST("/v2/entities")
        suspend fun postNewContenedor(@Body params : ContenedorModel) : Response<Unit>
        // UPDATE CONTENDOR



   //CAMIONES

        //GET CAMIONES
        @GET("/v2/entities/?type=Vehicle&limit=1000")
        suspend fun getAllCamiones(): Response<List<CamionModel>>
        //GET CAMIONES POR ZONA
        @GET("/v2/entities/{zona_id}")
        suspend fun getCamionByZona(@Path("zona_id") zona_id : String): Response<List<CamionModel>>
        //GET CAMIONES POR ID
        @GET("/v2/entities/{id}")
        suspend fun getCamionById(@Path("id") id : String): Response<List<CamionModel>>
        // ALTA CAMIONES
        @POST("/v2/entities")
        suspend fun postNewCamion(@Body params : CamionModel) : Response<Unit>


  //GENERIC
        //UPDATE
          @POST("/v2/op/update")
          suspend fun updateEntitie(@Body params : UpdateRequestModel) : Response<Unit>
        //DELETE
          @POST("/v2/op/update")
          suspend fun deleteEntitie(@Body params : DeleteRequestModel) : Response<Unit>

}