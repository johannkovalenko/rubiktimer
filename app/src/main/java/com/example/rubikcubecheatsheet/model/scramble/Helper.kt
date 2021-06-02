package com.example.rubikcubecheatsheet.model.scramble

object Helper {
    public fun isSameMove(curr : Int, pre : Int) : Boolean {
        return curr == pre || curr + 6 == pre || curr - 6 == pre || curr + 12 == pre || curr - 12 == pre
    }

    public fun isOppositeSideMove(curr : Int, pre : Int) : Boolean {
        return when (curr % 2) {
            0 -> pre == curr + 1 || pre == curr + 1 + 6 || pre == curr + 1 + 12 || pre == curr + 1 - 6 || pre == curr + 1 - 12
            1 -> pre == curr - 1 || pre == curr - 1 + 6 || pre == curr - 1 + 12 || pre == curr - 1 - 6 || pre == curr - 1 - 12
            else -> false
        }
    }
}