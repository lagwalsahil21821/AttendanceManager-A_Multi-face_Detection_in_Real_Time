package com.example.attendancemanager_multi_facedetectioninrealtime.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.example.attendancemanager_multi_facedetectioninrealtime.R
import com.example.attendancemanager_multi_facedetectioninrealtime.SelectedCourse1Activity
import com.example.attendancemanager_multi_facedetectioninrealtime.SelectedCourseActivity
import com.example.attendancemanager_multi_facedetectioninrealtime.model.Teacher
import com.example.attendancemanager_multi_facedetectioninrealtime.storage.SaveUser
import com.google.firebase.database.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [TeacherFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

class TeacherFragment : Fragment() {
    private val teacherList: MutableList<Teacher> = ArrayList()
    private lateinit var teacherRef: DatabaseReference
    private var dept: String? = null
    private var intentedId: String? = null
    private var shift: String? = null
    private lateinit var name: TextView
    private lateinit var id: TextView
    private lateinit var desig: TextView
    private lateinit var tShift: TextView
    private lateinit var depat: TextView
    private lateinit var teacherAttendanceCV: CardView
    private lateinit var ViewAttendanceCV: CardView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_teacher, container, false)
        val saveUser = SaveUser()
        intentedId = saveUser.teacher_IDloadData(requireContext())

        name = view.findViewById(R.id.teacherInfoName)
        id = view.findViewById(R.id.teacherInfoId)
        desig = view.findViewById(R.id.teacherInfoDesig)
        tShift = view.findViewById(R.id.teacherShiftDept)
        depat = view.findViewById(R.id.teacherInfoDept)
        ViewAttendanceCV = view.findViewById(R.id.ViewAttendanceCV)
        teacherAttendanceCV = view.findViewById(R.id.teacherAttendanceCV)

        name.text = saveUser.getTeacher(requireContext())?.name
        id.text = saveUser.getTeacher(requireContext())?.id
        desig.text = saveUser.getTeacher(requireContext())?.designation
        tShift.text = saveUser.getTeacher(requireContext())?.shift
        depat.text = saveUser.getTeacher(requireContext())?.department

        teacherAttendanceCV.setOnClickListener {
            val intent = Intent(requireContext(), SelectedCourseActivity::class.java)
            intent.putExtra("TID", intentedId)
            startActivity(intent)
        }

        ViewAttendanceCV.setOnClickListener {
            val intent = Intent(requireContext(), SelectedCourse1Activity::class.java)
            startActivity(intent)
        }

        fetchTeacher()
        return view
    }

    private fun fetchTeacher() {
        teacherRef = FirebaseDatabase.getInstance().reference.child("Department")
        teacherRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                teacherList.clear()
                if (dataSnapshot.exists()) {
                    for (dataSnapshot1 in dataSnapshot.children) {
                        dept = dataSnapshot1.key
                        val deptref: DatabaseReference = teacherRef.child(dept!!).child("Teacher")
                        deptref.addValueEventListener(object : ValueEventListener {
                            override fun onDataChange(dataSnapshot: DataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    for (dataSnapshot2 in dataSnapshot.children) {
                                        for (dataSnapshot3 in dataSnapshot2.children) {
                                            if (dataSnapshot3.hasChildren()) {
                                                val teacher: Teacher? =
                                                    dataSnapshot3.getValue(Teacher::class.java)
                                                if (teacher?.id == intentedId) {
                                                    val name1: String = teacher?.name!!
                                                    val id1: String = teacher.id!!
                                                    val desig1: String = teacher.designation!!
                                                    val dept1: String = teacher.department!!
                                                    shift = teacher.shift
                                                    name.text = name1
                                                    id.text = id1
                                                    desig.text = desig1
                                                    depat.text = dept1
                                                    tShift.text = shift

                                                }
                                            }
                                        }
                                        teacherAttendanceCV.setOnClickListener {
                                            if (!shift.isNullOrEmpty()) {
                                                val intent = Intent(
                                                    requireContext(),
                                                    SelectedCourseActivity::class.java
                                                )
                                                intent.putExtra("TID", intentedId)
                                                intent.putExtra("TSHIFT", shift)
                                                startActivity(intent)
                                            }
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
}
