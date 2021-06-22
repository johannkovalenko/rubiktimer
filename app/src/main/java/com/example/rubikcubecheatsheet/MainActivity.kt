package com.example.rubikcubecheatsheet

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.rubikcubecheatsheet.controller.Generator
import com.example.rubikcubecheatsheet.controller.Search
import com.example.rubikcubecheatsheet.controller.statistics.ShortStatistics
import com.example.rubikcubecheatsheet.controller.statistics.ShowHideStat
import com.example.rubikcubecheatsheet.model.CubeMode
import com.example.rubikcubecheatsheet.model.data.DB
import com.example.rubikcubecheatsheet.model.data.Data
import com.example.rubikcubecheatsheet.model.enumerations.Mode
import com.example.rubikcubecheatsheet.model.statistics.Statistics
import com.example.rubikcubecheatsheet.model.timer.Timer
import com.example.rubikcubecheatsheet.view.confirmbuttons.ConfirmButtons
import com.example.rubikcubecheatsheet.view.dropdowns.DropDowns
import com.example.rubikcubecheatsheet.view.hints.HintImages
import com.example.rubikcubecheatsheet.view.hints.ShowHide
import com.example.rubikcubecheatsheet.view.labels.Labels
import java.time.LocalDateTime
import java.util.*
import java.util.stream.Collectors

class MainActivity : AppCompatActivity() {
    enum class TimeMode {
        ON, OFF, CONFIRM
    }

    private var timer: Timer? = null
    private val cubeMode = CubeMode(Mode.Speed)
    private var statistics: Statistics? = null
    private var timeMode = TimeMode.OFF
    private var shortStatistics: ShortStatistics? = null
    private var generator: Generator? = null
    private var search: Search? = null
    private var confirmButtons: ConfirmButtons? = null
    private var dropDowns: DropDowns? = null

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

        statistics = Statistics(cubeMode, getExternalFilesDir(null))
        shortStatistics = ShortStatistics(findViewById(R.id.shortStat), statistics)
        shortStatistics!!.write()
        timer = Timer(getExternalFilesDir(null), cubeMode, statistics!!)

        this.confirmButtons = ConfirmButtons(this, statistics!!)
        this.dropDowns = DropDowns(this, data_dict, search!!, cubeMode, shortStatistics!!)
    }

    fun startRecord(view: View) {
        val start = findViewById<Button>(R.id.startRecord)

        if (timeMode != TimeMode.OFF) return
        timer!!.start()
        timeMode = TimeMode.ON
        start.visibility = View.INVISIBLE
        confirmButtons!!.hide("Running")
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        findViewById<ConstraintLayout>(R.id.mainLayout).setBackgroundColor(Color.RED)
    }

    fun stopRecord(view: View) {
        val start = findViewById<Button>(R.id.startRecord)
        if (timeMode != TimeMode.ON) {
            ShowHide.run(this)
            return
        }
        timeMode = TimeMode.CONFIRM
        start.visibility = View.VISIBLE
        confirmButtons!!.show(timer!!.stop())
        window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        findViewById<ConstraintLayout>(R.id.mainLayout).setBackgroundResource(R.drawable.background2)
    }

    fun reject(view: View) {
        if (timeMode != TimeMode.CONFIRM) return
        timeMode = TimeMode.OFF

        val lastEntry = statistics!!.getDateTimeOfLastEntry()
        val beforeTime = LocalDateTime.now().minusMinutes(30)

        if (lastEntry.isAfter(beforeTime))
            timer!!.reject()

        confirmButtons!!.hide("Rejected")
        shortStatistics!!.write()
    }

    fun changeCategory(view: View) {
        if (timeMode != TimeMode.CONFIRM) return
        timeMode = TimeMode.OFF
        timer!!.confirm(0f, false)
        confirmButtons!!.hide("ChangedCategory")
        shortStatistics!!.write()
    }

    fun addTwoSeconds(view: View) {
        if (timeMode != TimeMode.CONFIRM) return
        timeMode = TimeMode.OFF
        timer!!.confirm(2f, true)
        confirmButtons!!.hide("Added Two")
        shortStatistics!!.write()
    }

    fun confirm(view: View) {
        if (timeMode != TimeMode.CONFIRM) return
        timeMode = TimeMode.OFF
        timer!!.confirm(0f, true)
        confirmButtons!!.hide("Confirmed")
        shortStatistics!!.write()
    }

    fun showStat(view: View) {
        val generateButton = findViewById<Button>(R.id.generate)
        val start = findViewById<Button>(R.id.startRecord)
        ShowHideStat.Run(findViewById(R.id.textViewStatistics), statistics, cubeMode)

        generateButton.visibility = if (generateButton.visibility == View.INVISIBLE) View.VISIBLE else View.INVISIBLE
        start.visibility = if(start.visibility == View.INVISIBLE) View.VISIBLE else View.INVISIBLE
    }

    fun hideTitleBar() {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        supportActionBar!!.hide()
    }

    fun eventGenerate(view: View) {
        findViewById<LinearLayout>(R.id.layouthints).visibility = View.INVISIBLE
        generator!!.Run()
    }
}