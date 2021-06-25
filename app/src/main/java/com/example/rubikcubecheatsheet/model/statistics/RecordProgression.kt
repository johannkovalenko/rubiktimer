package com.example.rubikcubecheatsheet.model.statistics

import com.example.rubikcubecheatsheet.model.Entry
import com.example.rubikcubecheatsheet.model.statistics.Format.toStr
import com.example.rubikcubecheatsheet.model.statistics.Format.round
import com.example.rubikcubecheatsheet.model.statistics.category.Data
import java.time.LocalDateTime

class RecordProgression(private val aoData : List<Data>){
    private data class RecordTypes (var single : Float, val aoRecords : List<Float>) {
        val difference = aoRecords[2] - single // hundred
        val differenceInPercent = aoRecords[2] / single * 100 - 100 // hundred
    }

    private var currentSingle = Float.MAX_VALUE
    private val currentRecords : MutableList<Float> = mutableListOf()

    init {
        for(i in aoData.indices)
            currentRecords.add(Float.MAX_VALUE)
    }
    private val recordEvolution = mutableMapOf<LocalDateTime, RecordTypes>()

    public fun check(entry : Entry) {
        var counter = 0

        if (aoData[2].allTimeTop < currentSingle) { // hundred
            currentSingle = aoData[2].allTimeTop
            counter++
        }

        for (i in aoData.indices)
            if (aoData[i].days["top"]!!.avg < currentRecords[i]) {
                currentRecords[i] = aoData[i].days["top"]!!.avg
                counter++
            }

        if (counter > 0)
            recordEvolution[entry.whenDate] = RecordTypes(currentSingle, currentRecords.toList())
    }

    public fun print (sb : StringBuilder) {
        sb.append("<br><h3>Record Evolution</h3>")
        sb.append("<table>")

        sb.append("<tr><th>Date</th><th>Single</th><th>ao5</th><th>ao12</th><th>ao100</th><th></th></tr>")
        for ((day, recordTypes) in recordEvolution) {
            sb.append("<tr>")
            sb.append("<td>${day.toStr()}:</td>")
            sb.append("<td class='withborder'>${recordTypes.single.round()}</td>")

            for (aoRecord : Float in recordTypes.aoRecords)
                sb.append("<td class='withborder'>${aoRecord.round()}</td>")

            sb.append("<td>${recordTypes.difference.round(1)}</td>")
            sb.append("<td>${recordTypes.differenceInPercent.round(1)}%</td>")

            sb.append("</tr>")
        }
        sb.append("</table>")
    }
}