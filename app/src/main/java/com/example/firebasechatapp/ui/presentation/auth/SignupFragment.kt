package com.example.firebasechatapp.ui.presentation.auth

import androidx.fragment.app.viewModels
import com.example.firebasechatapp.R
import com.example.firebasechatapp.databinding.FragmentSignupBinding
import com.example.firebasechatapp.ui.presentation.BaseFragment
import com.example.firebasechatapp.ui.viewModels.auth.SignUpViewModel

class SignupFragment : BaseFragment<FragmentSignupBinding>() {
    override val viewModel: SignUpViewModel by viewModels()

    override fun getLayoutResource() = R.layout.fragment_signup
}
