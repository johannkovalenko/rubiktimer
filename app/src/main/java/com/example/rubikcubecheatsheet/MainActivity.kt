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
        super.requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        supportActionBar!!.hide()

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

        findViewById<WebView>(R.id.shortStat).loadDataWithBaseURL(null, controller!!.getShortStatistics().toString(), "text/html", "utf-8", null)

        this.confirmButtons = ConfirmButtons(this)
        this.dropDowns = DropDowns(this, controller!!, data_dict, search!!)
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
            ShowHide.run(this)
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
        UpdateShortStat(text, "Added Two") }

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
        findViewById<LinearLayout>(R.id.layouthints).visibility = View.INVISIBLE
        generator!!.Run()
    }
}