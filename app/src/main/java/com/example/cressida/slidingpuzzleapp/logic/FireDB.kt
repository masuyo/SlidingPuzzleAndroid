package com.example.cressida.slidingpuzzleapp.logic

import android.util.Log
import com.example.cressida.slidingpuzzleapp.model.LeaderboardData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class FireDB {

    companion object : FireControl() {

        private val db = FirebaseFirestore.getInstance()
        private val uid = GetUID()

        fun UploadScore(level: String, score: Int) {
            val data = HashMap<String, Any>()
            data["score"] = score

            db.collection("users").document(uid).collection("levelscores").document(level).set(data).addOnSuccessListener {
                UpdateHighScore()
            }
        }

        private fun GetUID(): String {

            if (auth.uid == null) {
                throw Exception()
            } else {
                return (auth.uid as String)
            }

        }


        fun GetScore(level: String): Double {
            var score: Double
            score = 0.toDouble()

            db.collection("users").document(uid).collection("levelscores").document(level)
                    .get()
                    .addOnSuccessListener {
                        score = it.getDouble("score")!!
                    }

            return score
        }

        fun GetAllScore(toCall: IFireCallback) {
            var data = ArrayList<LeaderboardData>()

            db.collection("users").document(uid).collection("levelscores").get()
                    .addOnSuccessListener {
                        for (document in it) {
                            data.add(document.toObject(LeaderboardData::class.java))
                        }

                        toCall.ProcessData(data)
                    }


        }

        fun GetAllHighScore(toCall: IFireCallback) {
            val uCollection = db.collection("users")

            var highScoreList = ArrayList<LeaderboardData>()

            uCollection.orderBy("highscore", Query.Direction.DESCENDING).get()
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            for (document in task.getResult()!!) {

                                highScoreList.add(document.toObject(LeaderboardData::class.java))
                                //Log.e("sajt", highScoreList.count().toString())
                            }
                            toCall.ProcessData(highScoreList)
                        }

                    }

            Log.e("sajt", highScoreList.count().toString())
            for (data in highScoreList) {
                Log.e("sajt", data.username + " " + data.highscore)
            }
        }

        fun GetHighScore(): Double {
            var highScore: Double
            highScore = 0.toDouble()

            db.collection("users").document(uid).get()
                    .addOnSuccessListener {
                        highScore = it.getDouble("highscore")!!
                    }

            return highScore
        }

        private fun UpdateHighScore() {
            val uDocument = db.collection("users").document(uid)
            val uScores = db.collection("users").document(uid).collection("levelscores")
            var newScore: Double


            uScores.get()
                    .addOnSuccessListener {

                        var scores = ArrayList<Double>()
                        for (document in it) {
                            scores.add(document.getDouble("score")!!)

                        }

                        newScore = 0.toDouble()

                        for (item in scores) {
                            newScore += item
                        }

                        Log.e("sum", newScore.toString())

                        uDocument.update("highscore", newScore)
                    }
        }

    }

    interface IFireCallback {
        fun ProcessData(data: ArrayList<LeaderboardData>)
    }


}