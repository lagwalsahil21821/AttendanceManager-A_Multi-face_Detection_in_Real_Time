package com.example.attendancemanager_multi_facedetectioninrealtime.storage

import android.content.Context
import com.example.attendancemanager_multi_facedetectioninrealtime.model.Student
import com.example.attendancemanager_multi_facedetectioninrealtime.model.Teacher

class SaveUser {
    private var value: Boolean? = null

    fun admin_saveData(context: Context, b: Boolean?) {
        val pref = context.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        val editor = pref.edit()
        editor.putBoolean("isUserLogIn", b ?: false)
        editor.apply()
    }

    fun admin_loadData(context: Context): Boolean {
        val pref = context.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        value = pref.getBoolean("isUserLogIn", false)
        return value ?: false
    }

    fun introSave(context: Context, b: Boolean?) {
        val pref = context.getSharedPreferences("intro", Context.MODE_PRIVATE)
        val editor = pref.edit()
        editor.putBoolean("intro", b ?: false)
        editor.apply()
    }

    fun introLoad(context: Context): Boolean {
        val pref = context.getSharedPreferences("intro", Context.MODE_PRIVATE)
        value = pref.getBoolean("intro", false)
        return value ?: false
    }

    fun teacher_saveData(context: Context, b: Boolean?) {
        val pref = context.getSharedPreferences("myPrefss", Context.MODE_PRIVATE)
        val editor = pref.edit()
        editor.putBoolean("isUserLogIn", b ?: false)
        editor.apply()
    }

    fun teacher_loadData(context: Context): Boolean {
        val pref = context.getSharedPreferences("myPrefss", Context.MODE_PRIVATE)
        value = pref.getBoolean("isUserLogIn", false)
        return value ?: false
    }

    // ... (similar conversions for other methods)

    fun saveTeacher(context: Context, teacher: Teacher) {
        val sharedPreferences = context.getSharedPreferences("TEACHER", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("id", teacher.id)
        editor.putString("name", teacher.name)
        editor.putString("desination", teacher.designation)
        editor.putString("dept", teacher.department)
        editor.putString("shift", teacher.shift)
        editor.apply()
    }

    fun getTeacher(context: Context): Teacher? {
        val sharedPreferences = context.getSharedPreferences("TEACHER", Context.MODE_PRIVATE)
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
        val sharedPreferences = context.getSharedPreferences("STUDENT", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("id", student.id)
        editor.putString("name", student.name)
        editor.putString("batch", student.batch)
        editor.putString("dept", student.department)
        editor.putString("shift", student.shift)
        editor.putString("course", student.course)
        editor.putString("course_code", student.course_code)
        editor.putString("password", student.password)
        editor.apply()
    }

    fun getStudent(context: Context): Student? {
        val sharedPreferences = context.getSharedPreferences("STUDENT", Context.MODE_PRIVATE)
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
