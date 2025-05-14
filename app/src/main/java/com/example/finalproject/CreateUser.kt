package com.example.finalproject

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.finalProject.R
import com.google.android.material.textfield.TextInputEditText

class CreateUser :AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_create)
        var createUser: Button = findViewById<Button>(R.id.createUser)
        var inputUsername: TextInputEditText = findViewById<TextInputEditText>(R.id.inputUsername)
        var inputWeight: TextInputEditText = findViewById<TextInputEditText>(R.id.inputWeight)
        createUser.setOnClickListener(View.OnClickListener { switchActivities() })

    }
    private fun switchActivities() {
        var inputUsername: TextInputEditText = findViewById<TextInputEditText>(R.id.inputUsername)
        var inputWeight: EditText = findViewById<EditText>(R.id.inputWeight)

        val url = String.format("http://10.0.2.2:5000/create/?user_name=%s&weight=%s",inputUsername.getText(),inputWeight.getText())
        val queue = Volley.newRequestQueue(this)
        val jsonObjectRequest = JsonObjectRequest(Request.Method.POST, url, null,
            Response.Listener { response ->
                inputUsername.setText("Response: %s".format(response.toString()))

            },
            Response.ErrorListener { error ->
                inputUsername.setText(url)
                println(error)
            }
        )
        queue.add(jsonObjectRequest)
        val switchActivityIntent: Intent = Intent(
            this,
            InsertWeightandExercise::class.java
        )
        switchActivityIntent.putExtra("username",inputUsername.getText())
        startActivity(switchActivityIntent)
    }


}