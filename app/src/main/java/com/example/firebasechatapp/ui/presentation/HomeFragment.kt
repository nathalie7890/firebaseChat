package com.example.firebasechatapp.ui.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.firebasechatapp.R
import com.example.firebasechatapp.databinding.FragmentHomeBinding
import com.example.firebasechatapp.ui.viewModels.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    override val viewModel: HomeViewModel by viewModels()
    override fun getLayoutResource() = R.layout.fragment_home

    override fun onBindView(view: View, savedInstanceState: Bundle?) {
        super.onBindView(view, savedInstanceState)

        binding?.run {
            btnAdd.setOnClickListener {
                viewModel.addMessage()
            }
        }
    }

    override fun onBindData(view: View) {
        super.onBindData(view)
    }
}