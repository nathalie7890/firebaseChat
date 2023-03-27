package com.example.firebasechatapp.ui.presentation.auth

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.firebasechatapp.R
import com.example.firebasechatapp.data.model.User
import com.example.firebasechatapp.databinding.FragmentSignupBinding
import com.example.firebasechatapp.ui.presentation.BaseFragment
import com.example.firebasechatapp.ui.viewModels.auth.SignUpViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignupFragment : BaseFragment<FragmentSignupBinding>() {
    override val viewModel: SignUpViewModel by viewModels()

    override fun getLayoutResource() = R.layout.fragment_signup

    override fun onBindView(view: View, savedInstanceState: Bundle?) {
        super.onBindView(view, savedInstanceState)
        binding?.run {
            btnSignup.setOnClickListener {
                val name = etUsername.text.toString()
                val email = etEmail.text.toString()
                val pass = etPassword.text.toString()

                viewModel.signUp(name, email, pass)
            }
        }
    }

    override fun onBindData(view: View) {
        super.onBindData(view)
        lifecycleScope.launch {
            viewModel.signupFinish.collect {
                val action = LoginFragmentDirections.toLoginFragment()
                navController.navigate(action)
            }
        }

    }
}
