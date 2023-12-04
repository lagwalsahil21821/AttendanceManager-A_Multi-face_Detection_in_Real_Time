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

class DeptActivity : AppCompatActivity() {

    private lateinit var tDeptSp: Spinner
    private lateinit var tShiftSp: Spinner
    private lateinit var tNextBtn: Button
    private lateinit var tDeptRef: DatabaseReference
    private var tDeptList: MutableList<String> = ArrayList()
    private var tSelectedDept: String? = null
    private var tSelectedShift: String? = null
    private lateinit var tshiftList: Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dept)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        tshiftList = resources.getStringArray(R.array.shift)

        tDeptSp = findViewById(R.id.TdeptSp)
        tShiftSp = findViewById(R.id.TShiftSp)
        tNextBtn = findViewById(R.id.tNextBtn)
        tDeptRef = FirebaseDatabase.getInstance().reference.child("Department")

        tDeptRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                tDeptList.clear()
                tDeptList.add("Select Department")
                if (dataSnapshot.exists()) {
                    for (dataSnapshot1 in dataSnapshot.children) {
                        if (dataSnapshot1.hasChildren()) {
                            val key: String = dataSnapshot1.key!!
                            tDeptList.add(key)
                        }
                    }
                    val arrayAdapter: ArrayAdapter<String> =
                        ArrayAdapter(this@DeptActivity, android.R.layout.simple_list_item_1, tDeptList)
                    tDeptSp.adapter = arrayAdapter
                    tDeptSp.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(
                            parent: AdapterView<*>?,
                            view: View?,
                            position: Int,
                            id: Long
                        ) {
                            tSelectedDept = parent?.getItemAtPosition(position).toString()

                            if (!tSelectedDept.isNullOrEmpty()) {
                                val arrayAdapter1: ArrayAdapter<String> =
                                    ArrayAdapter(this@DeptActivity, android.R.layout.simple_list_item_1, tshiftList)
                                tShiftSp.adapter = arrayAdapter1
                                tShiftSp.onItemSelectedListener =
                                    object : AdapterView.OnItemSelectedListener {
                                        override fun onItemSelected(
                                            parent: AdapterView<*>?,
                                            view: View?,
                                            position: Int,
                                            id: Long
                                        ) {
                                            tSelectedShift = parent?.getItemAtPosition(position).toString()
                                        }

                                        override fun onNothingSelected(parent: AdapterView<*>?) {}
                                    }
                            }
                        }

                        override fun onNothingSelected(parent: AdapterView<*>?) {}
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {}

        })

        tNextBtn.setOnClickListener {
            if (tSelectedDept != null && tSelectedDept != "Select Department"
                && tSelectedShift != null && tSelectedShift != "Select shift"
            ) {
                val intent = Intent(this@DeptActivity, TeacherListActivity::class.java)
                intent.putExtra("TDEPT", tSelectedDept)
                intent.putExtra("TSHIFT", tSelectedShift)
                startActivity(intent)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}
