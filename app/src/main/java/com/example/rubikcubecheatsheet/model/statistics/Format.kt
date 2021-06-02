package com.example.rubikcubecheatsheet.model.statistics

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object Format {
    public fun String.toDate() : LocalDateTime {
        return LocalDateTime.parse(this, DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"))
    }

    public fun LocalDateTime.dateOnly(): LocalDateTime {
        return LocalDateTime.of(this.year, this.month, this.dayOfMonth, 0, 0, 0)
    }

    public fun LocalDateTime.toStr() : String {
        return this.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
    }

    public fun Int.leadingZero() : String {
        var temp = "0${this}"

        return temp.substring(temp.length - 2)
    }

    public fun Float.round(digits : Int = 2) : String {
        return if (this == Float.MAX_VALUE)
            "-"
        else if (digits == 1)
            String.format("%.1f", this)
        else
            String.format("%.2f", this)
    }
}