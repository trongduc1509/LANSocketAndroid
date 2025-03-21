package com.example.lansocketandroid.presentation.widget

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lansocketandroid.R

class ChatAdapter() :
    RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {
    private val messages = mutableListOf<String>()

    class ChatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val messageTextView: TextView = itemView.findViewById(R.id.tvMessage)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateMessages(newMessages: List<String>) {
        messages.clear()
        messages.addAll(newMessages)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.message_ui, parent, false)
        return ChatViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        holder.messageTextView.text = messages[position]
    }

    override fun getItemCount(): Int = messages.size
}