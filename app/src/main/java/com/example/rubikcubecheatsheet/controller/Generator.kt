package com.example.rubikcubecheatsheet.controller

import com.example.rubikcubecheatsheet.model.data.DB
import com.example.rubikcubecheatsheet.view.labels.Labels
import java.util.*

class Generator(val data: List<DB>, labels: Labels) {
    private val builder: Builder = Builder(data, labels)
    private val numberOfPositions: Int = builder.NumberOfPositions()

    public fun run() : List<String> {
        val index = Random().nextInt(data.size)
        val position = Random().nextInt(numberOfPositions)
        builder.Get(position).Run(index)
        return data[index].pictures
    }
}