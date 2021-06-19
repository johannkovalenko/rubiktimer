package com.example.rubikcubecheatsheet.model.statistics.category

import android.util.Log
import com.example.rubikcubecheatsheet.model.Entry
import com.example.rubikcubecheatsheet.model.enumerations.Mode
import com.example.rubikcubecheatsheet.model.statistics.category.ao.day.Day
import com.example.rubikcubecheatsheet.model.statistics.Top100
import com.example.rubikcubecheatsheet.model.statistics.category.ao.*
import java.time.LocalDateTime

class Category {
    private val entries         = mutableListOf<Entry>()
    private val mapDayCount     = mutableMapOf<LocalDateTime, Int>()

    private val aoData            = listOf(Data(5, 0, entries), Data(12, 0, entries), Data(100, 5, entries), Data(1000, 50, entries))

    private var aoList          = arrayOf(AO5(aoData[0]), AO12(aoData[1]), AO100(aoData[2]), AO1000(aoData[3]))

    private val printAO         = PrintAO(aoData, aoList)
    //private val recordProgression = RecordProgression(aoData)
    private val top100 = Top100()

    public fun add(entry: Entry) {
        entries.add(entry)

        if (!mapDayCount.containsKey(entry.whenDate))
            mapDayCount.put(entry.whenDate, 1)
        else
            mapDayCount[entry.whenDate] = mapDayCount[entry.whenDate]!! + 1

        for (ao : AO in aoList)
            ao.add(entry)

        if (entry.dnf || entry.mode == Mode.Training) return

        top100.check(entry)
        //recordProgression.check(entry)
    }

    public fun getDateTimeOfLastEntry() : LocalDateTime {
        return entries.get(entries.lastIndex).dateAndTime
    }

    public fun printShort(sb: StringBuilder) {
        sb.append("<table>")

        for (ao : AO in aoList)
            ao.printShort(sb)

        sb.append("</table>")
    }

    public fun print(sb: StringBuilder, header : String) {
        sb.append("<h1>${header} Day " + mapDayCount.size +"</h1>")
        sb.append("<table>")

        printAO.percentilesHeader(sb)

        cumulateAndPercent(aoData[2].topPerDay["current"]!!.percentiles)
        printAO.percentilesBody(sb, "current")

        val dateList : List<String> = aoData[2].topPerDay.keys.toList()
        for (index in dateList.size - 1 downTo 2) {
            cumulateAndPercent(aoData[2].topPerDay[dateList[index]]!!.percentiles)
            printAO.percentilesBody(sb, dateList[index])
        }

        sb.append("</table>")

        top100.print(sb, aoData[2].currentSeconds) //hundred
        //recordProgression.print(sb)
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