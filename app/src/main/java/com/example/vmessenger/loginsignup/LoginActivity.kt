package com.example.vmessenger.loginsignup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.vmessenger.MainActivity
import com.example.vmessenger.R
import com.example.vmessenger.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    private lateinit var mauth: FirebaseAuth
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()
        binding=ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mauth=FirebaseAuth.getInstance()
        binding.btnlogin.setOnClickListener(){
            val em=binding.email.text.toString()
            val pass=binding.password.text.toString()
            cons(em,pass)
        }
    }
    private fun cons(em:String,pass:String){
        //logic for login
        mauth.signInWithEmailAndPassword(em, pass)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Toast.makeText(this@LoginActivity, "login successed", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, MainActivity::class.java))

                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this@LoginActivity, "user doesn't exist", Toast.LENGTH_SHORT).show()
                }
            }

    }
}