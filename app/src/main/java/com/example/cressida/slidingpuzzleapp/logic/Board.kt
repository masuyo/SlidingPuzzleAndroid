package com.example.cressida.slidingpuzzleapp.logic

import kotlin.properties.Delegates

class Board {

    var IsEnded: Boolean by Delegates.observable(false) { _, oldValue, newValue ->
        onGameEnding?.invoke(oldValue, newValue)
    }
    var onGameEnding: ((Boolean, Boolean) -> Unit)? = null //akkor is jelez amikor false-ra vissza állítjuk


    private var size: Int = 0
    var sizerect: Int = 0
    var minStep: Int =0
    var actualStep: Int = 0
    private var minedge: Int = 0
    private var maxedge: Int = 0
    var table: ArrayList<Block> = ArrayList()
    private val pruposex = 5
    private val finisher: Block = Block(0, 3, 2, false)
    private var target: Block = Block()
    fun loadMap()
    {
        IsEnded = false
        table.add(finisher)
        minStep = 2
        table.add(Block(3, 3, 3, true))
        setSize(size)
    }
    fun setSize(Size:Int )
    {
        size = Size
        sizerect = Size/6
        for ( i in 0 until table.size )
        {
            table[i].cordinateX = table[i].x * sizerect
            table[i].cordinateY = table[i].y * sizerect
        }
    }
    private fun isEnded()
    {
        if (finisher.x + size - 1 == pruposex)
        {
            IsEnded = false
        }
    }

    private fun elementontablepoint(x: Int, y: Int): Block
    {
        for ( i in 0 until table.size )
        {
            if (!table[i].vertical && table[i].x == x)
            {
                if (table[i].y <= y && y < table[i].y+table[i].size)
                    return table[i]
            }
            if (table[i].vertical && table[i].y == y)
            {
                if (table[i].x <= x && x < table[i].x+table[i].size)
                    return table[i]
            }
        }
        return com.example.cressida.slidingpuzzleapp.logic.Block()
    }

    private fun elementInCordinate(x: Int, y: Int): Block {
        val xp = x/sizerect
        val xy = y/sizerect
        return elementontablepoint(xp,xy)
    }
    //frissíteni kell utána a megjelenitést,   ha elengedjük a képernyöt ezt kell meghívni, hogy elengedjük az aktuális elem mozgatását.
    fun finishTheMove() {
        if (target.size != -1)
        {
            target.x= target.cordinateX/sizerect //pontositani ha van már alap megjelenités
            target.y = target.cordinateY/sizerect
            target = Block()
            actualStep++
            //megjelenités modositása
            isEnded()
        }
    }
    //frissíteni a megjelenést, a kordináták a tábla sarkához képest értendők (bal felső a 0,0)
    fun move(X: Int,Y: Int,DistanceX: Int,DistanceY: Int) {
        if (target.size == -1){//mozgatandó elem kiválasztása mozgatás határainak kiszámitása
            target = elementInCordinate(X,Y)
            if (target.size != -1){
                if (target.vertical) {//y irányú ág
                    var i = target.y-1
                    var found = false
                    while (  i >= 0  && !found){//amig nem érjük el az akadályozó pontot
                        if ( elementontablepoint(target.x,i).size!=-1)//ha üres a mező
                        {
                            i--
                        }else{
                            found = true
                        }
                    }
                    minedge = (i+1) * sizerect
                    i = target.y + target.size
                    found = false
                    while (  i < 6  && !found)
                    {
                        if (elementontablepoint(target.x,i).size!=-1) {//üres
                            i++
                        }
                        else{
                            found = true
                        }
                    }
                    maxedge = i * sizerect
                }
                if (target.vertical) {//x irányú ág
                    var i = target.x-1
                    var found = false
                    while (  i >= 0  && !found){//amig nem érjük el az akadályozó pontot
                        if ( elementontablepoint(i,target.y).size!=-1)//ha üres a mező
                        {
                            i--
                        }else{
                            found = true
                        }
                    }
                    minedge = (i+1) * sizerect //kivitele hogy csak akkor számoljuk ha kell
                    i = target.x + target.size
                    found = false
                    while (  i < 6  && !found)
                    {
                        if (elementontablepoint(i,target.y).size!=-1) {//üres
                            i++
                        }
                        else{
                            found = true
                        }
                    }
                    maxedge = i * sizerect
                }
            }
        }
        if (target.size != -1){//mozgatás
            if (target.vertical) {//y irányú ág
                val newy = target.cordinateY + DistanceY
                if ( DistanceY > 0){//lefele mozog méret
                    if (maxedge > newy + target.size*sizerect){//nem megy túl az útolsó szabad pozición ameddig elcsuszhat
                        target.cordinateY = newy
                    }else{
                        target.cordinateY = maxedge - target.size*sizerect
                    }
                }else{//felfele mozog
                    if (minedge < newy){//nem megy túl az útolsó szabad pozición ameddig elcsuszhat
                        target.cordinateY = newy
                    }else{
                        target.cordinateY = minedge
                    }
                }
            }else{//x irányú ág
                val newx = target.cordinateX + DistanceX
                if ( DistanceX > 0){//jobbra mozog
                    if (maxedge > newx + target.size*sizerect){//nem megy túl az útolsó szabad pozición ameddig elcsuszhat
                        target.cordinateX = newx
                    }else{
                        target.cordinateX = maxedge - target.size*sizerect
                    }
                }else{//felfele mozog
                    if (minedge > newx){//nem megy túl az útolsó szabad pozición ameddig elcsuszhat
                        target.cordinateX = newx
                    }else{
                        target.cordinateX = minedge
                    }
                }
            }
        }
    }
}