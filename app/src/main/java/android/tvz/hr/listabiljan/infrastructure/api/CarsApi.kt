package android.tvz.hr.listabiljan.infrastructure.api

import android.tvz.hr.listabiljan.infrastructure.models.Car
import retrofit2.http.GET
import retrofit2.http.Path

interface CarApi {
    @GET("/cars")
    suspend fun getCars(): List<Car>

    @GET("/cars/{id}")
    suspend fun getCarById(@Path("id") id: Int): Car
}