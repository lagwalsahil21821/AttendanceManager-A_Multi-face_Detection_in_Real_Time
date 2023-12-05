package com.example.attendancemanager_multi_facedetectioninrealtime

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.appcompat.widget.Toolbar
import com.example.attendancemanager_multi_facedetectioninrealtime.model.Course
import com.example.attendancemanager_multi_facedetectioninrealtime.storage.SaveUser
import com.google.firebase.database.*

class SelectedCourse1Activity : AppCompatActivity() {
    private lateinit var selectCourseSp: Spinner
    private lateinit var nextBtn: Button
    private var intendedId: String = ""
    private var intendedShift: String = ""
    private val courseList = mutableListOf<Course>()
    private val courseNameList = mutableListOf<String>()
    private lateinit var CourseRef: DatabaseReference
    private var dept: String = ""
    private var selectedCourse: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selected_course)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        intendedId = SaveUser().teacher_IDloadData(applicationContext).toString()
        selectCourseSp = findViewById(R.id.TtCourseSp)
        nextBtn = findViewById(R.id.tsNextBtn)

        intendedShift = SaveUser().teacher_ShiftLoadData(applicationContext).toString()

        fetchCourse()

        nextBtn.setOnClickListener {
            if (selectedCourse != "Select course") {
                SaveUser().teacher_CourseSaveData(applicationContext, selectedCourse)
                val intent = Intent(this@SelectedCourse1Activity, ViewAttendanceActivity::class.java)
                intent.putExtra("SC", selectedCourse)
                startActivity(intent)
            }
        }
    }

    private fun fetchCourse() {

        SaveUser().getTeacher(this)?.let {
            FirebaseDatabase.getInstance().reference
                .child("Department")
                .child(it.department)
                .child("Course")
                .child(intendedShift)
        }?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                courseList.clear()
                courseNameList.clear()
                courseNameList.add(0, "Select course")

                if (dataSnapshot.exists()) {
                    for (dataSnapshot2 in dataSnapshot.children) {
                        if (dataSnapshot2.hasChildren()) {
                            val course = dataSnapshot2.getValue(Course::class.java)
                            if (course?.teacher_id == intendedId) {
                                val name = course.course_name
                                if (name != null) {
                                    courseNameList.add(name)
                                }
                            }
                        }
                    }

                    val adapter =
                        ArrayAdapter(
                            this@SelectedCourse1Activity,
                            android.R.layout.simple_list_item_1,
                            courseNameList
                        )
                    selectCourseSp.adapter = adapter
                    selectCourseSp.onItemSelectedListener =
                        object : AdapterView.OnItemSelectedListener {
                            override fun onItemSelected(
                                parent: AdapterView<*>?,
                                view: View?,
                                position: Int,
                                id: Long
                            ) {
                                selectedCourse = parent?.getItemAtPosition(position).toString()
                            }

                            override fun onNothingSelected(parent: AdapterView<*>?) {}
                        }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == android.R.id.home) {
            finish()
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }
}