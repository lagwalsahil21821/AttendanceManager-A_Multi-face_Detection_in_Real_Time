package com.example.attendancemanager_multi_facedetectioninrealtime.storage

import android.content.Context
import com.example.attendancemanager_multi_facedetectioninrealtime.model.Student
import com.example.attendancemanager_multi_facedetectioninrealtime.model.Teacher

class SaveUser {
    private var value: Boolean? = null

    fun admin_saveData(context: Context, b: Boolean) {
        val pref = context.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        val editor = pref.edit()
        editor.putBoolean("isUserLogIn", b)
        editor.apply()
    }

    fun admin_loadData(context: Context): Boolean {
        val pref = context.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        value = pref.getBoolean("isUserLogIn", false)
        return value!!
    }

    fun introSave(context: Context, b: Boolean) {
        val pref = context.getSharedPreferences("intro", Context.MODE_PRIVATE)
        val editor = pref.edit()
        editor.putBoolean("intro", b)
        editor.apply()
    }

    fun introLoad(context: Context): Boolean {
        val pref = context.getSharedPreferences("intro", Context.MODE_PRIVATE)
        value = pref.getBoolean("intro", false)
        return value!!
    }

    fun teacher_saveData(context: Context, b: Boolean) {
        val pref = context.getSharedPreferences("myPrefss", Context.MODE_PRIVATE)
        val editor = pref.edit()
        editor.putBoolean("isUserLogIn", b)
        editor.apply()
    }

    fun teacher_loadData(context: Context): Boolean {
        val pref = context.getSharedPreferences("myPrefss", Context.MODE_PRIVATE)
        value = pref.getBoolean("isUserLogIn", false)
        return value!!
    }

    fun Student_saveData(context: Context, b: Boolean) {
        val pref = context.getSharedPreferences("SV", Context.MODE_PRIVATE)
        val editor = pref.edit()
        editor.putBoolean("isUserLogIn", b)
        editor.apply()
    }

    fun Student_loadData(context: Context): Boolean {
        val pref = context.getSharedPreferences("SV", Context.MODE_PRIVATE)
        value = pref.getBoolean("isUserLogIn", false)
        return value!!
    }

    fun teacher_IDsaveData(context: Context, b: String) {
        val pref = context.getSharedPreferences("myPrefsss", Context.MODE_PRIVATE)
        val editor = pref.edit()
        editor.putString("isUserLogIn", b)
        editor.apply()
    }

    fun teacher_IDloadData(context: Context): String? {
        val pref = context.getSharedPreferences("myPrefsss", Context.MODE_PRIVATE)
        return pref.getString("isUserLogIn", null)
    }

    fun teacher_NameSaveData(context: Context, b: String) {
        val pref = context.getSharedPreferences("name", Context.MODE_PRIVATE)
        val editor = pref.edit()
        editor.putString("isUserLogIn", b)
        editor.apply()
    }

    fun teacher_NameLoadData(context: Context): String? {
        val pref = context.getSharedPreferences("name", Context.MODE_PRIVATE)
        return pref.getString("isUserLogIn", null)
    }

    fun teacher_ShiftSaveData(context: Context, b: String) {
        val pref = context.getSharedPreferences("shift", Context.MODE_PRIVATE)
        val editor = pref.edit()
        editor.putString("isUserLogIn", b)
        editor.apply()
    }

    fun teacher_ShiftLoadData(context: Context): String? {
        val pref = context.getSharedPreferences("shift", Context.MODE_PRIVATE)
        return pref.getString("isUserLogIn", null)
    }

    fun teacher_DeptSaveData(context: Context, b: String) {
        val pref = context.getSharedPreferences("tdept", Context.MODE_PRIVATE)
        val editor = pref.edit()
        editor.putString("isUserLogIn", b)
        editor.apply()
    }

    fun teacher_DeptLoadData(context: Context): String? {
        val pref = context.getSharedPreferences("tdept", Context.MODE_PRIVATE)
        return pref.getString("isUserLogIn", null)
    }

    fun teacher_CourseSaveData(context: Context, b: String) {
        val pref = context.getSharedPreferences("tcourse", Context.MODE_PRIVATE)
        val editor = pref.edit()
        editor.putString("isUserLogIn", b)
        editor.apply()
    }

    fun teacher_CourseLoadData(context: Context): String? {
        val pref = context.getSharedPreferences("tcourse", Context.MODE_PRIVATE)
        return pref.getString("isUserLogIn", null)
    }

    fun saveTeacher(context: Context, teacher: Teacher) {
        val sharedPreferences =
            context.getSharedPreferences("TEACHER", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("id", teacher.getId())
        editor.putString("name", teacher.getName())
        editor.putString("desination", teacher.getDesignation())
        editor.putString("dept", teacher.getDepartment())
        editor.putString("shift", teacher.getShift())
        editor.apply()
    }

    fun getTeacher(context: Context): Teacher? {
        val sharedPreferences =
            context.getSharedPreferences("TEACHER", Context.MODE_PRIVATE)
        return Teacher(
            sharedPreferences.getString("id", null),
            sharedPreferences.getString("name", null),
            sharedPreferences.getString("dept", null),
            sharedPreferences.getString("desination", null),
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            sharedPreferences.getString("shift", null)
        )
    }

    fun saveStudent(context: Context, student: Student) {
        val sharedPreferences =
            context.getSharedPreferences("STUDENT", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("id", student.getId())
        editor.putString("name", student.getName())
        editor.putString("batch", student.getBatch())
        editor.putString("dept", student.getDepartment())
        editor.putString("shift", student.getShift())
        editor.putString("course", student.getCourse())
        editor.putString("course_code", student.getCourse_code())
        editor.putString("password", student.getPassword())
        editor.apply()
    }

    fun getStudent(context: Context): Student? {
        val sharedPreferences =
            context.getSharedPreferences("STUDENT", Context.MODE_PRIVATE)
        return Student(
            sharedPreferences.getString("name", null),
            sharedPreferences.getString("id", null),
            "",
            "",
            sharedPreferences.getString("dept", null),
            sharedPreferences.getString("batch", null),
            "",
            "",
            "",
            "",
            sharedPreferences.getString("course", null),
            sharedPreferences.getString("course_code", null),
            sharedPreferences.getString("shift", null),
            sharedPreferences.getString("password", null)
        )
    }
}
