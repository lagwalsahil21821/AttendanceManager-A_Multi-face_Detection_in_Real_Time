package com.example.attendancemanager_multi_facedetectioninrealtime.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.example.attendancemanager_multi_facedetectioninrealtime.R
import com.example.attendancemanager_multi_facedetectioninrealtime.model.Teacher

class TeacherListAdapter(private val context: Context, private var teacherList: List<Teacher>) :
    RecyclerView.Adapter<TeacherListAdapter.TeacherListViewHolder>() {

    constructor(context: Context) : this(context, mutableListOf())

    @NonNull
    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): TeacherListViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val view: View = layoutInflater.inflate(R.layout.single_student_layout, viewGroup, false)
        return TeacherListViewHolder(view)
    }

    override fun onBindViewHolder(teacherListViewHolder: TeacherListViewHolder, i: Int) {
        val teacher = teacherList[teacherListViewHolder.adapterPosition]
        teacherListViewHolder.studentName.text = teacher.name
        teacherListViewHolder.courseCode.text = teacher.designation
        teacherListViewHolder.teacherEmail.text = teacher.email
    }

    override fun getItemCount(): Int {
        return teacherList.size
    }

    inner class TeacherListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var studentName: TextView = itemView.findViewById(R.id.studentNameTV)
        var courseCode: TextView = itemView.findViewById(R.id.studentCourseTv)
        var teacherEmail: TextView = itemView.findViewById(R.id.studentIDv)
    }

    fun updateCollection(teacherList: List<Teacher>) {
        this.teacherList = teacherList
        notifyDataSetChanged()
    }
}