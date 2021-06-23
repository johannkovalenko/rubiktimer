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
import com.example.rubikcubecheatsheet.view.*
import com.example.rubikcubecheatsheet.view.labels.Labels
import java.io.File
import java.time.LocalDateTime
import java.util.ArrayList
import java.util.LinkedHashMap
import kotlin.text.StringBuilder

class Controller (
        private val resources : Resources,
        folder: File,
        private val controls : Controls
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
        generator = Generator(data, controls.labels)
        search = Search(data_dict, controls.labels)

        controls.webViews.updateShortStat(shortStatistics.write())
    }

    public fun getShortStatistics() : StringBuilder {
        return shortStatistics.write()
    }

    public fun trainingOrReject() : String {
        val lastEntry = statistics.getDateTimeOfLastEntry()
        val beforeTime = LocalDateTime.now().minusMinutes(30)

        return if (lastEntry.isBefore(beforeTime))
            "Training"
        else
            "Reject"
    }

    public fun setCubeMode(strMode : String) {
        cubeMode.set(strMode)
    }

    public fun startRecord() {
        if (timeMode == TimeMode.OFF) {
            timer.start()
            timeMode = TimeMode.ON
            controls.confirmButtons.hide("Running")
            controls.mainBoard.redBackground()
            controls.confirmButtons.hideStart()
        }
    }

    public fun stopRecord() {
        if (timeMode != TimeMode.ON) {
            controls.hintImages.showHide()
        }
        else {
            timeMode = TimeMode.CONFIRM
            controls.confirmButtons.show(timer.stop(), trainingOrReject())
            controls.mainBoard.standardBackground()
            controls.confirmButtons.showStart()
        }
    }

    public fun confirm(secondsToAdd : Float, keepMode : Boolean, message : String) {
        if (timeMode != TimeMode.CONFIRM)
            return

        timeMode = TimeMode.OFF
        timer.confirm(secondsToAdd, keepMode)
        controls.confirmButtons.hide(message)
        controls.webViews.updateShortStat(shortStatistics.write())
    }

    public fun reject() {
        if (timeMode != TimeMode.CONFIRM) return

        timeMode = TimeMode.OFF

        val lastEntry = statistics.getDateTimeOfLastEntry()
        val beforeTime = LocalDateTime.now().minusMinutes(30)

        if (lastEntry.isAfter(beforeTime))
            timer.reject()

        controls.confirmButtons!!.hide("Rejected")
        controls.webViews.updateShortStat(shortStatistics.write())
    }

    public fun generate() {
        controls.hintImages.FillPictureBox(generator.run())
        controls.hintImages.hide()
    }

    public fun search(item : String) {
        controls.hintImages.FillPictureBox(search.Run(item))
    }

    public fun getDataDict() : List<String> {
        return ArrayList(data_dict.keys)
    }

    public fun writeShortStat() {
        val sb = StringBuilder()
        sb.append("<html>")
        sb.append("<style>")
        sb.append("td {background-color:#f7f6df; font-size:16px}")
        sb.append(".withborder {border: solid 1pt black; background-color:#80ffbd}")
        sb.append(".red {border: solid 1pt black; background-color:red}")
        sb.append("</style>")
        sb.append("<body>")

        statistics.print(sb)

        sb.append("</body>")
        sb.append("</html>")

        controls.webViews.updateStat(sb)
    }
}