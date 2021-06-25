package com.example.rubikcubecheatsheet

import android.os.Bundle
import android.view.*
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.rubikcubecheatsheet.controller.Controller
import com.example.rubikcubecheatsheet.view.Controls
import com.example.rubikcubecheatsheet.view.DropDowns

class MainActivity : AppCompatActivity() {

    private var controller : Controller? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        HideTitleBar()
        setContentView(R.layout.activity_main)

        val controls = Controls(this)
        controller = Controller(this.resources, getExternalFilesDir(null)!!, controls)
        DropDowns(this, controller!!)

//        val buttonShowStat = Button(this)
//        buttonShowStat.text = "TEST"
//        buttonShowStat.background = R.drawable.my_textviews
//        buttonShowStat.setOnClickListener { controller!!.generate() }
//        val mainLayout = findViewById<ConstraintLayout>(R.id.mainLayout)
//        mainLayout.addView(buttonShowStat)
    }

    private fun HideTitleBar() {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        supportActionBar!!.hide()
    }

    fun startRecord     (view: View) { controller!!.startRecord() }
    fun stopRecord      (view: View) { controller!!.stopRecord() }
    fun reject          (view: View) { controller!!.reject() }
    fun changeCategory  (view: View) { controller!!.confirm(0f, false, "ChangedCategory") }
    fun addTwoSeconds   (view: View) { controller!!.confirm(2f, true, "Added Two") }
    fun confirm         (view: View) { controller!!.confirm(0f, true, "Confirmed") }
    fun showStat        (view: View) { controller!!.writeShortStat() }
    fun eventGenerate   (view: View) { controller!!.generate() }
}