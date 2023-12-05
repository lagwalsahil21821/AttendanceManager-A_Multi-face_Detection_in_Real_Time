package com.example.attendancemanager_multi_facedetectioninrealtime.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.attendancemanager_multi_facedetectioninrealtime.R
import com.example.attendancemanager_multi_facedetectioninrealtime.model.Student
import com.example.attendancemanager_multi_facedetectioninrealtime.storage.SaveUser
import com.google.firebase.database.*

class ViewAttendanceAdapter(private val context: Context, private var studentList: List<Student>) :
    RecyclerView.Adapter<ViewAttendanceAdapter.ViewAttendanceViewHolder>() {

    private val presentList = ArrayList<String>()
    private val absentList = ArrayList<String>()

    private var presentRef: DatabaseReference? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewAttendanceViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val view = layoutInflater.inflate(R.layout.single_student_layout, parent, false)
        return ViewAttendanceViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewAttendanceViewHolder, @SuppressLint("RecyclerView") position: Int) {
        val student = studentList[holder.adapterPosition]

        holder.studentName.text = student.name
        holder.courseCode.text = student.course_code
        holder.studentId.text = student.id

        holder.itemView.setOnClickListener {
            presentRef =
                SaveUser().teacher_CourseLoadData(context)?.let { it1 ->
                    SaveUser().getTeacher(context)?.let { it2 ->
                        FirebaseDatabase.getInstance().reference.child("Department")
                            .child(student.department)
                            .child("Attendance")
                            .child(it2.shift)
                            .child(it1)
                    }
                }

            presentRef?.addListenerForSingleValueEvent(object : ValueEventListener {
                @SuppressLint("MissingInflatedId")
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    presentList.clear()
                    absentList.clear()

                    if (dataSnapshot.exists()) {
                        for (dataSnapshot1 in dataSnapshot.children) {
                            for (dataSnapshot2 in dataSnapshot1.child("Present").children) {
                                val key = dataSnapshot2.key
                                if (holder.adapterPosition != -1) {
                                    if (key == studentList[position].id) {
                                        presentList.add(key!!)
                                    }
                                }
                            }

                            for (dataSnapshot2 in dataSnapshot1.child("Absent").children) {
                                val key = dataSnapshot2.value.toString()
                                if (key == studentList[holder.adapterPosition].id) {
                                    absentList.add(key)
                                }
                            }
                        }

                        val dialog = AlertDialog.Builder(context).create()
                        val view = LayoutInflater.from(context).inflate(R.layout.viewatedance, null)

                        val presentTV: TextView = view.findViewById(R.id.presentStudentTV1)
                        val absentTV: TextView = view.findViewById(R.id.absentStudentTV1)
                        val name: TextView = view.findViewById(R.id.vName)
                        val id: TextView = view.findViewById(R.id.vID)
                        val button: Button = view.findViewById(R.id.Okbtn)

                        name.text = studentList[holder.adapterPosition].name
                        id.text = studentList[holder.adapterPosition].id
                        presentTV.text = presentList.size.toString()
                        absentTV.text = absentList.size.toString()

                        button.setOnClickListener { dialog.dismiss() }

                        dialog.setView(view)
                        dialog.setCancelable(true)
                        dialog.show()
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                }
            })
        }
    }

    override fun getItemCount(): Int {
        return studentList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateCollection(updatedList: List<Student>) {
        studentList = updatedList
        notifyDataSetChanged()
    }

    inner class ViewAttendanceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val studentName: TextView = itemView.findViewById(R.id.studentNameTV)
        val courseCode: TextView = itemView.findViewById(R.id.studentCourseTv)
        val studentId: TextView = itemView.findViewById(R.id.studentIDv)
    }
}
