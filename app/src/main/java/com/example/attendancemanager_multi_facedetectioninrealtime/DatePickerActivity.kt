package com.example.attendancemanager_multi_facedetectioninrealtime

import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Base64
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.FileProvider
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.io.InputStream


class DatePickerActivity : AppCompatActivity() {
    private lateinit var intentCourse: String
    private lateinit var nextBtn: Button
    private lateinit var dateET: EditText
    private lateinit var dateIbtn: ImageButton
    private lateinit var date: String
    private lateinit var datePickerDialog: DatePickerDialog

    var TAKE_PHOTO_CODE = 0
    var count = 0

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

        val dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString() + "/picFolder/"
        val newdir = File(dir)
        newdir.mkdirs()

        nextBtn.setOnClickListener {
            date = dateET.text.toString()
            if (!date.isEmpty()) {
                count++
                val file = "$dir$count.jpg"
                val newfile = File(file)
                try {
                    newfile.createNewFile()
                } catch (e: IOException) {
                    // Handle the exception
                }

                val outputFileUri: Uri = FileProvider.getUriForFile(
                    this@DatePickerActivity,
                    BuildConfig.APPLICATION_ID + ".provider",
                    newfile
                );

                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri)

                startActivityForResult(cameraIntent, TAKE_PHOTO_CODE)
                //                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE) // capture whole class pic
                intent.putExtra("SC", intentCourse)
                intent.putExtra("DATE", date)
            }
            else{
                Toast.makeText(applicationContext, "Pick a date first",Toast.LENGTH_SHORT).show()
            }

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == TAKE_PHOTO_CODE && resultCode == RESULT_OK) {
            val imageUri: Uri? = data?.data
            val imageStream: InputStream? = contentResolver.openInputStream(imageUri!!)
            val selectedImage: Bitmap? = BitmapFactory.decodeStream(imageStream)
            val encodedImage: String? = encodeImage(selectedImage!!)
        }
    }

    private fun encodeImage(bm: Bitmap): String? {
        val baos = ByteArrayOutputStream()
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val b: ByteArray = baos.toByteArray()
        return Base64.encodeToString(b, Base64.DEFAULT)
    }





    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}