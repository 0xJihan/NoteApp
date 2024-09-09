package com.jihan.noteapp.retrofit

import com.jihan.noteapp.model.UserRequest
import com.jihan.noteapp.model.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserApi {

    @POST("users/signup")
    suspend fun signup( @Body request: UserRequest) : Response<UserResponse>

       @POST("users/signin")
    suspend fun signin( @Body request: UserRequest) : Response<UserResponse>



}