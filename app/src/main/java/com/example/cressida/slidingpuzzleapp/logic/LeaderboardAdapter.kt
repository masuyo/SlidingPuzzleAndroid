
package com.example.cressida.slidingpuzzleapp.logic

import android.graphics.Color
import android.graphics.Typeface
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.cressida.slidingpuzzleapp.model.LeaderboardData
import com.example.cressida.slidingpuzzleapp.R
import kotlinx.android.synthetic.main.leaderboard_list_item.view.*


class LeaderboardAdapter(var items: ArrayList<LeaderboardData>) : RecyclerView.Adapter<CustomViewHolder>() {


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(p0.context)
        val cellForRow = layoutInflater.inflate(R.layout.leaderboard_list_item, p0, false)

        return CustomViewHolder(cellForRow)
    }

    override fun getItemCount(): Int {
        Log.e("sajt", items.count().toString())

        return items.count()

    }

    override fun onBindViewHolder(p0: CustomViewHolder, p1: Int) {
        Log.e("asd", "onBindViewHolder called!")
        p0.view.tv_leaderboard_username.text = items.get(p1).username
        p0.view.tv_leaderboard_score.text = items.get(p1).highscore.toString()

        when (p1) {
            0 -> {
                p0.view.setBackgroundResource(R.drawable.leaderboard_background_border_gold)
                p0.view.tv_leaderboard_username.textSize = 30.0f
                p0.view.tv_leaderboard_username.setTypeface(null, Typeface.BOLD)

                p0.view.tv_leaderboard_score.textSize = 30.0f
                p0.view.tv_leaderboard_score.setTypeface(null, Typeface.BOLD)

            }

            1 -> {
                p0.view.setBackgroundResource(R.drawable.leaderboard_background_border_silver)
                p0.view.tv_leaderboard_username.textSize = 27.0f
                p0.view.tv_leaderboard_username.setTypeface(null, Typeface.BOLD)

                p0.view.tv_leaderboard_score.textSize = 27.0f
                p0.view.tv_leaderboard_score.setTypeface(null, Typeface.BOLD)
            }

            2 -> {
                p0.view.setBackgroundResource(R.drawable.leaderboard_background_border_bronze)
                p0.view.tv_leaderboard_username.textSize = 24.0f
                p0.view.tv_leaderboard_username.setTypeface(null, Typeface.BOLD)

                p0.view.tv_leaderboard_score.textSize = 24.0f
                p0.view.tv_leaderboard_score.setTypeface(null, Typeface.BOLD)
            }
        }

    }

}

class CustomViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

}



