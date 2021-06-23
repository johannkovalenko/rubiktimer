package com.example.rubikcubecheatsheet.view

import android.graphics.Color
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.rubikcubecheatsheet.R

class MainBoard (val mainForm : AppCompatActivity){

    public fun redBackground() {
        mainForm.window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        mainForm.findViewById<ConstraintLayout>(R.id.mainLayout).setBackgroundColor(Color.RED)
    }

    public fun standardBackground() {
        mainForm.window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        mainForm.findViewById<ConstraintLayout>(R.id.mainLayout).setBackgroundResource(R.drawable.background2)
    }
}