package com.example.attendancemanager_multi_facedetectioninrealtime.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.example.attendancemanager_multi_facedetectioninrealtime.CourseDeptActivity
import com.example.attendancemanager_multi_facedetectioninrealtime.DeptActivity
import com.example.attendancemanager_multi_facedetectioninrealtime.R
import com.example.attendancemanager_multi_facedetectioninrealtime.SelectStudentActivity

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AdminHomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AdminHomeFragment : Fragment() {

    private lateinit var studentListCV: CardView
    private lateinit var teacherListCV: CardView
    private lateinit var courseListCV: CardView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_admin_home, container, false)

        studentListCV = view.findViewById(R.id.studentListCV)
        teacherListCV = view.findViewById(R.id.teacherListCV)
        courseListCV = view.findViewById(R.id.courseListCV)

        studentListCV.setOnClickListener {
            val intent = Intent(requireContext(), SelectStudentActivity::class.java)
            startActivity(intent)
        }

        teacherListCV.setOnClickListener {
            val intent = Intent(requireContext(), DeptActivity::class.java)
            startActivity(intent)
        }

        courseListCV.setOnClickListener {
            val intent = Intent(requireContext(), CourseDeptActivity::class.java)
            startActivity(intent)
        }

        return view
    }
}