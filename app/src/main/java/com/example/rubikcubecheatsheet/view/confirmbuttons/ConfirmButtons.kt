package com.example.rubikcubecheatsheet.view.confirmbuttons

import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.rubikcubecheatsheet.R
import com.example.rubikcubecheatsheet.controller.Controller

class ConfirmButtons (mainForm : AppCompatActivity) {
    private val start   = mainForm.findViewById<Button>(R.id.startRecord)
    private val confirm = mainForm.findViewById<Button>(R.id.confirm)
    private val reject  = mainForm.findViewById<Button>(R.id.reject)
    private val change  = mainForm.findViewById<Button>(R.id.changecategory)
    private val addTwo  = mainForm.findViewById<Button>(R.id.addtwoseconds)

    public fun show(message: String, rejectButtonText : String) {
        start.text = message

        reject.text = rejectButtonText
        confirm.visibility = View.VISIBLE
        reject.visibility = View.VISIBLE
        change.visibility = View.VISIBLE
        addTwo.visibility = View.VISIBLE
    }

    public fun hide(message: String) {
        start.text = message
        confirm.visibility = View.INVISIBLE
        reject.visibility = View.INVISIBLE
        change.visibility = View.INVISIBLE
        addTwo.visibility = View.INVISIBLE
    }
}