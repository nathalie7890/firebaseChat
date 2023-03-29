package com.khayrul.firebasechatapp.view.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.khayrul.firebasechatapp.R
import com.khayrul.firebasechatapp.model.model.User
import com.khayrul.firebasechatapp.databinding.FragmentHomeBinding
import com.khayrul.firebasechatapp.view.adapters.ChatAdapter
import com.khayrul.firebasechatapp.viewModel.HomeViewModel
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

    private fun setupAdapter() {
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