package com.example.attendancemanager_multi_facedetectioninrealtime

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext

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
//           val intent = Intent(this, AdminActivity::class.java)
//            startActivity(intent)
        }

        teacherCard.setOnClickListener {
//            intent = Intent(this@MainActivity, TeacherLoginActivity::class.java)
//            startActivity(intent)
//            finish()
            //temporary
            val intent = Intent(this@MainActivity, SelectedCourseActivity::class.java)
            //intent.putExtra("TID", intentedId)
            startActivity(intent)
        }

        studentCard.setOnClickListener {
            val intent = Intent(this@MainActivity, StudentLoginActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}