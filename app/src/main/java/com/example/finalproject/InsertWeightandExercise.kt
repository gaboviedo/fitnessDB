package com.example.finalproject

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.finalProject.R
import com.google.android.material.textfield.TextInputEditText

class InsertWeightandExercise : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_insert_weight_and_exercise)

        val username =getIntent().getStringExtra("username").toString()
        var logoutBtn: Button = findViewById<Button>(R.id.logout)
        var viewStatsBtn: Button = findViewById<Button>(R.id.viewStats)
        var startExercise: Button = findViewById<Button>(R.id.exerciseStart)
        var weightInput: EditText = findViewById<EditText>(R.id.inputWeight)
        var levelInput: EditText = findViewById<EditText>(R.id.level)
        var exTypeInput: EditText = findViewById<EditText>(R.id.ex_type)
        var muscleGroupInput: EditText = findViewById<EditText>(R.id.muscle_group)
        var equipmentInput: EditText = findViewById<EditText>(R.id.equipment)


        logoutBtn.setOnClickListener(View.OnClickListener { switchLActivities() })
        viewStatsBtn.setOnClickListener(View.OnClickListener { switchVSActivities(username) })
        startExercise.setOnClickListener(View.OnClickListener { switchVEActivities(username) })

    }
//    todo:test these two funs
    private fun updateWeightData(username:String) {
        var weightInput: EditText = findViewById<EditText>(R.id.inputWeight)
        val url = String.format("http://10.0.2.2:5000/log-setting/?user_name=%s&weight=%s",username,weightInput.getText())
        val queue = Volley.newRequestQueue(this)
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST, url, null,
            Response.Listener { response ->
                weightInput.setText("Response: %s".format(response.toString()))

            },
            Response.ErrorListener { error ->
                weightInput.setText(url)
                println(error)
            }
        )
        queue.add(jsonObjectRequest)
    }
    private fun receivePrefData(username:String):Pair<String, String>  {

        var levelInput: EditText = findViewById<EditText>(R.id.level)
        var exTypeInput: EditText = findViewById<EditText>(R.id.ex_type)
        var muscleGroupInput: EditText = findViewById<EditText>(R.id.muscle_group)
        var equipmentInput: EditText = findViewById<EditText>(R.id.equipment)

        var s1:String=""
        var s2:String=""
        val url = String.format("http://10.0.2.2:5000/log-setting/?user_name=%s&muscle_group=%s&equipment=%s&ex_type=%s&level=%s",username,muscleGroupInput.getText(),equipmentInput.getText(),exTypeInput.getText(),levelInput.getText())
        val queue = Volley.newRequestQueue(this)
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                s1 = response.getJSONObject("desc").toString()
                s2 = response.getJSONObject("title").toString()
            },
            Response.ErrorListener { error ->
                levelInput.setText(url)
                println(error)
            }
        )
        queue.add(jsonObjectRequest)
        println("this is s1")
        println(s1)
        return Pair(s1,s2)
    }
    private fun switchLActivities() {
        val switchActivityIntent: Intent = Intent(
            this,
            MainActivity::class.java
        )
        startActivity(switchActivityIntent)
    }

    private fun switchVSActivities(username:String) {
        receivePrefData(username)
        updateWeightData(username)
        val switchActivityIntent: Intent = Intent(
            this,
            ViewStats::class.java
        )
        switchActivityIntent.putExtra("username",username)
        startActivity(switchActivityIntent)
    }

    private fun switchVEActivities(username:String) {
        var el = receivePrefData(username)
        updateWeightData(username)
        val switchActivityIntent: Intent = Intent(
            this,
            ViewExercise::class.java
        )
        switchActivityIntent.putExtra("username",username)
        switchActivityIntent.putExtra("exercise titles",el.first)
        switchActivityIntent.putExtra("exercise description",el.second)

        startActivity(switchActivityIntent)
    }


}