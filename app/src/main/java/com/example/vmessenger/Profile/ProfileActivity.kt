package com.example.vmessenger.Profile
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import com.example.vmessenger.databinding.ProfileLayoutBinding
import com.example.vmessenger.users.user
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import com.google.firebase.storage.FirebaseStorage

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ProfileLayoutBinding
    private lateinit var mauth: FirebaseAuth
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var firebaseStorage: FirebaseStorage
    private lateinit var databaseReference: DatabaseReference
   private lateinit var imageUri: Uri
   private lateinit var dialog: AlertDialog.Builder
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ProfileLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dialog=AlertDialog.Builder(this).setMessage("Upadting Profile").setCancelable(false)
        firebaseDatabase=FirebaseDatabase.getInstance()
        firebaseStorage=FirebaseStorage.getInstance()
        databaseReference=FirebaseDatabase.getInstance().getReference()
        mauth= FirebaseAuth.getInstance()
        binding.imgbtn.setOnClickListener{
            val intent=Intent()
            intent.action=Intent.ACTION_GET_CONTENT
            intent.type="image/*"
            startActivityForResult(intent,1)

        }
        binding.btncontinue.setOnClickListener{
                 if(binding.edtuserName.text!!.isEmpty()){
                      Toast.makeText(this,"Please enter your name",Toast.LENGTH_SHORT).show()
                 }
            else if(imageUri==null){Toast.makeText(this,"Please select your profile",Toast.LENGTH_SHORT).show()}
            else uploadData()
        }

        databaseReference.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                  val user=snapshot.getValue(user::class.java)
                binding.edtuserName.setText(user!!.userName)

             //   if(user.image==""){binding.headerImage.setImageResource(R.drawable.ic)}

                 Glide.with(this@ProfileActivity).load(user.image).into(binding.headerImage)
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext,error.message,Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun uploadData() {
        val storageRef= FirebaseStorage.getInstance().getReference("profile").child(FirebaseAuth.getInstance().currentUser!!.uid).child("profile.jpg")
        storageRef.putFile(imageUri!!)
            .addOnSuccessListener {
                storageRef.downloadUrl.addOnSuccessListener {
                    val hashmap:HashMap<String,String> =HashMap()
                    hashmap.put("userName",binding.edtuserName.text.toString())
                    hashmap.put("image",imageUri.toString())
                    databaseReference.updateChildren(hashmap as Map<String,Any?>)
                    storeData(it)
                }.addOnFailureListener{
                    Toast.makeText(this,it.message,Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener {
                Toast.makeText(this,it.message,Toast.LENGTH_SHORT).show()
            }
    }

    private fun storeData(imageUrl: Uri?) {
        val obj= user(imageUrl.toString(),binding.edtuserName.text.toString())
        FirebaseDatabase.getInstance().getReference().child("userProfile").child(FirebaseAuth.getInstance().currentUser!!.uid).setValue(obj).addOnCompleteListener() {
            if(it.isSuccessful){
                Toast.makeText(this,"Registrered successfully",Toast.LENGTH_SHORT).show()

            }
            else{
                Toast.makeText(this,it.exception!!.message,Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(data!=null){
            if(data.data!=null){
                imageUri=data.data!!
                binding.headerImage.setImageURI(imageUri)
            }
        }
    }

}
