package com.example.firebasechatapp.view.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.firebasechatapp.MyApplication
import com.example.firebasechatapp.R
import com.example.firebasechatapp.model.model.Message
import com.example.firebasechatapp.databinding.ItemLayoutMessageBinding
import com.example.firebasechatapp.utils.Utils.update

class MessageAdapter(
    private var items: MutableList<Message>,
    private val context: Context
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemLayoutMessageBinding.inflate(layoutInflater, parent, false)
        return if (viewType == INCOMING) {
            ItemIncomingMessageHolder(binding)
        } else {
            ItemOutgoingMessageHolder(binding)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]
        when (holder) {
            is ItemIncomingMessageHolder -> {
                holder.bind(item)
            }

            is ItemOutgoingMessageHolder -> {
                holder.bind(item)
            }
        }
    }


    fun setMessages(items: MutableList<Message>) {
        val oldItems = this.items
        this.items = items.toMutableList()
        update(oldItems, items) { message1, message2 ->
            message1.id == message2.id
        }
    }

    override fun getItemViewType(position: Int): Int {
        val item = items[position]
        val currentUsername = (context.applicationContext as MyApplication).username ?: ""

        if (currentUsername == item.name) {
            return INCOMING
        }

        return OUTGOING
    }

    class ItemIncomingMessageHolder(val binding: ItemLayoutMessageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(message: Message) {
            binding.run {
                tvUsername.text = message.name
                tvMessage.text = message.message
            }
        }
    }

    class ItemOutgoingMessageHolder(val binding: ItemLayoutMessageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(message: Message) {
            binding.run {
                cvMessage.setBackgroundResource(R.drawable.incoming_bubble)
                tvUsername.text = message.name
                tvMessage.text = message.message
            }
        }
    }

    companion object {
        val INCOMING = 100
        val OUTGOING = 101
    }
}
