package com.example.rubikcubecheatsheet.model.statistics.category.ao

import com.example.rubikcubecheatsheet.model.statistics.Format.round
import com.example.rubikcubecheatsheet.model.statistics.category.ao.day.Day
import com.example.rubikcubecheatsheet.model.statistics.category.Data

class PrintAO (private val aoData : List<Data>, private val aoList : Array<AO>, private val whichAO : Int){
    private val template = "class='withborder' style='background-color:"

    public fun percentilesHeader(sb : StringBuilder){

        sb.append("<tr>")
        sb.append("<th>Date</th><th>Rec</th><th>ao5</th><th>ao12</th><th>ao100</th><th>1000</th>")

        val intAllTimeTop = aoData[whichAO].allTimeTop.toInt() // hundred
        var counter = 0

        for (result : Int in aoList[whichAO].percentiles) // hundred
            if (intAllTimeTop < result) {
                if (++counter > 7)
                    break
                sb.append("<th>$result</th>")
            }
        sb.append("</tr>")
    }

    public fun allCellsPerLine(sb : StringBuilder, date : String) {

        sb.append("<tr>")
        sb.append("<td>${date}</td>")

        topPerDay           (sb, date)
        bestAveragesPerDay  (sb, date)
        percentiles         (sb, date)
        amount              (sb, date)
        topEver             (sb, date)
        bestAveragesEver    (sb, date)

        sb.append("</tr>")
    }

    private fun bestAveragesPerDay(sb: StringBuilder, date: String) {

        for (i in aoList.indices) {
            if (aoData[i].days.containsKey(date)) {
                val day = aoData[i].days[date]!!
                sb.append("<td ${template}${aoList[i].colorScheme(day.dnf)}'>${day.avg.round()}</td>")
            }
            else
                sb.append("<td>-</td>")
        }
    }

    private fun bestAveragesEver(sb : StringBuilder, date: String) {

        for (i in aoList.indices) {
            if (aoData[i].days.containsKey(date)) {
                val day = aoData[i].days[date]!!
                sb.append("<td>${day.topAvgEver.round()}</td>")
            }
            else
                sb.append("<td>-</td>")
        }
    }

    private fun percentiles(sb : StringBuilder, date : String) {

        if (!aoData[whichAO].days.containsKey(date))
            sb.append("<td>-</td><td>-</td><td>-</td><td>-</td><td>-</td><td>-</td><td>-</td>")
        else {
            val intAllTimeTop = aoData[whichAO].allTimeTop.toInt() // hundred

            var counter = 0

            for (result : Int in aoList[whichAO].percentiles) // hundred
                if (intAllTimeTop < result) {
                    if (++counter > 7)
                        break

                    if (!aoData[whichAO].days[date]!!.percentiles.containsKey(result))
                        sb.append("<td></td>")
                    else
                        sb.append("<td class='withborder''>${aoData[whichAO].days[date]!!.percentiles[result]!!.addedTotal}</td>")
                }
        }
    }

    private fun topPerDay(sb : StringBuilder, date: String)
    {
        sb.append("<td ${template}rgb(0, 204, 0)'>${aoData[0].days[date]!!.top.round()}</td>")
    }

    private fun topEver(sb : StringBuilder, date: String) {
        sb.append("<td>${aoData[0].days[date]!!.topEntryEver.round()}</td>")
    }

    private fun amount(sb : StringBuilder, date : String) {
        sb.append("<td>${aoData[0].days[date]!!.amount}</td>")
    }
}