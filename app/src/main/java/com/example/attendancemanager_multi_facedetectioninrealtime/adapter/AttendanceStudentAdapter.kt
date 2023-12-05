package com.example.attendancemanager_multi_facedetectioninrealtime.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.ContextThemeWrapper
import androidx.recyclerview.widget.RecyclerView
import com.example.attendancemanager_multi_facedetectioninrealtime.R
import com.example.attendancemanager_multi_facedetectioninrealtime.model.Teacher

class AttendanceStudentAdapter(private val context: Context, private var teacherlist: List<Teacher>) :
    RecyclerView.Adapter<AttendanceStudentAdapter.TeacherListViewHolder>() {

    //constructor() : this(ContextThemeWrapper(App.instance, R.style.AppTheme), mutableListOf())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeacherListViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val view = layoutInflater.inflate(R.layout.single_student_layout, parent, false)
        return TeacherListViewHolder(view)
    }

    override fun onBindViewHolder(holder: TeacherListViewHolder, position: Int) {
        holder.studentName.text = teacherlist[position].name
        holder.courseCode.text = teacherlist[position].designation
    }

    override fun getItemCount(): Int {
        return teacherlist.size
    }

    fun updateCollection(updatedList: List<Teacher>) {
        teacherlist = updatedList
        notifyDataSetChanged()
    }

    inner class TeacherListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val studentName: TextView = itemView.findViewById(R.id.studentNameTV)
        val courseCode: TextView = itemView.findViewById(R.id.studentCourseTv)
    }
}
