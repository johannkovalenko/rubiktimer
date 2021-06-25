package com.example.rubikcubecheatsheet.model.statistics.category

import com.example.rubikcubecheatsheet.model.Entry
import com.example.rubikcubecheatsheet.model.statistics.category.ao.day.Day

data class Data (val numberOfElements: Int, val maxDNF : Int, val entries: MutableList<Entry>) {
    public var allTimeTop       = Float.MAX_VALUE
    public var currentPosition  = 0

    public val days : MutableMap<String, Day>   = mutableMapOf("top" to Day(), "current" to Day())
    public val currentSeconds : MutableList<Float> = mutableListOf()
}