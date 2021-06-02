package com.example.rubikcubecheatsheet.model.timer

import com.example.rubikcubecheatsheet.controller.file.Append
import com.example.rubikcubecheatsheet.model.CubeMode
import com.example.rubikcubecheatsheet.model.Entry
import com.example.rubikcubecheatsheet.model.enumerations.Mode
import com.example.rubikcubecheatsheet.model.statistics.Statistics
import com.example.rubikcubecheatsheet.model.statistics.Format.dateOnly
import com.example.rubikcubecheatsheet.model.statistics.Format.round
import java.io.File
import java.time.Duration
import java.time.Instant
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Timer (private val folder: File?, private val cubeMode: CubeMode, private val statistics: Statistics) {
    private var instantStart    : Instant = Instant.MIN
    private var instantStop     : Instant = Instant.MIN
    private var secondsElapsed  : Float   = Float.MIN_VALUE

    private val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")

    fun start() {
        instantStart = Instant.now()
    }

    fun stop() : String {
        instantStop = Instant.now()
        secondsElapsed = Duration.between(instantStart, instantStop).toMillis().toFloat() / 1000
        return secondsElapsed.round().replace(",", ".")
    }

    fun confirm(secondsToAdd : Float, keepMode : Boolean) {
        secondsElapsed += secondsToAdd

        val mode : Mode = if (keepMode) cubeMode.mode else Mode.Training
        val timeNow = LocalDateTime.now().format(formatter)
        val record = secondsElapsed.round().replace(",", ".")

        val date = LocalDateTime.now().dateOnly()
        val entry = Entry(
                LocalDateTime.now(),
                date,
                secondsElapsed,
                //secondsElapsed.toInt() / 5 * 5,
                mode,
                false,
                date
        )
        statistics.add(entry)

        Append().Run(folder, "results.csv", timeNow + "\t" + record + "\t" + mode.toString())
    }

    fun reject() {
        val timeNow = LocalDateTime.now().format(formatter)
        val date = LocalDateTime.now().dateOnly()

        val entry = Entry(
                LocalDateTime.now(),
                date,
                0f,
                cubeMode.mode,
                true,
                date
        )
        statistics.add(entry)

        Append().Run(folder, "results.csv", timeNow + "\tDNF\t" + cubeMode.mode.toString())
    }
}