package com.example.finalproject

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.finalProject.R
import com.google.android.material.textfield.TextInputEditText

class ViewStats : AppCompatActivity()  {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_stats)
        val username =getIntent().getStringExtra("username").toString()
        var weightBtn:Button = findViewById<Button>(R.id.showWeight)
        var lessonBtn:Button = findViewById<Button>(R.id.lesson)
        var exitBtn:Button = findViewById<Button>(R.id.logout)
        var logNewDataBtn:Button = findViewById<Button>(R.id.button8)
        var datapw:TextView = findViewById<TextView>(R.id.textView3)

        exitBtn.setOnClickListener(View.OnClickListener { switcheActivities() })
        logNewDataBtn.setOnClickListener(View.OnClickListener { switchdataActivities() })
        weightBtn.setOnClickListener(View.OnClickListener { showWeightData(username)})
        lessonBtn.setOnClickListener(View.OnClickListener { showLessonData(username) })


    }
    private fun switcheActivities() {
        val switchActivityIntent: Intent = Intent(
            this,
            MainActivity::class.java
        )
        startActivity(switchActivityIntent)
    }
    private fun switchdataActivities() {
        val switchActivityIntent: Intent = Intent(
            this,
            InsertWeightandExercise::class.java
        )
        startActivity(switchActivityIntent)
    }

    private  fun showWeightData(username:String){
        var datapw:TextView = findViewById<TextView>(R.id.textView3)
        val url = String.format("http://10.0.2.2:5000/stats/weight/?user_name=%s",username)
        val queue = Volley.newRequestQueue(this)
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                datapw.setText(response.getJSONObject("message").toString())

            },
            Response.ErrorListener { error ->
                datapw.setText(url)
                println(error)
            }
        )
        queue.add(jsonObjectRequest)
    }
    private fun showLessonData(username:String){
        var datapw:TextView = findViewById<TextView>(R.id.textView3)
        val url = String.format("http://10.0.2.2:5000/stats/past-lessons/?user_name=%s",username)
        val queue = Volley.newRequestQueue(this)
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                datapw.setText(response.getJSONObject("message").toString())

            },
            Response.ErrorListener { error ->
                datapw.setText(url)
                println(error)
            }
        )
        queue.add(jsonObjectRequest)
    }


}