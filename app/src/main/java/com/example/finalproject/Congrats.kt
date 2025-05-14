package com.example.finalproject

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.util.LruCache
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.toolbox.ImageLoader
import com.android.volley.toolbox.NetworkImageView
import com.android.volley.toolbox.Volley
import com.example.finalProject.R
import com.google.android.material.textfield.TextInputEditText

class Congrats : AppCompatActivity()  {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_congrats)
        val username =getIntent().getStringExtra("username").toString()
        var statsView:Button = findViewById<Button>(R.id.viewStats)
        var logout:Button = findViewById<Button>(R.id.logout)
        statsView.setOnClickListener(View.OnClickListener { switchVSActivities(username) })
        logout.setOnClickListener(View.OnClickListener { switchmActivities() })

        var imageCat: NetworkImageView = findViewById<NetworkImageView>(R.id.imageView)
        val url = "https://cataas.com/cat"
        val queue = Volley.newRequestQueue(this)
        val imageLoader = ImageLoader(queue, object : ImageLoader.ImageCache {
            private val mCache: LruCache<String, Bitmap> = LruCache<String, Bitmap>(10)
            override fun putBitmap(url: String, bitmap: Bitmap) {
                mCache.put(url, bitmap)
            }

            override fun getBitmap(url: String): Bitmap? {
                return mCache.get(url)
            }
        })

        imageCat.setImageUrl(url, imageLoader)


    }
    private fun switchmActivities() {
        val switchActivityIntent: Intent = Intent(
            this,
            MainActivity::class.java
        )
        startActivity(switchActivityIntent)
    }
    private fun switchVSActivities(username:String) {
        val switchActivityIntent: Intent = Intent(
            this,
            ViewStats::class.java
        )
        switchActivityIntent.putExtra("username",username)
        startActivity(switchActivityIntent)
    }
}