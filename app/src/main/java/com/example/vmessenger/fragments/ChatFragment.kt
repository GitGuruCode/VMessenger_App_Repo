package com.example.vmessenger.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vmessenger.Adapter.myAdapter
import com.example.vmessenger.R
import com.example.vmessenger.databinding.FragmentChatBinding
import com.example.vmessenger.users.user
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.util.ArrayList


class ChatFragment : Fragment() {
    private lateinit var adapter: myAdapter
    private lateinit var userList: ArrayList<user>
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDbRef: DatabaseReference
    private lateinit var binding: FragmentChatBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentChatBinding.inflate(layoutInflater)
       //
       //return inflater.inflate(R.layout.fragment_chat, container, false)
        userList=ArrayList()
        adapter=myAdapter(requireContext(), userList  )
        mAuth= FirebaseAuth.getInstance()
        binding.recyclerView.layoutManager=LinearLayoutManager(requireContext())
        binding.recyclerView.adapter=adapter
        mDbRef= FirebaseDatabase.getInstance().getReference()
        mDbRef.child("userV").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                userList.clear()
                for (postSnapshot in snapshot.children){
                    val currentUser=postSnapshot.getValue(user::class.java)

                    if(mAuth.currentUser?.uid!=currentUser?.uid){
                        userList.add(currentUser!!)
                    }

                }
                adapter.notifyDataSetChanged()
            }
            override fun onCancelled(error: DatabaseError) {

            }
        })

        return  binding.root
    }
}