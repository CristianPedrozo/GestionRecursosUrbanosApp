package com.example.recolectar_app.data.network

import com.example.recolectar_app.model.zona.ZonaModel
import com.google.gson.JsonObject
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface FiwareApiClient {
    //GET ZONAS
    @GET("/v2/entities/?type=Zona")
    suspend fun getAllZonas(): Response<List<ZonaModel>>

    @POST("/v2/entities")
    suspend fun postZona(json : JSONObject)

//
//    @GET("users")
//    fun searchUsers(@Query("name") searchText: String): Call<UserList>

    //GET 1 ZONA POR ID
    @GET("/v2/entities/{zona_nombre}")
    suspend fun getZonaByName(@Path("zona_nombre") zona_nombre: String): Response<List<ZonaModel>>


//    @POST("users")
//    fun createUser(@Body params: User): Call<UserResponse>
//
//    @PATCH("users/{user_id}")
//    fun updateUser(@Path("user_id") user_id: String, @Body params: User): Call<UserResponse>
//
//    @DELETE("users/{user_id}")
//    fun deleteUser(@Path("user_id") user_id: String): Call<UserResponse>
}