package com.example.house_analysis.data.api.service

import com.example.house_analysis.data.model.request.UserLoginModel
import com.example.house_analysis.data.model.request.UserRegisterModel
import com.example.house_analysis.data.model.response.TokenResponse
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("auth")
    fun loginUser(@Body userData: UserLoginModel): Observable<TokenResponse>

    @POST("auth/register")
    fun registerUser(@Body userData: UserRegisterModel): Observable<Response<Unit>>
}