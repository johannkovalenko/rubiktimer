package com.example.rubikcubecheatsheet.model.statistics.category.ao.day

import java.time.LocalDateTime

public class Day {
    var topAvgEver = Float.MAX_VALUE
    var topEntryEver = Float.MAX_VALUE
    var top = Float.MAX_VALUE
    var amount = 0
    var sum = 0f
    var dnf = 0
    var avg = Float.MAX_VALUE
    var date: LocalDateTime = LocalDateTime.MIN
    var percentiles : MutableMap<Int, Cumulative> = sortedMapOf()

    var one100thBest = Float.MAX_VALUE
    var numberOfHundredBest = 0

    class Cumulative {
        var total = 1
        var addedTotal = 0
    }
}