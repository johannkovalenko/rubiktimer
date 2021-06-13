package com.example.rubikcubecheatsheet.model.statistics

import com.example.rubikcubecheatsheet.controller.file.RawFile
import com.example.rubikcubecheatsheet.model.CubeMode
import com.example.rubikcubecheatsheet.model.Entry
import com.example.rubikcubecheatsheet.model.enumerations.Mode
import com.example.rubikcubecheatsheet.model.statistics.category.Category
import java.io.File
import java.time.LocalDateTime

class Statistics(private val cubeMode: CubeMode, folder: File?) {
    private val categories = mutableMapOf<Mode, Category>()

    init {
        for (singleMode in Mode.values())
            categories[singleMode] = Category()

        val rawLines = RawFile().Read2(folder, "results.csv")

        for (line in rawLines)
            add(Extract.Entry(line) ?: continue)
    }

    public fun add(entry : Entry) {
        categories[entry.mode]?.add(entry)
    }

    public fun getDateTimeOfLastEntry() : LocalDateTime {
        return categories[cubeMode.mode]!!.getDateTimeOfLastEntry()
    }

    public fun print(sb: StringBuilder) {
        categories[cubeMode.mode]?.print(sb, cubeMode.mode.toString())
    }

    public fun printShort(sb: StringBuilder) {
        categories[cubeMode.mode]!!.printShort(sb)
    }


}