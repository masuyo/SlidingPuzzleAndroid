package com.example.cressida.slidingpuzzleapp

import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class LevelSelectActivity : AppCompatActivity() {

    private val filename = "SlidingGameMaps"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_levelselect)

        var playMap1 = findViewById<ImageView>(R.id.imageViewButton)
        playMap1.setOnClickListener {
            val intent = Intent( this, LevelActivity::class.java)
            intent.putExtra("map",getMaps(1))
            startActivity(intent)
        }
        var playMap2 = findViewById<ImageView>(R.id.imageViewButton2)
        playMap2.setOnClickListener {
            val intent = Intent( this, LevelActivity::class.java)
            intent.putExtra("map",getMaps(2))
            startActivity(intent)
        }
        var playMap3 = findViewById<ImageView>(R.id.imageViewButton3)
        playMap3.setOnClickListener {
            val intent = Intent( this, LevelActivity::class.java)
            intent.putExtra("map",getMaps(3))
            startActivity(intent)
        }
        var playMap4 = findViewById<ImageView>(R.id.imageViewButton4)
        playMap4.setOnClickListener {
            val intent = Intent( this, LevelActivity::class.java)
            intent.putExtra("map",getMaps(4))
            startActivity(intent)
        }
        var playMap5 = findViewById<ImageView>(R.id.imageViewButton5)
        playMap5.setOnClickListener {
            val intent = Intent( this, LevelActivity::class.java)
            intent.putExtra("map",getMaps(5))
            startActivity(intent)
        }
    }
    fun createMapsFile(){

        val settings = getSharedPreferences(filename, MODE_PRIVATE)
        val editor = settings.edit()
        editor.putString("lvl1","7,0 2 2 false,0 1 2 false,1 3 3 false,2 4 2 true,3 4 3 false,4 0 3 true,5 0 3 true")
        editor.putString("lvl2","7,1 2 2 false,1 4 2 true,2 1 3 false,3 0 2 false,3 3 3 true,4 3 2 false,5 0 3 true")
        editor.putString("lvl3","10,0 2 2 false,2 3 3 false,2 0 3 false,2 1 3 false,2 4 2 true,3 6 2 false,4 2 3 true")
        editor.putString("lvl4","10,0 2 2 false,1 0 3 false,1 1 2 false,1 3 3 false,2 4 2 true,3 4 2 true,4 0 3 true,4 5 2 false")
        editor.putString("lvl5","15,0 2 2 false,0 3 1 false,0 4 3 false,0 5 2 false,2 0 3 true,2 3 2 false,3 4 2 true,4 1 2 false,4 5 2 false,5 2 3 true")
        editor.putBoolean("initialized",true)
        editor.apply()
    }

     fun getMaps(lvlnumber: Int ):String{
         val key = "lvl$lvlnumber"
         val settings = getSharedPreferences(filename, MODE_PRIVATE)
         if (!settings.contains("initialized"))
         {
             createMapsFile()
         }
         return settings.getString(key, "1,0 2 2 false") ?: "1,0 2 2 false"
     }
}
