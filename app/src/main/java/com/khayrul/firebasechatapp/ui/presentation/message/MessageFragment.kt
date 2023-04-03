package com.khayrul.firebasechatapp.ui.presentation.message

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.khayrul.firebasechatapp.R
import com.khayrul.firebasechatapp.databinding.FragmentMessageBinding
import com.khayrul.firebasechatapp.ui.presentation.adapters.MessageAdapter
import com.khayrul.firebasechatapp.ui.presentation.base.BaseFragment
import com.khayrul.firebasechatapp.ui.presentation.message.viewModel.MessageViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MessageFragment : BaseFragment<FragmentMessageBinding>() {
    private lateinit var adapter: MessageAdapter
    override val viewModel: MessageViewModel by viewModels()
    private val args: MessageFragmentArgs by navArgs()

    override fun getLayoutResource() = R.layout.fragment_message

    override fun onBindView(view: View, savedInstanceState: Bundle?) {
        super.onBindView(view, savedInstanceState)
        binding?.viewModel = viewModel
        setupAdapter()

        binding?.run {
            btnCrash.setOnClickListener {
                throw RuntimeException("Test Crash")
            }
        }
    }

    override fun onBindData(view: View) {
        super.onBindData(view)

        viewModel.messages.asLiveData().observe(viewLifecycleOwner) {
            adapter.setMessages(it.toMutableList())
        }
    }

    private fun setupAdapter() {
        val layoutManager = LinearLayoutManager(requireContext())
        adapter = MessageAdapter(mutableListOf(), requireContext())

        binding?.rvMessages?.adapter = adapter
        binding?.rvMessages?.layoutManager = layoutManager
    }
}