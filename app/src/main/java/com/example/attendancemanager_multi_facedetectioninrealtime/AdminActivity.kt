package com.example.attendancemanager_multi_facedetectioninrealtime

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.example.attendancemanager_multi_facedetectioninrealtime.storage.SaveUser
import com.example.attendancemanager_multi_facedetectioninrealtime.fragment.AdminHomeFragment
import com.google.android.material.navigation.NavigationView

class AdminActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)

        val adminToolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.admin_toolbar_id)
        setSupportActionBar(adminToolbar)

        val adminNavDrawer = findViewById<DrawerLayout>(R.id.admin_nav_drawer_id)
        val adminNavView = findViewById<NavigationView>(R.id.admin_nav_view_id)

        val actionBarDrawerToggle = ActionBarDrawerToggle(
            this, adminNavDrawer, adminToolbar, R.string.open, R.string.close
        )

        adminNavDrawer.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.admin_fragment_container, AdminHomeFragment()).commit()
        }
        adminNavView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_item_logout -> {
                    val intent = Intent(this@AdminActivity, MainActivity::class.java)
                    val saveUser = SaveUser()
                    saveUser.admin_saveData(this@AdminActivity, false)
                    startActivity(intent)
                    finish()
                }
            }
            true
        }
    }
}