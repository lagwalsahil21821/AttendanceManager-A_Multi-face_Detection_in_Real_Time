package com.example.attendancemanager_multi_facedetectioninrealtime

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.attendancemanager_multi_facedetectioninrealtime.model.Course
import com.example.attendancemanager_multi_facedetectioninrealtime.storage.SaveUser
import com.google.firebase.database.*

class SelectedCourseActivity : AppCompatActivity() {
    private lateinit var selectCourseSp: Spinner
    private lateinit var nextBtn: Button
    private lateinit var intendedId: String
    private lateinit var intendedShift: String
    private val courseList = ArrayList<Course>()
    private val courseNameList = ArrayList<String>()
    private lateinit var courseRef: DatabaseReference
    private lateinit var dept: String
    private lateinit var selectedCourse: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selected_course)
        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        intendedId = SaveUser().teacher_IDloadData(applicationContext).toString()
        selectCourseSp = findViewById(R.id.TtCourseSp)
        nextBtn = findViewById(R.id.tsNextBtn)

        intendedShift = SaveUser().teacher_ShiftLoadData(applicationContext).toString()


        val courses =
            arrayOf("Select Course", "Data Analytics", "Artificial Intelligence", "Wireless Sensor Network", "Operating System", "Computer Networks", "DBMS", "Data Structure", "Analysis & Design of Algorithm")

        val spinnerArrayAdapter: ArrayAdapter<String> = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, courses)
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) // The drop down view

        selectCourseSp.adapter = spinnerArrayAdapter

        selectCourseSp.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long,
                ) {
                    selectedCourse =
                        parent?.getItemAtPosition(position).toString()
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }

        nextBtn.setOnClickListener {
            if (selectedCourse != courses[0]) {
                val intent = Intent(this@SelectedCourseActivity, DatePickerActivity::class.java)
                intent.putExtra("SC", selectedCourse)
                startActivity(intent)
            }
            else{
                Toast.makeText(applicationContext, "Please Select Course!!!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun fetchCourse() {
        courseRef = FirebaseDatabase.getInstance().reference.child("Department")
        courseRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                courseList.clear()
                courseNameList.clear()
                courseNameList.add(0, "Select course")
                if (dataSnapshot.exists()) {
                    for (dataSnapshot1 in dataSnapshot.children) {
                        dept = dataSnapshot1.key!!
                        val deptRef = courseRef.child(dept).child("Course").child(intendedShift)
                            .orderByChild("teacher_id").equalTo(intendedId)
                        deptRef.addListenerForSingleValueEvent(object : ValueEventListener {
                            override fun onDataChange(dataSnapshot: DataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    for (dataSnapshot2 in dataSnapshot.children) {
                                        if (dataSnapshot2.hasChildren()) {
                                            val course = dataSnapshot2.getValue(Course::class.java)
                                            val name = course?.course_name
                                            if (name != null) {
                                                courseNameList.add(name)
                                            }
                                        }
                                        val adapter = ArrayAdapter(
                                            this@SelectedCourseActivity,
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
                                                    id: Long,
                                                ) {
                                                    selectedCourse =
                                                        parent?.getItemAtPosition(position).toString()
                                                }

                                                override fun onNothingSelected(parent: AdapterView<*>?) {}
                                            }
                                    }
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