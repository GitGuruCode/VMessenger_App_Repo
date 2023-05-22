package com.example.vmessenger

import android.content.Intent
import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vmessenger.Adapter.MessageAdapter
import com.example.vmessenger.databinding.ActivityMessageBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class MessageActivity : AppCompatActivity() {
    lateinit var binding: ActivityMessageBinding
    lateinit var messageAdapter: MessageAdapter
    lateinit var messageList: ArrayList<MessageEntity>
    lateinit var DBref:DatabaseReference
    var senderRoom:String?=null
    var receiverRoom:String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // setContentView(R.layout.activity_message)
        binding=ActivityMessageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val name=intent.getStringExtra("name")
        val ReceiverUid=intent.getStringExtra("uid")
        val SenderUid=FirebaseAuth.getInstance().currentUser!!.uid
        DBref= FirebaseDatabase.getInstance().getReference()
        supportActionBar?.title=name
        messageList=ArrayList()
        messageAdapter=MessageAdapter(this,messageList)
        senderRoom=ReceiverUid+ SenderUid
        receiverRoom=SenderUid+ReceiverUid
        binding.recyclerViewMessage.layoutManager=LinearLayoutManager(this)
        binding.recyclerViewMessage.adapter=messageAdapter
        DBref.child("chats").child(senderRoom!!).child("messages").addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                messageList.clear()
                for(postSnapShot in snapshot.children){
                    val message=postSnapShot.getValue(MessageEntity::class.java)
                    messageList.add(message!!)
                }
                messageAdapter.notifyDataSetChanged()
            }
            override fun onCancelled(error: DatabaseError) {

            }

        })
        binding.send.setOnClickListener{
            DBref.child("chats").child(senderRoom!!).child("messages").push().setValue(MessageEntity(binding.typeamessage.text.toString(), SenderUid))
                .addOnSuccessListener {
                    DBref.child("chats").child(receiverRoom!!).child("messages").push().setValue(MessageEntity(binding.typeamessage.text.toString(), ReceiverUid!!))
                }
            binding.typeamessage.setText("")
        }
    }
}