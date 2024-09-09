package com.jihan.noteapp.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.jihan.noteapp.R
import com.jihan.noteapp.databinding.FragmentRegisterBinding
import com.jihan.noteapp.model.UserRequest
import com.jihan.noteapp.utils.HelperClass
import com.jihan.noteapp.utils.NetworkResult
import com.jihan.noteapp.utils.TokenManager
import com.jihan.noteapp.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = checkNotNull(_binding)
    private val viewModel by viewModels<AuthViewModel>()
    @Inject lateinit var tokenManager: TokenManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        if (!tokenManager.getToken().isNullOrBlank()){
            findNavController().navigate(R.id.action_registerFragment_to_mainFragment)
        }

        _binding = FragmentRegisterBinding.inflate(inflater, container, false)


        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        // Sign Up button setOnClick listener
        binding.btnSignUp.setOnClickListener {
            getUserRequest()
            val pair = validateUserInput()

            when (pair.first){
                true -> {
                     viewModel.registerUser(getUserRequest())
                }
                false -> {
                    binding.txtError.setTextColor(Color.RED)
                    binding.txtError.text = pair.second
                }
            }
        }


        // Login button setOnClick listener
        binding.btnLogin.setOnClickListener {
             findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }



        // Observing NetworkResponse
        bindNetworkResponseObserver()
    }

    private fun getUserRequest(): UserRequest {
        val userName = binding.edUsername.text.toString()
        val email = binding.edEmail.text.toString()
        val password = binding.edPassword.text.toString()

        return UserRequest(username = userName, email = email, password = password)
    }

    private fun validateUserInput(): Pair<Boolean, String> {

        val userRequest = getUserRequest()

        return HelperClass().validateUserCredentials(userName = userRequest.username, email = userRequest.email, password = userRequest.password)

    }


    private fun bindNetworkResponseObserver() {
        viewModel.userResponse.observe(viewLifecycleOwner) {
            binding.progressBar.isVisible = false

            when (it) {

                is NetworkResult.Loading -> {
                    binding.progressBar.isVisible = true
                }

                is NetworkResult.Success -> {
                    tokenManager.saveToken(it.data!!.token)
                    Toast.makeText(requireContext(), "Account created successfully", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_registerFragment_to_mainFragment)
                }

                is NetworkResult.Error -> {
                    binding.txtError.text = it.message
                }
            }
        }
    }

    // prevent memory leaks
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}