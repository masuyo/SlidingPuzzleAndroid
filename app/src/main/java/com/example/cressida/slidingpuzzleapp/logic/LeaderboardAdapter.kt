package com.example.cressida.slidingpuzzleapp.logic

import android.content.Context
import android.support.constraint.R.id.parent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.cressida.slidingpuzzleapp.R
import kotlinx.android.synthetic.main.leaderboard_list_item.view.*

class LeaderboardAdapter(val items: ArrayList<String>, val context: Context) : RecyclerView.Adapter<ViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.leaderboard_list_item, p0, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder?.tvLeaderboardUsername.text = items.get(position)
        // holder?.tvLeaderboardHighscore.text = items.get(position).second.toString()
    }

}

class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val tvLeaderboardUsername = view.tv_leaderboard_username
    //val tvLeaderboardHighscore = view.tv_leaderboard_highscore
}