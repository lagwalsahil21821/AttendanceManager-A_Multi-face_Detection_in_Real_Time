package com.example.attendancemanager_multi_facedetectioninrealtime

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.MenuItem
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.FileProvider
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.InputStream


class DatePickerActivity : AppCompatActivity() {
    private lateinit var intentCourse: String
    private lateinit var nextBtn: Button
    private lateinit var uploadBtn: Button
    private lateinit var dateET: EditText
    private lateinit var dateIbtn: ImageButton
    private lateinit var date: String
    private lateinit var imgView: ImageView
    lateinit var imageUri: Uri
    private lateinit var datePickerDialog: DatePickerDialog

    private val contract = registerForActivityResult(ActivityResultContracts.TakePicture()) {
        imgView.setImageURI(null)
        imgView.setImageURI(imageUri)
        Toast.makeText(applicationContext, "Image Captured Successfully", Toast.LENGTH_SHORT).show()


    }


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
        uploadBtn = findViewById(R.id.btnUpload)
        imgView = findViewById(R.id.imageBatch)
        intentCourse = intent.getStringExtra("SC").toString()

        dateIbtn.setOnClickListener {
            val datePicker = DatePicker(this@DatePickerActivity)
            val currentDay = datePicker.dayOfMonth
            val currentMonth = datePicker.month
            val currentYear = datePicker.year

            datePickerDialog = DatePickerDialog(
                this@DatePickerActivity,
                { _, year, month, dayOfMonth ->
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


        imageUri = createImageUri()!!

        nextBtn.setOnClickListener {
            date = dateET.text.toString()
            if (date.isNotEmpty()) {
                contract.launch(imageUri)
                intent.putExtra("SC", intentCourse)
                intent.putExtra("DATE", date)
            } else {
                Toast.makeText(applicationContext, "Pick a date first", Toast.LENGTH_SHORT).show()
            }
        }

        uploadBtn.setOnClickListener {
            val imageStream: InputStream? = contentResolver.openInputStream(imageUri)
            val selectedImage: Bitmap? = BitmapFactory.decodeStream(imageStream)
            val encodedImage: String? = encodeImage(selectedImage!!)
            requestOp(encodedImage)
        }
    }

    //to get uri of image
    private fun createImageUri(): Uri? {
        val image = File(applicationContext.filesDir, "camera_photo.png")
        return FileProvider.getUriForFile(applicationContext,
        "com.example.attendancemanager_multi_facedetectioninrealtime.fileProvider",
        image
        )
    }


//    private val cameraActivityResult =
//        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
//            if (result.resultCode == Activity.RESULT_OK) {
//                val data: Intent? = result.data
//                val imageUri: Uri? = data?.data
//                val imageStream: InputStream? = contentResolver.openInputStream(imageUri!!)
//                val selectedImage: Bitmap? = BitmapFactory.decodeStream(imageStream)
//                val encodedImage: String? = encodeImage(selectedImage!!)
//                requestOp(encodedImage)
//            }
//        }

    private fun encodeImage(bm: Bitmap): String? {
        val baos = ByteArrayOutputStream()
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val b: ByteArray = baos.toByteArray()
        return Base64.encodeToString(b, Base64.DEFAULT)
      }

    private fun requestOp(img : String?)  {

        val url = "https://05e4-2401-4900-c80-c3da-3f93-8421-680e-d132.ngrok.io/api/result";

        // Creating a StringRequest
        val dataToSend = img

        // Create a StringRequest
        val stringRequest = object : StringRequest(
            Request.Method.POST, url,
            Response.Listener<String> { response ->
                // Handle the response
                Log.d("Volley", "Response: $response")
            },
            Response.ErrorListener { error: VolleyError ->
                // Handle the error
                Log.e("Volley", "Error: ${error.message}")
            }) {
            override fun getBody(): ByteArray {
                // Convert the string to bytes and send as the request body
                return dataToSend!!.toByteArray(Charsets.UTF_8)
            }

            override fun getHeaders(): MutableMap<String, String> {
                // Set any additional headers if needed
                val headers = HashMap<String, String>()
                headers["Content-Type"] = "application/json"
                // Add any other headers as needed
                return headers
            }
        }

        // Add the request to the request queue
        Volley.newRequestQueue(this).add(stringRequest)

    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}