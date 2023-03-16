package com.example.firebasechatapp.ui.presentation.auth

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.firebasechatapp.R
import com.example.firebasechatapp.databinding.FragmentLoginBinding
import com.example.firebasechatapp.ui.presentation.BaseFragment
import com.example.firebasechatapp.ui.viewModels.auth.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>() {
    override val viewModel: LoginViewModel by viewModels()
    override fun getLayoutResource() = R.layout.fragment_login

    override fun onBindView(view: View, savedInstanceState: Bundle?) {
        super.onBindView(view, savedInstanceState)
        binding?.run {
            btnLogin.setOnClickListener {
                val email = etEmail.text.toString()
                val pass = etPassword.text.toString()
                viewModel.login(email, pass)
            }
        }
    }

    override fun onBindData(view: View) {
        super.onBindData(view)

        lifecycleScope.launch {
            viewModel.loginFinish.collect {
                val action = LoginFragmentDirections.toHomeFragment()
                navController.navigate(action)
            }
        }
        binding?.btnSignup?.setOnClickListener {
            val action = LoginFragmentDirections.actionLoginToSignup()
            navController.navigate(action)

        }
    }
}