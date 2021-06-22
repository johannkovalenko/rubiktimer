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
import com.example.rubikcubecheatsheet.controller.Generator
import com.example.rubikcubecheatsheet.controller.Search
import com.example.rubikcubecheatsheet.controller.statistics.ShowHideStat
import com.example.rubikcubecheatsheet.model.data.DB
import com.example.rubikcubecheatsheet.model.data.Data
import com.example.rubikcubecheatsheet.view.confirmbuttons.ConfirmButtons
import com.example.rubikcubecheatsheet.view.dropdowns.DropDowns
import com.example.rubikcubecheatsheet.view.hints.HintImages
import com.example.rubikcubecheatsheet.view.hints.ShowHide
import com.example.rubikcubecheatsheet.view.labels.Labels
import java.time.LocalDateTime
import java.util.*

class MainActivity : AppCompatActivity() {

    private var generator: Generator? = null
    private var search: Search? = null
    private var confirmButtons: ConfirmButtons? = null
    private var dropDowns: DropDowns? = null

    private var controller : Controller? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        hideTitleBar()
        setContentView(R.layout.activity_main)

        val labels = Labels(this)
        labels.Create(findViewById(R.id.board))
        val data: List<DB> = ArrayList()
        val data_dict: Map<String, DB> = LinkedHashMap()
        val hintImages = HintImages(this)
        hintImages.FillImages()
        Data().Prepare(this.resources, data, data_dict)
        generator = Generator(data, labels, hintImages)
        search = Search(data_dict, labels, hintImages)
        this.controller = Controller(getExternalFilesDir(null))

        val temp : WebView = findViewById(R.id.shortStat)
        temp.loadDataWithBaseURL(null, controller!!.getShortStatistics().toString(), "text/html", "utf-8", null)


        this.confirmButtons = ConfirmButtons(this, controller!!)
        this.dropDowns = DropDowns(this, controller!!, data_dict, search!!)
    }

    fun startRecord(view: View) {
        val goOn : Boolean = controller!!.startRecord()

        if (goOn){
            val start = findViewById<Button>(R.id.startRecord)
            start.visibility = View.INVISIBLE
            confirmButtons!!.hide("Running")
            window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
            findViewById<ConstraintLayout>(R.id.mainLayout).setBackgroundColor(Color.RED)
        }

    }

    fun stopRecord(view: View) {
        val start = findViewById<Button>(R.id.startRecord)
        if (controller!!.timeMode != TimeMode.ON) {
            ShowHide.run(this)
            return
        }
        controller!!.timeMode = TimeMode.CONFIRM
        start.visibility = View.VISIBLE
        confirmButtons!!.show(controller!!.GetTimer().stop())
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
        val temp : WebView = findViewById(R.id.shortStat)
        temp.loadDataWithBaseURL(null, controller!!.getShortStatistics().toString(), "text/html", "utf-8", null)
    }

    fun changeCategory(view: View) {
        if (controller!!.timeMode != TimeMode.CONFIRM) return
        controller!!.timeMode = TimeMode.OFF
        controller!!.GetTimer().confirm(0f, false)
        confirmButtons!!.hide("ChangedCategory")
        val temp : WebView = findViewById(R.id.shortStat)
        temp.loadDataWithBaseURL(null, controller!!.getShortStatistics().toString(), "text/html", "utf-8", null)
    }

    fun addTwoSeconds(view: View) {
        if (controller!!.timeMode != TimeMode.CONFIRM) return
        controller!!.timeMode = TimeMode.OFF
        controller!!.GetTimer().confirm(2f, true)
        confirmButtons!!.hide("Added Two")
        val temp : WebView = findViewById(R.id.shortStat)
        temp.loadDataWithBaseURL(null, controller!!.getShortStatistics().toString(), "text/html", "utf-8", null)
    }

    fun confirm(view: View) {
        if (controller!!.timeMode != TimeMode.CONFIRM) return
        controller!!.timeMode = TimeMode.OFF
        controller!!.GetTimer().confirm(0f, true)
        confirmButtons!!.hide("Confirmed")
        val temp : WebView = findViewById(R.id.shortStat)
        temp.loadDataWithBaseURL(null, controller!!.getShortStatistics().toString(), "text/html", "utf-8", null)
    }

    fun showStat(view: View) {
        val generateButton = findViewById<Button>(R.id.generate)
        val start = findViewById<Button>(R.id.startRecord)
        ShowHideStat.Run(findViewById(R.id.textViewStatistics), controller!!.GetStatistics())

        generateButton.visibility = if (generateButton.visibility == View.INVISIBLE) View.VISIBLE else View.INVISIBLE
        start.visibility = if(start.visibility == View.INVISIBLE) View.VISIBLE else View.INVISIBLE
    }

    fun hideTitleBar() {
        super.requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        supportActionBar!!.hide()
    }

    fun eventGenerate(view: View) {
        findViewById<LinearLayout>(R.id.layouthints).visibility = View.INVISIBLE
        generator!!.Run()
    }
}