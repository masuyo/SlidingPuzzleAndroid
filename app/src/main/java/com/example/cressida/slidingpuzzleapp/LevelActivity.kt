package com.example.cressida.slidingpuzzleapp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MotionEvent
import com.example.cressida.slidingpuzzleapp.views.BoardView

class LevelActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_level)
        val view = findViewById<BoardView>(R.id.board_view)



    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return super.onTouchEvent(event)
        return false
    }

}