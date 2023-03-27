package com.khayrul.firebasechatapp.ui.presentation.auth

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.khayrul.firebasechatapp.R
import com.khayrul.firebasechatapp.databinding.FragmentSignupBinding
import com.khayrul.firebasechatapp.ui.presentation.BaseFragment
import com.khayrul.firebasechatapp.ui.viewModels.auth.SignUpViewModel
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
