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
import com.example.rubikcubecheatsheet.view.hints.HintImages
import com.example.rubikcubecheatsheet.view.hints.ShowHide
import com.example.rubikcubecheatsheet.view.labels.Labels
import java.util.*
import java.util.stream.Collectors

class MainActivity : AppCompatActivity() {
    enum class TimeMode {
        ON, OFF, CONFIRM
    }

    private var spinner: Spinner? = null
    private var spinnerMode: Spinner? = null
    private var timer: Timer? = null
    private var cubeMode = CubeMode(Mode.Speed)
    private var statistics: Statistics? = null
    private var timeMode = TimeMode.OFF
    private var shortStatistics: ShortStatistics? = null
    private var generator: Generator? = null
    private var search: Search? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideTitleBar()
        setContentView(R.layout.activity_main)
        this.spinner = findViewById(R.id.spinner)
        this.spinnerMode = findViewById(R.id.spinnerMode)

        //findViewById<WebView>(R.id.textViewStatistics).setMovementMethod(ScrollingMovementMethod())
        spinnerSetListener()
        spinnerModeSetListener()
        val labels = Labels(this)
        labels.Create(findViewById(R.id.board))
        val data: List<DB> = ArrayList()
        val data_dict: Map<String, DB> = LinkedHashMap()
        val hintImages = HintImages(this)
        hintImages.FillImages()
        Data().Prepare(this.resources, data, data_dict)
        generator = Generator(data, labels, hintImages)
        search = Search(data_dict, labels, hintImages)
        fillDropdown(ArrayList(data_dict.keys), spinner)
        fillDropdown(EnumSet.allOf(Mode::class.java).stream().map { obj: Mode -> obj.name }.collect(Collectors.toList()), spinnerMode)
        statistics = Statistics(cubeMode, getExternalFilesDir(null))
        shortStatistics = ShortStatistics(findViewById(R.id.shortStat), statistics)
        shortStatistics!!.write()
        timer = Timer(getExternalFilesDir(null), cubeMode, statistics!!)
    }

    fun startRecord(view: View) {
        val start = findViewById<Button>(R.id.startRecord)

        if (timeMode != TimeMode.OFF) return
        timer!!.start()
        timeMode = TimeMode.ON
        start.visibility = View.INVISIBLE
        hideConfirmButton("Running")
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
        showConfirmButton(timer!!.stop())
        window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        findViewById<ConstraintLayout>(R.id.mainLayout).setBackgroundResource(R.drawable.background2)
    }

    fun reject(view: View) {
        if (timeMode != TimeMode.CONFIRM) return
        timeMode = TimeMode.OFF
        timer!!.reject()
        hideConfirmButton("Rejected")
        shortStatistics!!.write()
    }

    fun changeCategory(view: View) {
        if (timeMode != TimeMode.CONFIRM) return
        timeMode = TimeMode.OFF
        timer!!.confirm(0f, false)
        hideConfirmButton("ChangedCategory")
        shortStatistics!!.write()
    }

    fun addTwoSeconds(view: View) {
        if (timeMode != TimeMode.CONFIRM) return
        timeMode = TimeMode.OFF
        timer!!.confirm(2f, true)
        hideConfirmButton("Added Two")
        shortStatistics!!.write()
    }

    fun confirm(view: View) {
        if (timeMode != TimeMode.CONFIRM) return
        timeMode = TimeMode.OFF
        timer!!.confirm(0f, true)
        hideConfirmButton("Confirmed")
        shortStatistics!!.write()
    }

    fun showStat(view: View) {
        val generateButton = findViewById<Button>(R.id.generate)
        val start = findViewById<Button>(R.id.startRecord)
        ShowHideStat.Run(findViewById(R.id.textViewStatistics), statistics, cubeMode)

        generateButton.visibility = if (generateButton.visibility == View.INVISIBLE) View.VISIBLE else View.INVISIBLE
        start.visibility = if(start.visibility == View.INVISIBLE) View.VISIBLE else View.INVISIBLE
    }

    private fun showConfirmButton(message: String) {
        val start = findViewById<Button>(R.id.startRecord)
        val confirm = findViewById<Button>(R.id.confirm)
        val reject = findViewById<Button>(R.id.reject)
        val change = findViewById<Button>(R.id.changecategory)
        val addTwo = findViewById<Button>(R.id.addtwoseconds)
        start.text = message
        confirm.visibility = View.VISIBLE
        reject.visibility = View.VISIBLE
        change.visibility = View.VISIBLE
        addTwo.visibility = View.VISIBLE
    }

    private fun hideConfirmButton(message: String) {
        val start = findViewById<Button>(R.id.startRecord)
        val confirm = findViewById<Button>(R.id.confirm)
        val reject = findViewById<Button>(R.id.reject)
        val change = findViewById<Button>(R.id.changecategory)
        val addTwo = findViewById<Button>(R.id.addtwoseconds)
        start.text = message
        confirm.visibility = View.INVISIBLE
        reject.visibility = View.INVISIBLE
        change.visibility = View.INVISIBLE
        addTwo.visibility = View.INVISIBLE
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

    fun fillDropdown(keys: List<String>, spinner: Spinner?) {
        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, keys)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner!!.adapter = arrayAdapter
    }

    fun spinnerSetListener() {
        spinner!!.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View, i: Int, l: Long) {
                findViewById<LinearLayout>(R.id.layouthints).visibility = View.VISIBLE
                val item = spinner!!.selectedItem as String
                search!!.Run(item)
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {}
        }
    }

    fun spinnerModeSetListener() {
        spinnerMode!!.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View, i: Int, l: Long) {
                val item = spinnerMode!!.selectedItem as String
                cubeMode.set(item)
                shortStatistics!!.write()
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {}
        }
    }
}