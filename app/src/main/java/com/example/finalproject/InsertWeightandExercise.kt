package com.example.finalproject

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.finalProject.R
import com.google.android.material.textfield.TextInputEditText
import org.json.JSONArray

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


        logoutBtn.setOnClickListener(View.OnClickListener {
            switchLActivities() })
        viewStatsBtn.setOnClickListener(View.OnClickListener {
            switchVSActivities(username, object:VSActivityCallback {
                override fun onSuccess(
                    switchActivityIntent: Intent,
                    s1: JSONArray,
                    s2: JSONArray,
                    s3: String
                ) {
                    println("Switching context")
                    switchActivityIntent.putExtra("desc", s1.toString())
                    switchActivityIntent.putExtra("title", s2.toString())
                    switchActivityIntent.putExtra("username", s3)
                    startActivity(switchActivityIntent)
                }
            })
        })
        startExercise.setOnClickListener(View.OnClickListener {
            switchVEActivities(username, object:VSActivityCallback {
                override fun onSuccess(
                    switchActivityIntent: Intent,
                    s1: JSONArray,
                    s2: JSONArray,
                    s3: String
                ) {
                    println("Switching context")
                    val ans1 : ArrayList<String> = ArrayList<String>();
                    for(i in 0..(s1.length()-1)) {
                        ans1.add(s1.get(i).toString())
                    }
                    val ans2 : ArrayList<String> = ArrayList<String>();
                    for(i in 0..(s2.length()-1)){
                        ans2.add(s2.get(i).toString())
                    }
                    switchActivityIntent.putExtra("desc", ans1)
                    switchActivityIntent.putExtra("title", ans2)
                    switchActivityIntent.putExtra("username", s3)
                    startActivity(switchActivityIntent)
                }
            }



            ) })

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
    private fun switchLActivities() {
        val switchActivityIntent: Intent = Intent(
            this,
            MainActivity::class.java
        )

        startActivity(switchActivityIntent)
    }

    interface VSActivityCallback {
        fun onSuccess(intent: Intent, s1:JSONArray, s2:JSONArray, s3:String)
        //fun onError(error: VolleyError )
    }

    private fun switchVSActivities(username:String, callback:VSActivityCallback) {
        var levelInput: EditText = findViewById<EditText>(R.id.level)
        var exTypeInput: EditText = findViewById<EditText>(R.id.ex_type)
        var muscleGroupInput: EditText = findViewById<EditText>(R.id.muscle_group)
        var equipmentInput: EditText = findViewById<EditText>(R.id.equipment)
        updateWeightData(username)
        val switchActivityIntent: Intent = Intent(
            this,
            ViewStats::class.java
        )

        val url = String.format("http://10.0.2.2:5000/log-setting/?user_name=%s&muscle_group=%s&equipment=%s&ex_type=%s&level=%s",username,muscleGroupInput.getText(),equipmentInput.getText(),exTypeInput.getText(),levelInput.getText())
        val queue = Volley.newRequestQueue(this)
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                println(response.get("desc"))
                callback.onSuccess(switchActivityIntent, response.getJSONArray("desc"),response.getJSONArray("title"),username)

            },
            Response.ErrorListener { error ->
                levelInput.setText(url)
                println(error)
            }
        )
        queue.add(jsonObjectRequest)

    }

    private fun switchVEActivities(username:String, callback:VSActivityCallback) {
        updateWeightData(username)
        val switchActivityIntent: Intent = Intent(
            this,
            ViewExercise::class.java
        )
        var levelInput: EditText = findViewById<EditText>(R.id.level)
        var exTypeInput: EditText = findViewById<EditText>(R.id.ex_type)
        var muscleGroupInput: EditText = findViewById<EditText>(R.id.muscle_group)
        var equipmentInput: EditText = findViewById<EditText>(R.id.equipment)
        val url = String.format("http://10.0.2.2:5000/log-setting/?user_name=%s&muscle_group=%s&equipment=%s&ex_type=%s&level=%s",username,muscleGroupInput.getText(),equipmentInput.getText(),exTypeInput.getText(),levelInput.getText())
        val queue = Volley.newRequestQueue(this)
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                callback.onSuccess(switchActivityIntent, response.getJSONArray("desc"),response.getJSONArray("title"),username)

            },
            Response.ErrorListener { error ->
                levelInput.setText(url)
                println(error)
            }
        )
        queue.add(jsonObjectRequest)

    }



}