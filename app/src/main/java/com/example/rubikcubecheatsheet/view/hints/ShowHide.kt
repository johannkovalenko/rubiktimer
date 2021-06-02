package com.example.rubikcubecheatsheet.view.hints

import android.view.View
import android.widget.LinearLayout
import com.example.rubikcubecheatsheet.MainActivity
import com.example.rubikcubecheatsheet.R

object ShowHide {
    fun run(myForm: MainActivity) {
        val layoutHints = myForm.findViewById<LinearLayout>(R.id.layouthints)
        when (layoutHints.visibility) {
            View.VISIBLE -> layoutHints.visibility = View.INVISIBLE
            View.INVISIBLE, View.GONE -> layoutHints.visibility = View.VISIBLE
        }
    }
}