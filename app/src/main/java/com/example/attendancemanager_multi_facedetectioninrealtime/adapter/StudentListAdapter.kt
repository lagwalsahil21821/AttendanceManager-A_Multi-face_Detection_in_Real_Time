package com.example.attendancemanager_multi_facedetectioninrealtime.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.example.attendancemanager_multi_facedetectioninrealtime.R
import com.example.attendancemanager_multi_facedetectioninrealtime.model.Student

class StudentListAdapter(private val context: Context, private var studentList: List<Student>) :
    RecyclerView.Adapter<StudentListAdapter.StudentListViewHolder>() {

    constructor(context: Context) : this(context, mutableListOf())

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): StudentListViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val view: View = layoutInflater.inflate(R.layout.single_student_layout, viewGroup, false)
        return StudentListViewHolder(view)
    }

    override fun onBindViewHolder(studentListViewHolder: StudentListViewHolder, i: Int) {
        val student = studentList[studentListViewHolder.adapterPosition]
        studentListViewHolder.studentName.text = student.name
        studentListViewHolder.courseCode.text = student.course_code
        studentListViewHolder.studentId.text = student.id
    }

    override fun getItemCount(): Int {
        return studentList.size
    }

    inner class StudentListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var studentName: TextView = itemView.findViewById(R.id.studentNameTV)
        var courseCode: TextView = itemView.findViewById(R.id.studentCourseTv)
        var studentId: TextView = itemView.findViewById(R.id.studentIDv)
    }

    fun updateCollection(studentList: List<Student>) {
        this.studentList = studentList
        notifyDataSetChanged()
    }
}