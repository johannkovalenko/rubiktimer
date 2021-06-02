package com.example.rubikcubecheatsheet.model.statistics.category

import com.example.rubikcubecheatsheet.model.Entry
import com.example.rubikcubecheatsheet.model.enumerations.Mode
import com.example.rubikcubecheatsheet.model.statistics.category.ao.day.Day
import com.example.rubikcubecheatsheet.model.statistics.RecordProgression
import com.example.rubikcubecheatsheet.model.statistics.Top100
import com.example.rubikcubecheatsheet.model.statistics.category.ao.*

class Category {
    private val entries         = mutableListOf<Entry>()
    private val aoData            = listOf(Data(5, 0, entries), Data(12, 0, entries), Data(100, 5, entries), Data(1000, 50, entries))

    private var aoList          = arrayOf(AO5(aoData[0]), AO12(aoData[1]), AO100(aoData[2]), AO1000(aoData[3]))

    private val printAO         = PrintAO(aoData, aoList)
    private val recordProgression = RecordProgression(aoData)
    private val top100 = Top100()

    public fun add(entry: Entry) {
        entries.add(entry)

        for (ao : AO in aoList)
            ao.add(entry)

        if (entry.dnf || entry.mode == Mode.Training) return

        top100.check(entry)
        recordProgression.check(entry)
    }

    public fun printShort(sb: StringBuilder) {
        sb.append("<table>")

        for (ao : AO in aoList)
            ao.printShort(sb)

        sb.append("</table>")
    }

    public fun print(sb: StringBuilder, header : String) {
        sb.append("<h1>${header}</h1>")
        sb.append("<table>")

        printAO.percentilesHeader(sb)

        for ((date, day100) in aoData[2].topPerDay) { //hundred
            cumulateAndPercent(day100.percentiles)

            printAO.percentilesBody(sb, date)
        }
        sb.append("</table>")

        top100.print(sb, aoData[2].currentSeconds) //hundred
        recordProgression.print(sb)
        sb.append("<br><br>")
    }

    private fun cumulateAndPercent(roundedSeconds : Map<Int, Day.Cumulative>) {
        var sum = 0

        roundedSeconds.forEach {
            sum += it.value.total
            it.value.addedTotal = sum
        }
    }
}