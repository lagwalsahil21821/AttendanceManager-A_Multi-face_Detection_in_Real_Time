package com.example.attendancemanager_multi_facedetectioninrealtime

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import com.example.attendancemanager_multi_facedetectioninrealtime.databinding.ActivityAdminRegisterBinding
import com.google.firebase.auth.FirebaseAuth

class AdminRegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAdminRegisterBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAdminRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

//        val adminSignUpEmailET = findViewById<EditText>(R.id.adminSignUpEmailET)
//        val adminSingUpPassET = findViewById<EditText>(R.id.adminSignUpPassET)
//        val adminSignUpBtn = findViewById<Button>(R.id.adminSignUpBtn)
//        val adminLoginBtn = findViewById<LinearLayout>(R.id.goLogin)

//        adminSignUpBtn.setOnClickListener {
//
//        }
        binding.goLogin.setOnClickListener {
            val intent = Intent(this, AdminLoginActivity::class.java)
            startActivity(intent)
        }

        binding.adminSignUpBtn.setOnClickListener {
            val email = binding.adminSignUpEmailET.text.toString()
            val pass = binding.adminSignUpPassET.text.toString()
            val confirmPass = binding.adminSignUpRePassET.text.toString()

            if (email.isNotEmpty() && pass.isNotEmpty() && confirmPass.isNotEmpty()) {
                if (pass == confirmPass) {

                    firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener {
                        if (it.isSuccessful) {
                            val intent = Intent(this, AdminLoginActivity::class.java)
                            startActivity(intent)
                        }
                        else
                        {
                            Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()

                        }
                    }
                } else {
                    Toast.makeText(this, "Password is not matching", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Empty Fields Are not Allowed !!", Toast.LENGTH_SHORT).show()

            }
        }

    }
}