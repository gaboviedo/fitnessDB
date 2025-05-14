package com.example.finalproject

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.finalProject.R

class ViewExercise : AppCompatActivity()  {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_exercise)
//        val eTitle
        val username =getIntent().getStringExtra("username").toString()
        var elapsed:TextView = findViewById<TextView>(R.id.elapsedInfo)
        var currTitle:TextView = findViewById<TextView>(R.id.exTitle)
        var remaining:TextView = findViewById<TextView>(R.id.remainingEx)
        var descTextEx:TextView = findViewById<TextView>(R.id.ex_desc)
        var remainder:TextView = findViewById<TextView>(R.id.remainingEx)
        var setNext:Button = findViewById<Button>(R.id.nextSet)
        var exNext:Button = findViewById<Button>(R.id.nextEx)
        var switchBack:Button = findViewById<Button>(R.id.exitbtn)
        switchBack.setOnClickListener(View.OnClickListener { switcheActivities() })
        setNext.setOnClickListener(View.OnClickListener { increaseSets(elapsed) })







    }
    private fun switcheActivities() {
        val switchActivityIntent: Intent = Intent(
            this,
            MainActivity::class.java
        )
        startActivity(switchActivityIntent)
    }
    private fun increaseSets(setNext:TextView){
        var x = setNext.getText().toString().last().toString().toInt()+1
        val oStr = setNext.getText()
        if(x>9){
            setNext.setText("that's too many sets. Move on.")
            return
        }
        else{
            var newStr = oStr.dropLast(1).toString()
            val str = newStr.plus(x.toString())
            println(str)
            setNext.setText(str)
            return

        }
    }

}