package com.example.firebasechatapp.ui.presentation

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.firebasechatapp.R
import com.example.firebasechatapp.data.model.Chat
import com.example.firebasechatapp.data.model.User
import com.example.firebasechatapp.databinding.FragmentHomeBinding
import com.example.firebasechatapp.ui.adapters.ChatAdapter
import com.example.firebasechatapp.ui.viewModels.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    private lateinit var adapter: ChatAdapter
    override val viewModel: HomeViewModel by viewModels()
    override fun getLayoutResource() = R.layout.fragment_home

    override fun onBindView(view: View, savedInstanceState: Bundle?) {
        super.onBindView(view, savedInstanceState)

        setupAdapter()

        binding?.run {
//            btnAdd.setOnClickListener {
//                val action = HomeFragmentDirections.actionHomeToMessage()
//                NavHostFragment.findNavController(this@HomeFragment).navigate(action)
//            }
        }
    }

    override fun onBindData(view: View) {
        super.onBindData(view)
        viewModel.users.observe(viewLifecycleOwner) {
            adapter.setChats(it.toMutableList())
        }
    }

    fun setupAdapter() {
        val layoutManager = LinearLayoutManager(requireContext())
        adapter = ChatAdapter(mutableListOf())
        adapter.listener = object : ChatAdapter.Listener {
            override fun onClick(item: User) {
                val action = item.id.let { HomeFragmentDirections.actionHomeToMessage(item.id) }
                NavHostFragment.findNavController(this@HomeFragment).navigate(action)
            }
        }

        binding?.rvChat?.adapter = adapter
        binding?.rvChat?.layoutManager = layoutManager
    }
}