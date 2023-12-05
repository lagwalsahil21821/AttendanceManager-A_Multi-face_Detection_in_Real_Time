package com.example.attendancemanager_multi_facedetectioninrealtime

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.attendancemanager_multi_facedetectioninrealtime.adapter.ViewAttendanceAdapter
import com.example.attendancemanager_multi_facedetectioninrealtime.model.Student
import com.example.attendancemanager_multi_facedetectioninrealtime.storage.SaveUser
import com.google.firebase.database.*

class ViewAttendanceActivity : AppCompatActivity() {
    private lateinit var viewAttendanceRV: RecyclerView
    private var intentdedCourse: String? = null
    private lateinit var studentRef: DatabaseReference
    private lateinit var deptref: DatabaseReference
    private lateinit var attendanceRef: DatabaseReference
    private var dept: String? = null
    private var studentList: MutableList<Student> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_attendance)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        viewAttendanceRV = findViewById(R.id.ViewAttendanceRV)
        val intent: Intent = intent
        intentdedCourse = intent.getStringExtra("SC")

        studentRef = FirebaseDatabase.getInstance().reference.child("Department")

        studentRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (dataSnapshot1 in dataSnapshot.children) {
                        dept = dataSnapshot1.key
                        deptref = SaveUser().getTeacher(this@ViewAttendanceActivity)?.let {
                            studentRef.child(it.department)
                                .child("Student")
                        }!!
                        deptref.addListenerForSingleValueEvent(object : ValueEventListener {
                            override fun onDataChange(dataSnapshot: DataSnapshot) {
                                studentList.clear()
                                if (dataSnapshot.exists()) {
                                    for (ds1 in dataSnapshot.children) {
                                        for (ds2 in SaveUser().teacher_ShiftLoadData(applicationContext)
                                            ?.let {
                                                ds1.child("allstudent")
                                                    .child(it).children
                                            }!!) {
                                            if (ds2.hasChildren()) {
                                                val student = ds2.getValue(Student::class.java)
                                                if (intentdedCourse?.let {
                                                        student?.course?.contains(
                                                            it
                                                        )
                                                    } == true) {
                                                    if (student != null) {
                                                        studentList.add(student)
                                                    }
                                                }
                                            }
                                        }
                                    }

                                    val viewAttendanceAdapter =
                                        ViewAttendanceAdapter(this@ViewAttendanceActivity, studentList)

                                    viewAttendanceRV.layoutManager =
                                        LinearLayoutManager(this@ViewAttendanceActivity)
                                    viewAttendanceAdapter.notifyDataSetChanged()
                                    viewAttendanceRV.adapter = viewAttendanceAdapter
                                }
                            }

                            override fun onCancelled(databaseError: DatabaseError) {}
                        })
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}