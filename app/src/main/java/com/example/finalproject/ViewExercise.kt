package com.example.finalproject

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.finalProject.R
import org.json.JSONArray
public var index = 0
class ViewExercise : AppCompatActivity()  {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_exercise)

        val username =getIntent().getStringExtra("username").toString()
        val temp_titles =getIntent().getStringArrayListExtra("title")
        val temp_desc = getIntent().getStringArrayListExtra("desc")

        val descriptions:ArrayList<String> = ArrayList<String>();
        for(i in 0..(temp_desc!!.size-1)){
            descriptions.add(temp_desc!!.get(i))
        }
        val titleList:ArrayList<String> = ArrayList<String>();
        for(i in 0..(temp_titles!!.size-1)){
            titleList.add(temp_titles!!.get(i))
        }
        var elapsed:TextView = findViewById<TextView>(R.id.elapsedInfo)
        var currTitle:TextView = findViewById<TextView>(R.id.exTitle)
        var remaining:TextView = findViewById<TextView>(R.id.remainingEx)
        var descTextEx:TextView = findViewById<TextView>(R.id.ex_desc)
        var setNext:Button = findViewById<Button>(R.id.nextSet)
        var exNext:Button = findViewById<Button>(R.id.nextEx)
        var switchBack:Button = findViewById<Button>(R.id.exitbtn)
        currTitle.setText(titleList.get(index))
        descTextEx.setText(descriptions.get(index))
        remaining.setText((descriptions.size-index).toString())
        switchBack.setOnClickListener(View.OnClickListener { switcheActivities() })
        setNext.setOnClickListener(View.OnClickListener { increaseSets(elapsed) })
        exNext.setOnClickListener(View.OnClickListener { nextExercise(titleList,descriptions) })

        //val listOfStrings = Gson().fromJson(wordReview, mutableListOf<String>().javaClass)







    }
    private fun nextExercise(titleList:ArrayList<String>,descriptions:ArrayList<String>){

        var remaining:TextView = findViewById<TextView>(R.id.remainingEx)
        var currTitle:TextView = findViewById<TextView>(R.id.exTitle)
        var descTextEx:TextView = findViewById<TextView>(R.id.ex_desc)
        var elapsed:TextView = findViewById<TextView>(R.id.elapsedInfo)
        currTitle.setText(titleList.get(index))
        descTextEx.setText(descriptions.get(index).toString())
        remaining.setText("exercises remaining")
        remaining.append(descriptions.size.minus(index).toString())
        index++
        elapsed.setText("sets elapsed: 0")






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