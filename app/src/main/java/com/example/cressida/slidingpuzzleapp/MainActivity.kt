package com.example.cressida.slidingpuzzleapp

import android.content.Intent
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v7.app.AppCompatActivity
import android.view.MotionEvent
import android.view.TouchDelegate
import android.view.View
import com.example.cressida.slidingpuzzleapp.views.BoardView

class MainActivity : AppCompatActivity() {

    //private var myLayout:BoardView = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return super.onTouchEvent(event)
        return false
    }

}