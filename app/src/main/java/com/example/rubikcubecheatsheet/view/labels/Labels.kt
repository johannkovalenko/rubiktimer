package com.example.rubikcubecheatsheet.view.labels

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.View
import android.widget.TableLayout
import android.widget.TextView
import com.example.rubikcubecheatsheet.MainActivity
import com.example.rubikcubecheatsheet.R

class Labels(var mainForm: MainActivity) {
    private val labels = Array(7) { arrayOfNulls<TextView>(5) }
    private val createLabel: CreateLabel = CreateLabel(mainForm)
    private val row = Row(mainForm, createLabel, labels)

    init {
        val types = CreateLabelTypes().Run()
        for (i in types.indices)
            mainForm.findViewById<TableLayout>(R.id.board).addView(row.Create(i, types[i]))
    }

    fun FillLabels(color: Int, i: Int, j: Int) {

        if (color == Color.GRAY || color == 0)
            labels[i][j]!!.visibility = View.INVISIBLE
        else
            labels[i][j]!!.visibility = View.VISIBLE

        (labels[i][j]!!.background as GradientDrawable).setColor(color)
    }
}