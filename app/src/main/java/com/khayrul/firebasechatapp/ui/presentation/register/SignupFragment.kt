package com.khayrul.firebasechatapp.ui.presentation.register

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.khayrul.firebasechatapp.R
import com.khayrul.firebasechatapp.databinding.FragmentSignupBinding
import com.khayrul.firebasechatapp.ui.presentation.base.BaseFragment
import com.khayrul.firebasechatapp.ui.presentation.login.LoginFragmentDirections
import com.khayrul.firebasechatapp.ui.presentation.register.viewModel.SignUpViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignupFragment : BaseFragment<FragmentSignupBinding>() {
    override val viewModel: SignUpViewModel by viewModels()

    override fun getLayoutResource() = R.layout.fragment_signup

    override fun onBindView(view: View, savedInstanceState: Bundle?) {
        super.onBindView(view, savedInstanceState)
        binding?.viewModel = viewModel
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
