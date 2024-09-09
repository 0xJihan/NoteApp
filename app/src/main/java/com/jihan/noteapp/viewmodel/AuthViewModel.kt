package com.jihan.noteapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jihan.noteapp.model.UserRequest
import com.jihan.noteapp.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {

    val userResponse get() = userRepository.userResponseLiveData

    fun registerUser(userRequest: UserRequest){
        viewModelScope.launch (Dispatchers.IO) {
            userRepository.registerUser(userRequest)
        }
    }
    fun loginUser(userRequest: UserRequest){
        viewModelScope.launch (Dispatchers.IO) {
            userRepository.loginUser(userRequest)
        }
    }


}