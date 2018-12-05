package com.example.cressida.slidingpuzzleapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.cressida.slidingpuzzleapp.logic.Board
import kotlinx.android.synthetic.main.activity_level.*
import java.util.*


class LevelActivity : AppCompatActivity(), Observer {
    override fun update(o: Observable?, arg: Any?) {
        when (o){ is  Board -> {
            if (arg is Int)
            {
                actualStepCount.text = arg.toString()
            }
            if (arg is Long)
            {
               optimalStepCount.text = arg.toString()
            }
        } }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_level)




        var mapstring = intent.getStringExtra("map")
        var board = Board(mapstring)
        board.addObserver(this)
        board.valueInit()
        board_view.load(board)
        board_view.setLayerType(View.LAYER_TYPE_SOFTWARE, null)



/*        val intent = Intent( this, LevelSelectActivity::class.java)
        startActivity(intent)*/
    }
}
