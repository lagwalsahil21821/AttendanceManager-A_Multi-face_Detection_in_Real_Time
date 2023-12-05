package com.example.attendancemanager_multi_facedetectioninrealtime

import android.app.DatePickerDialog
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.camera2.CameraCaptureSession
import android.hardware.camera2.CameraDevice
import android.hardware.camera2.CameraManager
import android.hardware.camera2.CaptureRequest
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.provider.MediaStore
import android.view.MenuItem
import android.view.TextureView
import android.widget.*
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar



class DatePickerActivity : AppCompatActivity() {
    private lateinit var intentCourse: String
    private lateinit var nextBtn: Button
    private lateinit var dateET: EditText
    private lateinit var dateIbtn: ImageButton
    private lateinit var date: String
    private lateinit var datePickerDialog: DatePickerDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_date_picker)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        dateET = findViewById(R.id.dateET)
        dateIbtn = findViewById(R.id.dateIbtn)
        nextBtn = findViewById(R.id.dateNxtBtn)
        intentCourse = intent.getStringExtra("SC").toString()

        dateIbtn.setOnClickListener {
            val datePicker = DatePicker(this@DatePickerActivity)
            val currentDay = datePicker.dayOfMonth
            val currentMonth = datePicker.month
            val currentYear = datePicker.year

            datePickerDialog = DatePickerDialog(
                this@DatePickerActivity,
                DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                    val stringBuilder = StringBuilder()
                    stringBuilder.append("$dayOfMonth-")
                    stringBuilder.append("${month + 1}-")
                    stringBuilder.append(year)
                    dateET.setText(stringBuilder.toString())
                },
                currentYear,
                currentMonth,
                currentDay
            )
            datePickerDialog.show()
        }

        nextBtn.setOnClickListener {
            date = dateET.text.toString()
            if (date.isNotEmpty()) {
                if (checkSelfPermission(android.Manifest.permission.CAMERA) ==
                    PackageManager.PERMISSION_DENIED ||
                    checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_DENIED
                ) {
                    val permission = arrayOf<String>(
                        android.Manifest.permission.CAMERA,
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                    )
                    requestPermissions(permission, 112)
                } else {
                    openCamera()
                }
                // val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE) // capture whole class pic
//                intent.putExtra("SC", intentCourse)
//                intent.putExtra("DATE", date)
//                startActivity(intent)
            } else {
                Toast.makeText(applicationContext, "Pick a date first!", Toast.LENGTH_SHORT).show()
            }
        }
    }
    var image_uri: Uri? = null
    //TODO opens camera so that user can capture image
    private fun openCamera() {
        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, "New Picture")
        values.put(MediaStore.Images.Media.DESCRIPTION, "From the Camera")
        image_uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri)
        cameraActivityResultLauncher.launch(cameraIntent)
    }

    //TODO capture the image using camera and display it
    private var cameraActivityResultLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult(), ActivityResultCallback {
            if (it.resultCode === RESULT_OK) {
                //imageView.setImageURI(image_uri);
            }
        }
    )



    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}