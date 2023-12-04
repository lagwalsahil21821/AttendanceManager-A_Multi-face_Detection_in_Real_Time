package com.example.attendancemanager_multi_facedetectioninrealtime

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.attendancemanager_multi_facedetectioninrealtime.databinding.ActivityAdminRegisterBinding
import com.example.attendancemanager_multi_facedetectioninrealtime.databinding.ActivityTeacherRegisterBinding
import com.google.firebase.auth.FirebaseAuth

class TeacherRegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTeacherRegisterBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTeacherRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.goTeacherLogin.setOnClickListener {
            val intent = Intent(this, AdminLoginActivity::class.java)
            startActivity(intent)
        }

        binding.teacherSignUpBtn.setOnClickListener {
            val email = binding.teacherSignUpEmailET.text.toString()
            val pass = binding.teacherSignUpPassET.text.toString()
            val confirmPass = binding.teacherSignUpRePassET.text.toString()

            if (email.isNotEmpty() && pass.isNotEmpty() && confirmPass.isNotEmpty()) {
                if (pass == confirmPass) {

                    firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener {
                        if (it.isSuccessful) {
                            val intent = Intent(this, TeacherLoginActivity::class.java)
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