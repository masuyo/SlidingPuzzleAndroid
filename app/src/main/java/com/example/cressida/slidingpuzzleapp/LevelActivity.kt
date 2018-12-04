package com.example.cressida.slidingpuzzleapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.cressida.slidingpuzzleapp.logic.Board
import kotlinx.android.synthetic.main.activity_level.*

class LevelActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_level)

        var mapstring = intent.getStringExtra("map")

        board_view.Load(Board(mapstring))
    }
}
