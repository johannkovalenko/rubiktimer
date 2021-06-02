package com.example.rubikcubecheatsheet.model.scramble

import kotlin.random.Random

class Scramble (private val number : Int) : IScramble {
    private val map : Map<Int, String> = mapOf(
            0 to "R", 1 to "L", 2 to "U", 3 to "D", 4 to "F", 5 to "B",
            6 to "R'", 7 to "L'", 8 to "U'", 9 to "D'", 10 to "F'", 11 to "B'",
            12 to "R2", 13 to "L2", 14 to "U2", 15 to "D2", 16 to "F2", 17 to "B2")


    public fun run() : List<String>{
        val randomValues = List(100) { Random.nextInt(0, map.size) }
        return convertToStringArray(generateNumberArray(randomValues))
    }

    private fun convertToStringArray(intArray: IntArray) : List<String>{
        val output = mutableListOf<String>()

        for (number in intArray)
            output.add(map[number]!!)

        return output
    }

    public fun generateNumberArray(randomValues : List<Int>) : IntArray {
        val array = IntArray(number)

        var randomOffSet = 0

        for (i in 0 until number) {
            if (i == 0) {
                array[i] = randomValues[0]
                continue
            }

            while (true) {
                val curr = randomValues[i + randomOffSet]

                if (Helper.isSameMove(curr, array[i - 1])) {
                    randomOffSet++
                    continue
                }

                if (i == 1) {
                    array[i] = curr
                    break
                }
                else {
                    if (Helper.isOppositeSideMove(curr, array[i - 1]) && Helper.isSameMove(curr, array[i - 2]))
                        randomOffSet++
                    else {
                        array[i] = curr
                        break
                    }
                }

            }
        }
        return array
    }
}