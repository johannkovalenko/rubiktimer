package com.example.rubikcubecheatsheet.view

import androidx.appcompat.app.AppCompatActivity
import com.example.rubikcubecheatsheet.view.labels.Labels

class Controls(mainForm : AppCompatActivity){
    public val confirmButtons = ConfirmButtons(mainForm)
    public val hintImages = HintImages(mainForm)
    public val mainBoard = MainBoard(mainForm)
    public val webViews = WebViews(mainForm)
    public val labels = Labels(mainForm)
}