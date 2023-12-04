package com.example.attendancemanager_multi_facedetectioninrealtime

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.attendancemanager_multi_facedetectioninrealtime.databinding.ActivityAdminLoginBinding
import com.example.attendancemanager_multi_facedetectioninrealtime.databinding.ActivityTeacherLoginBinding
import com.google.firebase.auth.FirebaseAuth

class TeacherLoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTeacherLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTeacherLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        binding.goSignUP.setOnClickListener {
            val intent = Intent(this, TeacherRegisterActivity::class.java)
            startActivity(intent)
        }

        binding.teacherLoginBtn.setOnClickListener {
            val email = binding.teacherLoginId.text.toString()
            val pass = binding.teacherLoginPassword.text.toString()

            if (email.isNotEmpty() && pass.isNotEmpty()) {

                firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
                    if (it.isSuccessful) {
                        val intent = Intent(this, TeacherActivity::class.java)
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
}