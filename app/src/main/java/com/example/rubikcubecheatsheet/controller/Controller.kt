package com.example.rubikcubecheatsheet.controller

import com.example.rubikcubecheatsheet.TimeMode
import com.example.rubikcubecheatsheet.controller.statistics.ShortStatistics
import com.example.rubikcubecheatsheet.model.CubeMode
import com.example.rubikcubecheatsheet.model.enumerations.Mode
import com.example.rubikcubecheatsheet.model.statistics.Statistics
import com.example.rubikcubecheatsheet.model.timer.Timer
import java.io.File
import java.lang.StringBuilder
import java.time.LocalDateTime

class Controller (private val folder: File?){
    private val cubeMode = CubeMode(Mode.Speed)
    private val statistics: Statistics = Statistics(cubeMode, folder)
    private val shortStatistics: ShortStatistics
    private val timer : Timer

    public var timeMode = TimeMode.OFF

    init {
        shortStatistics = ShortStatistics(statistics)
        timer = Timer(folder, cubeMode, statistics)
    }

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

        if (lastEntry.isBefore(beforeTime))
            return "Training"
        else
            return "Reject"
    }

    public fun setCubeMode(strMode : String) {
        cubeMode.set(strMode)
    }

    public fun startRecord() : Boolean {
        if (timeMode == TimeMode.OFF) {
            GetTimer().start()
            timeMode = TimeMode.ON
            return true
        }
        else return false
    }
}