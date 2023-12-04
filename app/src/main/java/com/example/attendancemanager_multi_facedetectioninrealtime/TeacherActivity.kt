package com.example.attendancemanager_multi_facedetectioninrealtime

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.example.attendancemanager_multi_facedetectioninrealtime.fragment.TeacherFragment
import com.example.attendancemanager_multi_facedetectioninrealtime.storage.SaveUser
import com.google.android.material.navigation.NavigationView

class TeacherActivity : AppCompatActivity() {
    private lateinit var teacherNavView: NavigationView
    private lateinit var teacherNavDrawer: DrawerLayout
    private lateinit var teacherToolbar: Toolbar
    private var intententId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teacher)

        val intent1: Intent = intent
        intententId = intent1.getStringExtra("TEACHERID")

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.teacher_fragment_container, TeacherFragment())
                .commit()
        }

        teacherToolbar = findViewById(R.id.teacher_toolbar_id)
        setSupportActionBar(teacherToolbar)

        teacherNavDrawer = findViewById(R.id.teacher_nav_drawer_id)
        teacherNavView = findViewById(R.id.teacher_nav_view_id)

        val actionBarDrawerToggle =
            ActionBarDrawerToggle(this, teacherNavDrawer, teacherToolbar, R.string.nav_drawer_open, R.string.nav_drawer_close)

        teacherNavDrawer.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

        teacherNavView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_item_logout -> {
                    val intent = Intent(this@TeacherActivity, MainActivity::class.java)
                    val saveUser = SaveUser()
                    saveUser.teacher_saveData(this@TeacherActivity, false)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    startActivity(intent)
                    finish()
                }
            }
            true
        }
    }

    fun getData(): String? {
        return intententId
    }
}