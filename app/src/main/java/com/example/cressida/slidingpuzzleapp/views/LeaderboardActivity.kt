package com.example.cressida.slidingpuzzleapp.views

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import com.example.cressida.slidingpuzzleapp.R
import com.example.cressida.slidingpuzzleapp.logic.FireDB
import com.example.cressida.slidingpuzzleapp.logic.LeaderboardAdapter
import kotlinx.android.synthetic.main.activity_leaderboard.*
import kotlinx.android.synthetic.main.activity_main.*

class LeaderboardActivity : AppCompatActivity() {

    val fireDB = FireDB()

    var highScores = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leaderboard)

        AddData()

        rv_leaderboard.layoutManager = LinearLayoutManager(this)
        rv_leaderboard.adapter = LeaderboardAdapter(highScores, this)


    }

    private fun AddData() {
        highScores.add("asd")
        highScores.add("asd2")
        highScores.add("asd3")
        highScores.add("asd4")
        highScores.add("asd5")
        highScores.add("asd6")
        highScores.add("asd7")
        highScores.add("asd8")
        highScores.add("asd9")
        highScores.add("asd10")
        highScores.add("asd11")
        highScores.add("asd12")
        highScores.add("asd13")
        highScores.add("asd14")
        highScores.add("asd15")
        highScores.add("asd16")
        highScores.add("asd17")
    }
}
