package com.example.cressida.slidingpuzzleapp.logic



class Board(mapstring: String) {


    var minStep: Int = 0
    var actualStep: Int = 0
    var table: ArrayList<Block> = ArrayList()
    private val pruposex = 5
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
