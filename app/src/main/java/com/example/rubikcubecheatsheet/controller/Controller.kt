package com.example.rubikcubecheatsheet.controller

import com.example.rubikcubecheatsheet.TimeMode
import com.example.rubikcubecheatsheet.controller.statistics.ShortStatistics
import com.example.rubikcubecheatsheet.model.CubeMode
import com.example.rubikcubecheatsheet.model.enumerations.Mode
import com.example.rubikcubecheatsheet.model.statistics.Statistics
import com.example.rubikcubecheatsheet.model.timer.Timer
import java.io.File
import java.time.LocalDateTime
import kotlin.text.StringBuilder

class Controller (private val folder: File?){
    private val cubeMode = CubeMode(Mode.Speed)
    private val statistics: Statistics = Statistics(cubeMode, folder)
    private val shortStatistics = ShortStatistics(statistics)
    private val timer = Timer(folder, cubeMode, statistics)

    public var timeMode = TimeMode.OFF

    //interim
    public fun GetStatistics() : Statistics { return statistics }
    // interim
    public fun GetTimer() : Timer { return timer }

    public fun getShortStatistics() : StringBuilder {
        return shortStatistics.write()
    }

    public fun trainingOrReject() : String {
        val lastEntry = statistics!!.getDateTimeOfLastEntry()
        val beforeTime = LocalDateTime.now().minusMinutes(30)

        return if (lastEntry.isBefore(beforeTime))
            "Training"
        else
            "Reject"
    }

    public fun setCubeMode(strMode : String) {
        cubeMode.set(strMode)
    }

    public fun startRecord() : Boolean {
        if (timeMode == TimeMode.OFF) {
            timer.start()
            timeMode = TimeMode.ON
            return true
        }
        else return false
    }

    public fun confirm(secondsToAdd : Float, keepMode : Boolean) : String {
        if (timeMode != TimeMode.CONFIRM)
            return "SKIP"
        else {
            timeMode = TimeMode.OFF
            timer.confirm(secondsToAdd, keepMode)
            return getShortStatistics().toString()
        }
    }
}