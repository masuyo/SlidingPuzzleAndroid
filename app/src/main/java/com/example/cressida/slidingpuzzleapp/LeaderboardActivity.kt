package com.example.cressida.slidingpuzzleapp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.example.cressida.slidingpuzzleapp.logic.FireDB
import com.example.cressida.slidingpuzzleapp.logic.LeaderboardAdapter
import com.example.cressida.slidingpuzzleapp.model.LeaderboardData
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_leaderboard.*

class LeaderboardActivity : AppCompatActivity(), FireDB.IFireCallback {


    override fun ProcessData(data: ArrayList<LeaderboardData>) {
        rv_leaderboard.layoutManager = LinearLayoutManager(this) as RecyclerView.LayoutManager?
        rv_leaderboard.adapter = LeaderboardAdapter(data)
    }

    override fun onStart() {
        super.onStart()

        var mDocRef = FirebaseFirestore.getInstance().collection("users")

        FireDB.GetAllHighScore(this)


    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leaderboard)


    }

}
