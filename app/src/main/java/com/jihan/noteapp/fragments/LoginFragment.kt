package com.jihan.noteapp.fragments

import android.graphics.Color
import android.os.Bundle
import android.provider.CalendarContract.Colors
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.jihan.noteapp.R
import com.jihan.noteapp.databinding.FragmentLoginBinding
import com.jihan.noteapp.model.UserRequest
import com.jihan.noteapp.utils.HelperClass
import com.jihan.noteapp.utils.NetworkResult
import com.jihan.noteapp.utils.TokenManager
import com.jihan.noteapp.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class LoginFragment : Fragment() {


    private var _binding: FragmentLoginBinding? = null
    private val binding get() = checkNotNull(_binding)
    private val viewModel by viewModels<AuthViewModel>()
    @Inject lateinit var tokenManager: TokenManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)


        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.btnSignUp.setOnClickListener{
            findNavController().popBackStack()
        }


        binding.btnLogin.setOnClickListener {
            val pair = validateUserInput()
            when (pair.first){
                true -> {
                    viewModel.loginUser(getUserRequest())
                }
                false -> {
                    binding.txtError.text = pair.second
                }
            }
            //findNavController().navigate(R.id.action_loginFragment_to_mainFragment)
        }


        bindNetworkResponseObserver()

    }

    private fun validateUserInput(): Pair<Boolean, String> {

        val userRequest = getUserRequest()

        return HelperClass().validateUserCredentials(
            email = userRequest.email,
            password = userRequest.password
        )

    }

    private fun getUserRequest(): UserRequest {
        val email = binding.edEmail.text.toString()
        val password = binding.edPassword.text.toString()

        return UserRequest(username = "", email = email, password = password)
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
                    findNavController().navigate(R.id.action_loginFragment_to_mainFragment)
                    Toast.makeText(requireContext(), "User Login Successfully", Toast.LENGTH_SHORT)
                        .show()
                }

                is NetworkResult.Error -> {
                    binding.txtError.setTextColor(Color.RED)
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