package com.example.vmessenger.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.vmessenger.MessageActivity
import com.example.vmessenger.MessageEntity
import com.example.vmessenger.databinding.ReceviemBinding
import com.example.vmessenger.databinding.SentmBinding
import com.example.vmessenger.databinding.SmallpartBinding
import com.google.firebase.auth.FirebaseAuth

class MessageAdapter(val context: Context,val messageList:ArrayList<MessageEntity>):RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val ITEM_SENT=1
    val ITEM_RECEIVE=2
    class SentViewHolder(val binding: SentmBinding): RecyclerView.ViewHolder(binding.root)
    class RecevieViewHolder(val binding: ReceviemBinding):RecyclerView.ViewHolder(binding.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if(viewType==1){
            return SentViewHolder(SentmBinding.inflate(LayoutInflater.from(parent.context),parent,false))
        }
        else{
            return RecevieViewHolder(ReceviemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
        }
    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
           val currentMessage=messageList[position]
           if(holder.javaClass==SentViewHolder::class.java){
               val viewHolder=holder as SentViewHolder
               viewHolder.binding.sentTextView.text=currentMessage.message
           }
        else{
             val viewHolder=holder as RecevieViewHolder
               viewHolder.binding.receiveTextView.text=currentMessage.message
        }
    }

    override fun getItemViewType(position: Int): Int {
        val currentMessage=messageList[position]
        if(FirebaseAuth.getInstance().currentUser?.uid==currentMessage.senderId){
            return ITEM_SENT
        }
        else{ return ITEM_RECEIVE}
    }
    override fun getItemCount(): Int {
        return messageList.size
    }
}