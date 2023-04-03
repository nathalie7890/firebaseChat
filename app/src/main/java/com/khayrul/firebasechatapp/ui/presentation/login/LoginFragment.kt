package com.khayrul.firebasechatapp.ui.presentation.login

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.khayrul.firebasechatapp.MyApplication
import com.khayrul.firebasechatapp.ui.presentation.base.BaseFragment
import com.khayrul.firebasechatapp.ui.presentation.login.viewModel.LoginViewModel
import com.khayrul.firebasechatapp.R
import com.khayrul.firebasechatapp.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>() {
    override val viewModel: LoginViewModel by viewModels()
    override fun getLayoutResource() = R.layout.fragment_login

    override fun onBindView(view: View, savedInstanceState: Bundle?) {
        super.onBindView(view, savedInstanceState)
        binding?.viewModel = viewModel

        binding?.run {
            btnSignup.setOnClickListener {
                navigateToSignUp()
            }
        }
    }

    override fun onBindData(view: View) {
        super.onBindData(view)

        lifecycleScope.launch {
            viewModel.loginFinish.collect {
                navigateToHome()
            }
        }
    }

    private fun navigateToSignUp() {
        val action = LoginFragmentDirections.actionLoginToSignup()
        navController.navigate(action)
    }

    private fun navigateToHome() {
        (requireContext().applicationContext as MyApplication).fetchUserName()
        val action = LoginFragmentDirections.toHomeFragment()
        navController.navigate(action)
    }
}