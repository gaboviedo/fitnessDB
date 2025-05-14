package com.example.finalproject

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.finalProject.R
import com.google.android.material.textfield.TextInputEditText

class MainActivity :AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val switchToCreateActivity: Button = findViewById<Button>(R.id.createUser)
        val switchToPreferenceActivity: Button = findViewById<Button>(R.id.login)
        var userName: TextInputEditText = findViewById<TextInputEditText>(R.id.enterUser)



        switchToCreateActivity.setOnClickListener(View.OnClickListener { switchcActivities() })
        switchToPreferenceActivity.setOnClickListener(View.OnClickListener { switchpActivities() })



    }

    private fun switchcActivities() {
        val switchActivityIntent: Intent = Intent(
            this,
            CreateUser::class.java
        )
        startActivity(switchActivityIntent)
    }
    private fun switchpActivities() {
        var userName: TextInputEditText = findViewById<TextInputEditText>(R.id.enterUser)
        val url = String.format("http://10.0.2.2:5000/login/%s",userName.getText())

        val queue = Volley.newRequestQueue(this)
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,

            Response.Listener { response ->

                userName.setText("Response: %s".format(response.toString()))
            },
            Response.ErrorListener { error ->
                userName.setText("user not found")
            }

        )
        queue.add(jsonObjectRequest)

        val switchActivityIntent: Intent = Intent(
            this,
            InsertWeightandExercise::class.java
        )
        switchActivityIntent.putExtra("username",userName.getText().toString())

        startActivity(switchActivityIntent)
    }

}