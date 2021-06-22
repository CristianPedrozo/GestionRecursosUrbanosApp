package com.example.recolectar_app.data.network

import com.example.recolectar_app.model.contenedor.ContenedorModel
import com.example.recolectar_app.model.zona.ZonaModel
import org.json.JSONObject
import retrofit2.Call
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
        //GET CONTENEDORES
        @GET("/v2/entities/?type=WasteContainer&limit=1000")
        suspend fun getAllContenedores(): Response<List<ContenedorModel>>
        //GET CONTENEDOR POR ZONA
        @GET("/v2/entities/{zona_id}")
        suspend fun getContenedorByZona(@Path("zona_id") zona_id : String): Response<List<ContenedorModel>>
        //GET CONTENDOR POR ID
        @GET("/v2/entities/{id}")
        suspend fun getContenedorById(@Path("id") id : String): Response<List<ContenedorModel>>
        // ALTA ZONA
        @POST("/v2/entities")
        suspend fun postNewZona(@Body params : ZonaModel)

//
//    @GET("users")
//    fun searchUsers(@Query("name") searchText: String): Call<UserList>



//    @POST("users")
//    fun createUser(@Body params: User): Call<UserResponse>
//
//    @PATCH("users/{user_id}")
//    fun updateUser(@Path("user_id") user_id: String, @Body params: User): Call<UserResponse>
//
//    @DELETE("users/{user_id}")
//    fun deleteUser(@Path("user_id") user_id: String): Call<UserResponse>
}