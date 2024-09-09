package com.jihan.noteapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jihan.noteapp.model.UserRequest
import com.jihan.noteapp.model.UserResponse
import com.jihan.noteapp.retrofit.UserApi
import com.jihan.noteapp.utils.NetworkResult
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject

class UserRepository @Inject constructor(private val userApi: UserApi) {

    private val _userResponseLiveData = MutableLiveData<NetworkResult<UserResponse>>()
    val userResponseLiveData: LiveData<NetworkResult<UserResponse>> get() = _userResponseLiveData

    //=============================  Register the user =============================
    suspend fun registerUser(request: UserRequest) {
        _userResponseLiveData.postValue(NetworkResult.Loading())
        try {
            val result = userApi.signup(request)
            handleResponse(result)
        }
        catch (e : Exception){
            _userResponseLiveData.postValue(NetworkResult.Error("Network Error Occurred: ${e.message}"))
        }
    }


    //==========================  Login the user =========================
    suspend fun loginUser(request: UserRequest) {
        _userResponseLiveData.postValue(NetworkResult.Loading())

        try {
            val result = userApi.signin(request)
            handleResponse(result)
        }
        catch (e : Exception){
            _userResponseLiveData.postValue(NetworkResult.Error("Network Error Occurred: ${e.message}"))
        }



    }


    // ======================================= Handling Response ===========================================
    private fun handleResponse(result: Response<UserResponse>) {
        if (result.isSuccessful && result.body() != null) {
            _userResponseLiveData.postValue(NetworkResult.Success(result.body()!!))
        } else if (result.errorBody() != null) {

            val errorMessage = JSONObject(result.errorBody()!!.charStream().readText())

            _userResponseLiveData.postValue(NetworkResult.Error(errorMessage.getString("message")))
        } else {
            _userResponseLiveData.postValue(NetworkResult.Error("Something Went Wrong"))
        }
    }

}