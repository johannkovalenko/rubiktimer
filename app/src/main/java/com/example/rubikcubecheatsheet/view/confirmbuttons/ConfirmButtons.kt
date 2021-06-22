package com.example.rubikcubecheatsheet.view.confirmbuttons

import android.view.View
import android.widget.Button
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.example.rubikcubecheatsheet.R
import com.example.rubikcubecheatsheet.model.statistics.Statistics
import java.time.LocalDateTime

class ConfirmButtons (val mainForm : AppCompatActivity, val statistics: Statistics) {

    public fun show(message: String) {
        val start   = mainForm.findViewById<Button>(R.id.startRecord)
        val confirm = mainForm.findViewById<Button>(R.id.confirm)
        val reject  = mainForm.findViewById<Button>(R.id.reject)
        val change  = mainForm.findViewById<Button>(R.id.changecategory)
        val addTwo  = mainForm.findViewById<Button>(R.id.addtwoseconds)
        start.text = message

        val lastEntry = statistics!!.getDateTimeOfLastEntry()
        val beforeTime = LocalDateTime.now().minusMinutes(30)

        if (lastEntry.isBefore(beforeTime))
            reject.text = "Training"
        else
            reject.text = "Reject"

        confirm.visibility = View.VISIBLE
        reject.visibility = View.VISIBLE
        change.visibility = View.VISIBLE
        addTwo.visibility = View.VISIBLE
    }

    public fun hide(message: String) {
        val start   = mainForm.findViewById<Button>(R.id.startRecord)
        val confirm = mainForm.findViewById<Button>(R.id.confirm)
        val reject  = mainForm.findViewById<Button>(R.id.reject)
        val change  = mainForm.findViewById<Button>(R.id.changecategory)
        val addTwo  = mainForm.findViewById<Button>(R.id.addtwoseconds)
        start.text = message
        confirm.visibility = View.INVISIBLE
        reject.visibility = View.INVISIBLE
        change.visibility = View.INVISIBLE
        addTwo.visibility = View.INVISIBLE
    }
}