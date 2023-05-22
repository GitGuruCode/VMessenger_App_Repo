package com.example.vmessenger.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.vmessenger.MessageActivity
import com.example.vmessenger.databinding.SmallpartBinding
import com.example.vmessenger.users.user
import com.google.firebase.auth.FirebaseAuth

class myAdapter(val context: Context, val userList:ArrayList<user>)
    : RecyclerView.Adapter<myAdapter.UserViewHolder>(){
    class UserViewHolder(val binding: SmallpartBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(SmallpartBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }
    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.binding.st.text=userList[position].name
        holder.binding.sst.text=userList[position].email
        Glide.with(context).load(userList[position].image).into(holder.binding.sp)
        holder.itemView.setOnClickListener{
            val intent=Intent(context,MessageActivity::class.java)
             intent.putExtra("name",userList[position].name)
            intent.putExtra("uid",userList[position].uid)
            context.startActivity(intent)
        }
    }
    override fun getItemCount(): Int {
        return userList.size
    }
}