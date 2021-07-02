package com.example.rubikcubecheatsheet.model.statistics

import com.example.rubikcubecheatsheet.model.Entry
import com.example.rubikcubecheatsheet.model.enumerations.Mode
import com.example.rubikcubecheatsheet.model.statistics.Format.toStr
import com.example.rubikcubecheatsheet.model.statistics.Format.round
import java.time.LocalDateTime

class Top100 {
    private val top100      = mutableListOf<Float>()
    private val top5History     = mutableMapOf<LocalDateTime, MutableList<Float>>()

    public fun add(entry : Entry) {
        if (entry.dnf || entry.mode == Mode.Training)
            return
        else if (top100.size >= 5 && entry.seconds < top100[4]) {
            top5History[entry.whenDate] = mutableListOf()

            top100.add(entry.seconds)
            top100.sort()

            for (i : Int in 0..4)
                top5History[entry.whenDate]?.add(top100[i])
        }
        else {
            top100.add(entry.seconds)
            top100.sort()
        }

        if (top100.size > 100)
            top100.removeAt(top100.size - 1)
    }

    public fun get100thBestEntry() : Float {
        return if (top100.size >= 100)
            top100[99]
        else
            0f
    }

    public fun print (sb: StringBuilder, currentSeconds : List<Float>) {
        sb.append("<br><h3>Top 100 ever</h3>")
        sb.append("<table>")

        var counter = 0
        for (result in top100) {
            counter++
            if (counter == 1)
                sb.append("<tr>")

            if (!currentSeconds.contains(result))
                sb.append("<td>${result.round()}</td>")
            else
                sb.append("<th>${result.round()}</th>")

            if (counter == 5) {
                sb.append("</tr>")
                counter = 0
            }
        }
        sb.append("</table>")
    }

    public fun printTop5History(sb : StringBuilder, top5History : Map<LocalDateTime, List<Float>>) {
        sb.append("<br><h3>Top 5 History</h3>")
        sb.append("<table>")

        for ((day, list) in top5History) {
            sb.append("<tr>")
            sb.append("<td>${day.toStr()}:</td>")

            for (record : Float in list)
                sb.append("<td>${record.round()}</td>")

            sb.append("</tr>")
        }
        sb.append("</table>")
    }
}