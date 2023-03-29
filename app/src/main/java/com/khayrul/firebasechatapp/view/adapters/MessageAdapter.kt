package com.khayrul.firebasechatapp.view.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.khayrul.firebasechatapp.MyApplication
import com.khayrul.firebasechatapp.model.model.Message
import com.khayrul.firebasechatapp.utils.Utils.update
import com.khayrul.firebasechatapp.R
import com.khayrul.firebasechatapp.databinding.ItemLayoutMessageBinding

class MessageAdapter(
    private var items: MutableList<Message>,
    private val context: Context
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemLayoutMessageBinding.inflate(layoutInflater, parent, false)
        return if(viewType == INCOMING) {
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
        when(holder) {
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
        if(currentUsername != item.name) {
            return INCOMING
        }

        return OUTGOING
    }

    class ItemIncomingMessageHolder(val binding: ItemLayoutMessageBinding) :
        RecyclerView.ViewHolder(binding.root) {
            fun bind(message: Message) {
                binding.run {
                    cvMessage.setBackgroundResource(R.drawable.incoming_bubble)
                    tvUserName.text = message.name
                    tvMessage.text = message.message
                }
            }
        }

    class ItemOutgoingMessageHolder(val binding: ItemLayoutMessageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(message: Message) {
            binding.run {
                tvUserName.text = message.name
                tvMessage.text = message.message
            }
        }
    }


    companion object {
        const val INCOMING = 100
        const val OUTGOING = 101
    }
}
