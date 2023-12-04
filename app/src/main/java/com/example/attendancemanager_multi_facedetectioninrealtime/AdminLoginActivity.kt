package com.example.attendancemanager_multi_facedetectioninrealtime

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import com.example.attendancemanager_multi_facedetectioninrealtime.databinding.ActivityAdminLoginBinding
import com.google.firebase.auth.FirebaseAuth

class AdminLoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAdminLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        val adminSignINEmailET = findViewById<EditText>(R.id.adminSignInEmailET)
//        val adminSingInPassET = findViewById<EditText>(R.id.adminSignInPassET)
//        val adminLogInBtn = findViewById<Button>(R.id.adminLoginBtn)
//        val adminRegisterBtn = findViewById<LinearLayout>(R.id.goSignUP)
//
//        adminLogInBtn.setOnClickListener {
//
//
//        }
        firebaseAuth = FirebaseAuth.getInstance()
        binding.goSignUP.setOnClickListener {
            val intent = Intent(this, AdminRegisterActivity::class.java)
            startActivity(intent)
        }

        binding.adminLoginBtn.setOnClickListener {
            val email = binding.adminSignInEmailET.text.toString()
            val pass = binding.adminSignInPassET.text.toString()

            if (email.isNotEmpty() && pass.isNotEmpty()) {

                firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
                    if (it.isSuccessful) {
                        val intent = Intent(this, AdminActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()

                    }
                }
            } else {
                Toast.makeText(this, "Empty Fields Are not Allowed !!", Toast.LENGTH_SHORT).show()

            }
        }
    }

    // if you don't want to login everytime

//    override fun onStart() {
//        super.onStart()
//
//        if(firebaseAuth.currentUser != null){
//            val intent = Intent(this, AdminActivity::class.java)
//            startActivity(intent)
//        }
//    }
}
