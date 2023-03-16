package com.example.firebasechatapp.ui.presentation.auth

import androidx.fragment.app.viewModels
import com.example.firebasechatapp.R
import com.example.firebasechatapp.databinding.FragmentLoginBinding
import com.example.firebasechatapp.ui.presentation.BaseFragment
import com.example.firebasechatapp.ui.viewModels.auth.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>() {
    override val viewModel: LoginViewModel by viewModels()

    override fun getLayoutResource() = R.layout.fragment_login
}