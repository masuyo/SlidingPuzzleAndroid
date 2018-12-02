package com.example.cressida.slidingpuzzleapp.views

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.example.cressida.slidingpuzzleapp.R
import com.example.cressida.slidingpuzzleapp.logic.FireDB
import com.example.cressida.slidingpuzzleapp.logic.LeaderboardAdapter
import kotlinx.android.synthetic.main.activity_leaderboard.*

class LeaderboardActivity : AppCompatActivity() {


    val fireDB = FireDB()


    override fun onStart() {
        super.onStart()


        rv_leaderboard.layoutManager = LinearLayoutManager(this) as RecyclerView.LayoutManager?
        rv_leaderboard.adapter = LeaderboardAdapter(FireDB.highScoreList)
        fireDB.GetAllHighScore((rv_leaderboard.adapter as LeaderboardAdapter))
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leaderboard)




    }

}
