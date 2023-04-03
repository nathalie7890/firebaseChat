package com.example.firebasechatapp.view.fragments.auth

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.firebasechatapp.R
import com.example.firebasechatapp.databinding.FragmentSignupBinding
import com.example.firebasechatapp.view.fragments.BaseFragment
import com.example.firebasechatapp.viewModel.SignUpViewModel
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
