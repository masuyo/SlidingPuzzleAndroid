package com.example.cressida.slidingpuzzleapp.logic

import java.util.*
import kotlin.properties.Delegates


class Board(mapstring: String) : Observable() {


    var minStep: Int = 0
    var actualStep: Int = 0
    var table: ArrayList<Block> = ArrayList()
    private val pruposex = 5
    var mapName = ""
    val exit: Block = Block(5,2,1,false)
    init {
        loadMap(mapstring)
    }


    fun loadMap(mapstring: String) {
        //   IsEnded = false
        var map = mapstring.split(',')
        try {
            minStep = map[0].toInt()
            mapName = map[1]
            for (i in 2 until map.size) {
                var block = map[i].split(' ')
                var vertical = false
                if (block[3] == "true")
                    vertical = true

                table.add(Block(
                        block[0].toInt(),
                        block[1].toInt(),
                        block[2].toInt(),
                        vertical
                ))
            }
        } catch (nfe: NumberFormatException) {

        } catch (oii: IndexOutOfBoundsException) {

        }
    }
    fun valueInit(){
        setChanged()
        notifyObservers((minStep).toLong())
        setChanged()
        notifyObservers(actualStep)
    }

    fun gameOver() {

        var point = ((minStep.toDouble()/actualStep.toDouble())*100).toInt()
        FireDB.UploadScore(mapName,point)
        setChanged()
        notifyObservers(true)
    }

    fun move() {
        actualStep++
        setChanged()
        notifyObservers(actualStep)
    }
}
