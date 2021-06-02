package com.example.rubikcubecheatsheet.model.statistics.category.ao

import com.example.rubikcubecheatsheet.model.statistics.Format.leadingZero
import com.example.rubikcubecheatsheet.model.statistics.Format.round
import com.example.rubikcubecheatsheet.model.statistics.category.ao.day.Day
import com.example.rubikcubecheatsheet.model.statistics.category.Data

class PrintAO (private val aoData : List<Data>, private val aoList : Array<AO>){

    public fun percentilesHeader(sb : StringBuilder){

        sb.append("<tr>")
        sb.append("<th>Date</th><th>Rec</th><th>ao5</th><th>ao12</th><th>ao100</th>")

        val intAllTimeTop = aoData[2].allTimeTop.toInt() // hundred
        var counter = 0

        for (result : Int in aoList[2].percentiles) // hundred
            if (intAllTimeTop < result) {
                if (++counter > 7)
                    break
                sb.append("<th>$result</th>")
            }
        sb.append("</tr>")
    }

    public fun percentilesBody(sb : StringBuilder, date : String) {

        val template = "class='withborder' style='background-color:"

        val day100 : Day = aoData[2].topPerDay[date]!! // hundred

        sb.append("<tr>")
        sb.append("<td>${date}</td>")
        sb.append("<td ${template}rgb(0, 204, 0)'>${day100.top.round()}</td>")

        for (i in aoList.indices) {
            val day = aoData[i].topPerDay[date]!!
            sb.append("<td ${template}${aoList[i].colorScheme(day.dnf)}'>${day.avg.round()}</td>")
        }

        val intAllTimeTop = aoData[2].allTimeTop.toInt() // hundred

        var counter = 0

        for (result : Int in aoList[2].percentiles) // hundred
            if (intAllTimeTop < result) {
                if (++counter > 7)
                    break

                if (!day100.percentiles.containsKey(result))
                    sb.append("<td></td>")
                else
                    sb.append("<td class='withborder''>${day100.percentiles[result]!!.addedTotal.leadingZero()}</td>")
            }

        sb.append("<td>${day100.amount}</td>")
        sb.append("</tr>")
    }
}