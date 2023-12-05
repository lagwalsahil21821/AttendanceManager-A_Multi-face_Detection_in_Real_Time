package com.example.attendancemanager_multi_facedetectioninrealtime

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.firebase.database.*

class SelectStudentActivity : AppCompatActivity() {
    private lateinit var deptSP: Spinner
    private lateinit var batchSP: Spinner
    private lateinit var shiftSp: Spinner
    private lateinit var nextBtn: Button
    private val deptList = mutableListOf<String>()
    private val batchList = mutableListOf<String>()
    private var selectedDept = ""
    private var selectedBatch = ""
    private var selectedShift = ""
    private lateinit var deptAdapter: ArrayAdapter<String>
    private lateinit var batchAdapter: ArrayAdapter<String>
    private lateinit var shiftAdapter: ArrayAdapter<String>
    private lateinit var deptRef: DatabaseReference
    private lateinit var batchRef: DatabaseReference
    private var shift = arrayOf("Shift 1", "Shift 2")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_student)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        deptSP = findViewById(R.id.deptSp)
        batchSP = findViewById(R.id.batchSp)
        nextBtn = findViewById(R.id.selectNextBtn)
        shiftSp = findViewById(R.id.shiftSp)

        shift = resources.getStringArray(R.array.shift)

        deptRef = FirebaseDatabase.getInstance().reference.child("Department")
        deptRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                deptList.clear()
                deptList.add("Select department")
                if (dataSnapshot.exists()) {
                    for (dataSnapshot1 in dataSnapshot.children) {
                        if (dataSnapshot1.hasChildren()) {
                            val key = dataSnapshot1.key
                            deptList.add(key!!)
                        }
                    }

                    deptAdapter = ArrayAdapter(this@SelectStudentActivity, android.R.layout.simple_list_item_1, deptList)
                    deptSP.adapter = deptAdapter
                    deptAdapter.notifyDataSetChanged()
                    deptSP.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                            selectedDept = parent?.getItemAtPosition(position).toString()

                            batchRef = deptRef.child(selectedDept).child("Student")
                            batchRef.addValueEventListener(object : ValueEventListener {
                                override fun onDataChange(dataSnapshot: DataSnapshot) {
                                    batchList.clear()
                                    batchList.add("Select batch")
                                    if (dataSnapshot.exists()) {
                                        for (dataSnapshot2 in dataSnapshot.children) {
                                            if (dataSnapshot2.hasChildren()) {
                                                val batch = dataSnapshot2.key
                                                batchList.add(batch!!)
                                            }
                                        }

                                        batchAdapter = ArrayAdapter(this@SelectStudentActivity, android.R.layout.simple_list_item_1, batchList)
                                        batchSP.adapter = batchAdapter
                                        batchAdapter.notifyDataSetChanged()
                                        batchSP.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                                            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                                                selectedBatch = parent?.getItemAtPosition(position).toString()

                                                shiftAdapter = ArrayAdapter(this@SelectStudentActivity, android.R.layout.simple_list_item_1, shift)
                                                shiftSp.adapter = shiftAdapter
                                                shiftAdapter.notifyDataSetChanged()
                                                shiftSp.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                                                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                                                        selectedShift = parent?.getItemAtPosition(position).toString()
                                                    }

                                                    override fun onNothingSelected(parent: AdapterView<*>?) {

                                                    }
                                                }
                                            }

                                            override fun onNothingSelected(parent: AdapterView<*>?) {

                                            }
                                        }
                                    }
                                }

                                override fun onCancelled(databaseError: DatabaseError) {

                                }
                            })
                        }

                        override fun onNothingSelected(parent: AdapterView<*>?) {

                        }
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })

        nextBtn.setOnClickListener {
            if (selectedDept.isNotEmpty() && selectedDept != "Select department" &&
                selectedBatch.isNotEmpty() && selectedBatch != "Select batch" &&
                selectedShift.isNotEmpty() && selectedShift != "Select shift"
            ) {
                val intent = Intent(this@SelectStudentActivity, StudentListActivity::class.java)
                intent.putExtra("DEPT", selectedDept)
                intent.putExtra("BATCH", selectedBatch)
                intent.putExtra("SHIFT", selectedShift)
                startActivity(intent)
            }
        }
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