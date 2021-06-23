package com.example.rubikcubecheatsheet

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.webkit.WebView
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.rubikcubecheatsheet.controller.Controller
import com.example.rubikcubecheatsheet.controller.statistics.ShowHideStat
import com.example.rubikcubecheatsheet.view.ConfirmButtons
import com.example.rubikcubecheatsheet.view.DropDowns
import com.example.rubikcubecheatsheet.view.HintImages
import com.example.rubikcubecheatsheet.view.labels.Labels
import java.time.LocalDateTime

class MainActivity : AppCompatActivity() {

    private var controller : Controller? = null
    private var confirmButtons: ConfirmButtons? = null
    private var dropDowns: DropDowns? = null
    private var hintImages : HintImages? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        super.requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        supportActionBar!!.hide()

        setContentView(R.layout.activity_main)

        val labels = Labels(this)

        hintImages = HintImages(this)
        hintImages!!.FillImages()

        this.controller = Controller(this.resources, getExternalFilesDir(null)!!, labels, hintImages!!)

        findViewById<WebView>(R.id.shortStat).loadDataWithBaseURL(null, controller!!.getShortStatistics().toString(), "text/html", "utf-8", null)

        this.confirmButtons = ConfirmButtons(this)
        this.dropDowns = DropDowns(this, controller!!)
    }

    fun startRecord(view: View) {
        val goOn : Boolean = controller!!.startRecord()

        if (goOn){
            findViewById<Button>(R.id.startRecord).visibility = View.INVISIBLE
            confirmButtons!!.hide("Running")
            window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
            findViewById<ConstraintLayout>(R.id.mainLayout).setBackgroundColor(Color.RED)
        }
    }

    fun stopRecord(view: View) {
        if (controller!!.timeMode != TimeMode.ON) {
            hintImages!!.showHide()
            return
        }
        controller!!.timeMode = TimeMode.CONFIRM
        findViewById<Button>(R.id.startRecord).visibility = View.VISIBLE
        confirmButtons!!.show(controller!!.GetTimer().stop(), controller!!.trainingOrReject())
        window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        findViewById<ConstraintLayout>(R.id.mainLayout).setBackgroundResource(R.drawable.background2)
    }

    fun reject(view: View) {
        if (controller!!.timeMode != TimeMode.CONFIRM) return
        controller!!.timeMode = TimeMode.OFF

        val lastEntry = controller!!.GetStatistics().getDateTimeOfLastEntry()
        val beforeTime = LocalDateTime.now().minusMinutes(30)

        if (lastEntry.isAfter(beforeTime))
            controller!!.GetTimer().reject()

        confirmButtons!!.hide("Rejected")
        findViewById<WebView>(R.id.shortStat).loadDataWithBaseURL(null, controller!!.getShortStatistics().toString(), "text/html", "utf-8", null)
    }

    fun changeCategory(view: View) {
        val text = controller!!.confirm(0f, false)
        UpdateShortStat(text, "ChangedCategory")
    }

    fun addTwoSeconds(view: View) {
        val text = controller!!.confirm(2f, true)
        UpdateShortStat(text, "Added Two")
    }

    fun confirm(view: View) {
        val text = controller!!.confirm(0f, true)
        UpdateShortStat(text, "Confirmed")
    }

    private fun UpdateShortStat(text : String, message : String) {
        if (text == "SKIP") return

        confirmButtons!!.hide(message)
        findViewById<WebView>(R.id.shortStat).loadDataWithBaseURL(null, text, "text/html", "utf-8", null)
    }

    fun showStat(view: View) {
        ShowHideStat.Run(findViewById(R.id.textViewStatistics), controller!!.GetStatistics())

        findViewById<Button>(R.id.generate).visibility = if (findViewById<Button>(R.id.generate).visibility == View.INVISIBLE) View.VISIBLE else View.INVISIBLE
        findViewById<Button>(R.id.startRecord).visibility = if(findViewById<Button>(R.id.startRecord).visibility == View.INVISIBLE) View.VISIBLE else View.INVISIBLE
    }

    fun eventGenerate(view: View) {
        controller!!.generate()
        findViewById<LinearLayout>(R.id.layouthints).visibility = View.INVISIBLE
    }
}