package com.example.rubikcubecheatsheet.controller

import android.content.res.Resources
import com.example.rubikcubecheatsheet.TimeMode
import com.example.rubikcubecheatsheet.controller.statistics.ShortStatistics
import com.example.rubikcubecheatsheet.model.CubeMode
import com.example.rubikcubecheatsheet.model.data.DB
import com.example.rubikcubecheatsheet.model.data.Data
import com.example.rubikcubecheatsheet.model.enumerations.Mode
import com.example.rubikcubecheatsheet.model.statistics.Statistics
import com.example.rubikcubecheatsheet.model.timer.Timer
import com.example.rubikcubecheatsheet.view.HintImages
import com.example.rubikcubecheatsheet.view.labels.Labels
import java.io.File
import java.time.LocalDateTime
import java.util.ArrayList
import java.util.LinkedHashMap
import kotlin.text.StringBuilder

class Controller (
        private val resources : Resources,
        folder: File,
        labels : Labels,
        private val hintImages: HintImages
        ) {

    private val data: List<DB> = ArrayList()
    private val data_dict: Map<String, DB> = LinkedHashMap()
    private val cubeMode = CubeMode(Mode.Speed)
    private val statistics: Statistics = Statistics(cubeMode, folder)
    private val shortStatistics = ShortStatistics(statistics)
    private val timer = Timer(folder, cubeMode, statistics)
    private val generator: Generator
    private val search: Search

    public var timeMode = TimeMode.OFF

    init {
        Data().Prepare(this.resources, data, data_dict)
        generator = Generator(data, labels)
        search = Search(data_dict, labels)
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

    public fun generate() {
        val list : List<String> = generator!!.run()
        hintImages!!.FillPictureBox(list)
    }

    public fun search(item : String) {
        val list : List<String> = search.Run(item)
        hintImages.FillPictureBox(list)
    }

    public fun getDataDict() : List<String> {
        return ArrayList(data_dict.keys)
    }
}