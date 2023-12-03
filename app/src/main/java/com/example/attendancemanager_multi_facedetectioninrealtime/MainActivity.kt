package com.example.attendancemanager_multi_facedetectioninrealtime

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adminCard = findViewById<LinearLayout>(R.id.adminCard)
        val teacherCard = findViewById<LinearLayout>(R.id.teacherCard)
        val studentCard = findViewById<LinearLayout>(R.id.studentCard)

        adminCard.setOnClickListener {
            intent = Intent(this@MainActivity, AdminLoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        teacherCard.setOnClickListener {
            intent = Intent(this@MainActivity, TeacherLoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        studentCard.setOnClickListener {
            val intent = Intent(this@MainActivity, StudentLoginActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}