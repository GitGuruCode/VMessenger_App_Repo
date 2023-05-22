package com.example.vmessenger.loginsignup
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.vmessenger.MainActivity
import com.example.vmessenger.R
import com.example.vmessenger.databinding.ActivitySignUpBinding
import com.example.vmessenger.users.user
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUpActivity : AppCompatActivity() {
    private lateinit var mauth: FirebaseAuth
    private lateinit var mDbRef: DatabaseReference
    private lateinit var binding :ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        supportActionBar?.hide()
        mauth= FirebaseAuth.getInstance()
        binding=ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnsignup.setOnClickListener{
            var  name:String=binding.name.text.toString()
            var email:String=binding.email.text.toString()
            var password:String=binding.password.text.toString()
            createuser(name,email,password)
        }
        binding.btnlogin.setOnClickListener{
            startActivity(Intent(this,LoginActivity::class.java))
            finish()
        }
    }
    fun createuser(name:String,email:String,password:String){
        mauth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information

                    startActivity(Intent(this, MainActivity::class.java))
                    addUserToDatabase(name,email,mauth.currentUser?.uid!!)
                    Toast.makeText(this,"User created successfully", Toast.LENGTH_SHORT).show()
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this,"Unable to create user", Toast.LENGTH_SHORT).show()
                }
            }
    }
    private fun addUserToDatabase(na:String,em:String,Uid:String){
        mDbRef= FirebaseDatabase.getInstance().getReference()
        mDbRef.child("userV").child(Uid).setValue(user(na,em,Uid))
    }
}