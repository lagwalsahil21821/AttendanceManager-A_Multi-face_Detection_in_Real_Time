package com.example.attendancemanager_multi_facedetectioninrealtime.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.example.attendancemanager_multi_facedetectioninrealtime.R
import com.example.attendancemanager_multi_facedetectioninrealtime.model.Course

class CourseListAdapter(private val context: Context, private var courseList: List<Course>) :
    RecyclerView.Adapter<CourseListAdapter.CourseListViewHolder>() {

    constructor(context: Context) : this(context, mutableListOf())

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): CourseListViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val view: View = layoutInflater.inflate(R.layout.single_student_layout, viewGroup, false)
        return CourseListViewHolder(view)
    }

    override fun onBindViewHolder(courseListViewHolder: CourseListViewHolder, i: Int) {
        courseListViewHolder.courseName.text = courseList[courseListViewHolder.adapterPosition].course_name
        courseListViewHolder.courseCode.text = courseList[courseListViewHolder.adapterPosition].course_code
        courseListViewHolder.teacherName.text = courseList[courseListViewHolder.adapterPosition].teacher
    }

    override fun getItemCount(): Int {
        return courseList.size
    }

    inner class CourseListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var courseName: TextView = itemView.findViewById(R.id.studentNameTV)
        var courseCode: TextView = itemView.findViewById(R.id.studentCourseTv)
        var teacherName: TextView = itemView.findViewById(R.id.studentIDv)
    }

    fun updateCollection(courseList: List<Course>) {
        this.courseList = courseList
        notifyDataSetChanged()
    }
}