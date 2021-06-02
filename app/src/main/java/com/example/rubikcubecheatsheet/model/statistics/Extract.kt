package com.example.rubikcubecheatsheet.model.statistics

import com.example.rubikcubecheatsheet.model.Entry
import com.example.rubikcubecheatsheet.model.enumerations.Mode
import com.example.rubikcubecheatsheet.model.statistics.Format.dateOnly
import com.example.rubikcubecheatsheet.model.statistics.Format.toDate
import java.time.LocalDateTime
import java.time.Period

object Extract {
    private val beforeDate = LocalDateTime.now().minus(Period.ofMonths(2))

    public fun Entry (line : String) : Entry? {            val split = line.split('\t')

        if (split.size < 3) return null

        val mode        = getMode(split[2]) ?: return null
        val dnf         = split[1] == "DNF"
        val dateTime    = split[0].toDate()
        val record      = if (dnf) 0f else split[1].toFloat()
        val date        = dateTime.dateOnly()
        val grouped     = getGroupedDate(date)

        return Entry(dateTime, date, record, mode, dnf, grouped)
    }

    private fun getMode(rawMode : String) : Mode? {
        when (rawMode) {
            "Precision", "HalfFixed", "Calm", "NoCheating", "FullFixed" -> return Mode.Speed
            "MirrorColor", "Pastel", "BlackBorders", "WarmUp", "SlowCube", "UltraFixed" -> return null
        }

        return Mode.valueOf(rawMode)
    }

    private fun getGroupedDate(date : LocalDateTime) : LocalDateTime {
        return if (date.isBefore(beforeDate))
            LocalDateTime.of(date.year, date.month, 1, 0, 0, 0)
        else
            date
    }
}