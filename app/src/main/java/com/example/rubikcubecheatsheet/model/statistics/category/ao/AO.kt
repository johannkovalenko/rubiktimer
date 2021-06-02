package com.example.rubikcubecheatsheet.model.statistics.category.ao

import com.example.rubikcubecheatsheet.model.Entry
import com.example.rubikcubecheatsheet.model.statistics.category.Data
import com.example.rubikcubecheatsheet.model.statistics.Format.dateOnly
import com.example.rubikcubecheatsheet.model.statistics.Format.round
import com.example.rubikcubecheatsheet.model.statistics.Format.toStr
import com.example.rubikcubecheatsheet.model.statistics.category.ao.day.Day
import java.time.LocalDateTime

abstract class AO (protected val data : Data)
{
    public abstract fun add(entry: Entry)
    public abstract fun colorScheme(dnf : Int) : String

    private val top : Day = data.topPerDay["top"]!!
    private val current : Day = data.topPerDay["current"]!!

    public val percentiles = intArrayOf(
            5, 6, 7, 8, 9, 10, 11, 12,
            15, 17, 20, 23, 28,
            35, 45,
            60, 75, 90, 105, 120, 130, 140, 150,
            200, 300, 400, 500, 600, 700, 800, 900, 1000, 1100, 1200,
            1500, 1800, 2100, 2400, 2700)


    public fun printShort(sb: StringBuilder) {
        sb.append("<tr>")
        sb.append("<td>${data.numberOfElements}:</td>")

        val template = "class='withborder' style='background-color:"

        if (data.currentPosition >= data.numberOfElements) {

            if (top.avg == Float.MAX_VALUE)
                sb.append("<td></td>")
            else {
                sb.append("<td ${template}${colorScheme(top.dnf)}'>")
                sb.append(top.avg.round())
                if (LocalDateTime.now().dateOnly() == top.date)
                    sb.append(":")
                sb.append("</td>")
            }

            sb.append("<td ${template}${colorScheme(current.dnf)}'>")
            sb.append(current.avg.round())
            sb.append("</td>")

            sb.append("<td>Next</td>")
            for (offset: Int in 0..2) {
                val nextToBeat = data.entries[data.currentPosition - data.numberOfElements + offset].seconds
                sb.append("<td>${nextToBeat.round()}</td>")
            }
        }

        sb.append("<tr>")
    }

    protected fun arriving(entry: Entry) {
        if (!entry.dnf && entry.seconds < data.allTimeTop)
            data.allTimeTop = entry.seconds

        data.currentSeconds.add(entry.seconds)

        current.sum += entry.seconds
        current.date = entry.groupedDate

        if (entry.dnf) current.dnf++
    }

    protected fun isBelowThreshold(entry: Entry) : Boolean {

        if (data.currentPosition < data.numberOfElements) {
            if (!entry.dnf)
                top.sum = current.sum

            data.currentPosition++

            return true
        }
        else data.currentSeconds.removeAt(0)

        return false
    }

    protected fun leaving() {
        val leaving = data.entries[data.currentPosition - data.numberOfElements]

        if (leaving.dnf) current.dnf--

        current.sum -= leaving.seconds

        current.avg = current.sum / (data.numberOfElements - current.dnf)
    }

    protected fun topPerDay(entry : Entry) {
        val dataStr : String = entry.groupedDate.toStr()
        if (!data.topPerDay.containsKey(dataStr))
            data.topPerDay[dataStr] = Day()

        data.topPerDay[dataStr]!!.amount++

        if (entry.seconds != 0f)
            if (entry.seconds < data.topPerDay[dataStr]!!.top)
                data.topPerDay[dataStr]!!.top = entry.seconds

        data.currentPosition++
    }

    protected fun add(entry : Entry, percentiles : IntArray) {
        if (entry.dnf) return

        val percentile = get(entry.seconds, percentiles)

        if (current.percentiles.containsKey(percentile))
            current.percentiles[percentile]!!.total++
        else
            current.percentiles[percentile] = Day.Cumulative()
    }

    protected fun remove(data : Data, percentiles : IntArray) {
        val leaving = data.entries[data.currentPosition - data.numberOfElements]

        if (leaving.dnf) return

        val percentile = get(leaving.seconds, percentiles)

        current.percentiles[percentile]!!.total--
        if (current.percentiles[percentile]!!.total <= 0)
            current.percentiles.remove(percentile)
    }

    protected fun get(record : Float, percentiles : IntArray) : Int {
        val intValue : Int = record.toInt()

        for (percentile : Int in percentiles)
            if (intValue < percentile)
                return percentile

        return Integer.MAX_VALUE
    }

    protected fun setEver(entry : Entry, doDeepCopy : Boolean) {
        if (current.dnf > data.maxDNF) return

        if (current.avg < top.avg) {
            top.avg = current.avg
            top.sum = current.sum
            top.date = entry.groupedDate
            top.dnf = current.dnf
            if (doDeepCopy)
                top.percentiles = deepCopy(current.percentiles)
        }
    }

    protected fun setDay(entry : Entry, doDeepCopy : Boolean) {

        if (current.dnf > data.maxDNF) return

        val day = data.topPerDay[entry.groupedDate.toStr()] ?: error("")
        if (current.avg < day.avg) {
            day.avg = current.avg
            day.sum = current.sum
            day.dnf = current.dnf
            day.date = current.date

            if (doDeepCopy)
                day.percentiles = deepCopy(current.percentiles)
        }
    }

    private fun deepCopy(currentRounded : MutableMap<Int, Day.Cumulative>) : MutableMap<Int, Day.Cumulative> {
        val output = sortedMapOf<Int, Day.Cumulative>()

        for ((rounded, tempStat) in currentRounded){
            val tempTempStat = Day.Cumulative()
            tempTempStat.total = tempStat.total
            tempTempStat.addedTotal = tempStat.addedTotal

            output[rounded] = tempTempStat
        }
        return output
    }
}