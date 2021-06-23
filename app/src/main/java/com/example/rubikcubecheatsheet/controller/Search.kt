package com.example.rubikcubecheatsheet.controller

import com.example.rubikcubecheatsheet.model.data.DB
import com.example.rubikcubecheatsheet.view.labels.Labels

class Search(private val data_dict: Map<String, DB>, private val labels: Labels) {
    fun Run(item: String) : List<String> {
        for (i in 0..6) for (j in 0..4) {
            val color = data_dict[item]!!.constellation[i][j]
            labels.FillLabels(color, i, j)
        }
        return data_dict[item]!!.pictures
    }
}