package com.example.cressida.slidingpuzzleapp.logic

import android.content.Context.MODE_PRIVATE
import kotlin.properties.Delegates

class Board(mapstring: String) {

    /*  var IsEnded: Boolean by Delegates.observable(false) { _, oldValue, newValue ->
        onGameEnding?.invoke(oldValue, newValue)
    }
    var onGameEnding: ((Boolean, Boolean) -> Unit)? = null //akkor is jelez amikor false-ra vissza állítjuk
*/
    //private var size: Int = 0
    var minStep: Int = 0
    var actualStep: Int = 0
    private var minedge: Int = 0
    private var maxedge: Int = 0
    var table: ArrayList<Block> = ArrayList()
    //private var minedge: Int = 0
    //private var maxedge: Int = 0
    private val pruposex = 5
    private val finisher: Block = Block(0, 3, 2, false)
    private var target: Block = Block()
    //private var target: com.example.cressida.slidingpuzzleapp.logic.Block = com.example.cressida.slidingpuzzleapp.logic.Block()


    init {
        loadMap(mapstring)
    }


    fun loadMap(mapstring: String) {
        //   IsEnded = false
        var map = mapstring.split(',')
        try {
            minStep = map[0].toInt()
            for (i in 1 until map.size) {
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

    fun move(block: Block, step: Int): Boolean {
        table.add(finisher)
        minStep = 2
        table.add(Block(3, 3, 3, true))
        if (block.vertical) {
            block.y += step
        } else {
            block.x += step
        }
        actualStep++
        if (table[0].x == pruposex - 1)
            return true
        return false
    }
}
