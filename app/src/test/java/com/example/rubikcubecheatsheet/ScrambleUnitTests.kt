package com.example.rubikcubecheatsheet

import com.example.rubikcubecheatsheet.model.scramble.Helper
import com.example.rubikcubecheatsheet.model.scramble.Scramble
import org.junit.Assert
import org.junit.Test

class ScrambleUnitTests {

    private val scramble = Scramble(20)

    private val reverseMap : Map<String, Int> = mapOf(
            "R" to 0, "L" to 1, "U" to 2, "D" to 3, "F" to 4, "B" to 5,
            "R'" to 6, "L'" to 7, "U'" to 8, "D'" to 9, "F'" to 10, "B'" to 11,
            "R2" to 12, "L2" to 13, "U2" to 14, "D2" to 15, "F2" to 16, "B2" to 17)

    @Test
    public fun returnArrayHas20Elements() {
        val scrambleList = scramble.run()

        Assert.assertEquals(20, scrambleList.size)
    }

    @Test
    public fun everyValueBelow18() {
        for (i in 0..10) {
            val scrambleList = scramble.run()

            for (letter: String in scrambleList)
                Assert.assertTrue(reverseMap[letter]!! < 18)
        }
    }

    @Test
    public fun directionsDoNotRepeat() {

        for (k in 0..10) {
            val scrambleList : List<String> = scramble.run()

            for (i in scrambleList.indices)
                if (i > 0) {
                    val curr : Int = reverseMap[scrambleList[0]]!!
                    val pre : Int = reverseMap[scrambleList[1]]!!
                    Assert.assertFalse(curr == pre || curr + 6 == pre || curr - 6 == pre || curr + 12 == pre || curr - 12 == pre)
                }
        }
    }

    @Test
    public fun everyNumberAppearsAtLeastOnce() {
        val check = IntArray(100)

        for (i in 0..10) {
            val scrambleList: List<String> = scramble.run()

            for (letter : String in scrambleList)
                check[reverseMap[letter]!!]++
        }

        for (i in 0..17) {
            //println("$i ${check[i]}")
            Assert.assertTrue(check[i] > 0)

        }
    }

    @Test
    public fun printScrambles() {
        val input = generateIntList(arrayOf("L", "R", "L", "U", "L", "U", "L", "U", "L", "U", "L", "U", "L", "U", "L", "U", "L", "U", "L", "U", "L", "U", "L", "U", "L", "U", "L", "U", "L", "U", "L"))
        val expected = generateIntList(arrayOf("L", "R", "U", "L", "U", "L", "U", "L", "U", "L", "U", "L", "U", "L", "U", "L", "U", "L", "U", "L"))
        val actual : IntArray = scramble.generateNumberArray(input)

        Assert.assertArrayEquals(expected.toIntArray(), actual)
    }

    @Test
    public fun createArrayFromReverseMap() {
        val letters = arrayOf("R", "L", "U", "D", "F", "B", "R'", "L'", "U'", "D'", "F'", "B'", "R2", "L2", "U2", "D2", "F2", "B2")
        val expectedResult = intArrayOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17)


        Assert.assertArrayEquals(generateIntList(letters).toIntArray(), expectedResult)

    }

    private fun generateIntList(letters : Array<String>) : List<Int> {
        val output = mutableListOf<Int>()

        for (letter in letters)
            output.add(reverseMap[letter]!!)

        return output
    }

    @Test
    public fun isOppositeSideMove() {
        val curr = reverseMap["U"]!!
        val pre = reverseMap["D'"]!!
        val prePre = reverseMap["U'"]!!
        Assert.assertTrue(Helper.isOppositeSideMove(curr, pre) && Helper.isSameMove(curr, prePre))
    }
}