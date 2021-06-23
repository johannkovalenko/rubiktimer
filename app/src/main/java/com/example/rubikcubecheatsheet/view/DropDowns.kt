package com.example.rubikcubecheatsheet.view;

import android.R
import android.view.View
import android.webkit.WebView
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.example.rubikcubecheatsheet.controller.Controller
import com.example.rubikcubecheatsheet.model.enumerations.Mode
import java.util.*
import java.util.stream.Collectors

public class DropDowns (val mainForm : AppCompatActivity, val controller: Controller)
{
    private val spinner : Spinner = mainForm.findViewById(com.example.rubikcubecheatsheet.R.id.spinner)
    private val spinnerMode : Spinner = mainForm.findViewById(com.example.rubikcubecheatsheet.R.id.spinnerMode)

    init {

        spinnerSetListener()
        spinnerModeSetListener()
        fillDropdown(controller.getDataDict(), spinner)
        fillDropdown(EnumSet.allOf(Mode::class.java).stream().map { obj: Mode -> obj.name }.collect(Collectors.toList()), spinnerMode)
    }

    private fun fillDropdown(keys: List<String>, spinner: Spinner?) {
        val arrayAdapter = ArrayAdapter(mainForm, R.layout.simple_spinner_item, keys)
        arrayAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        spinner!!.adapter = arrayAdapter
    }

    private fun spinnerSetListener() {

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View, i: Int, l: Long) {
                mainForm.findViewById<LinearLayout>(com.example.rubikcubecheatsheet.R.id.layouthints).visibility = View.VISIBLE
                val item =
                controller!!.search(spinner.selectedItem as String)
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {}
        }
    }

    private fun spinnerModeSetListener() {
        spinnerMode.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View, i: Int, l: Long) {
                val item = spinnerMode.selectedItem as String
                controller.setCubeMode(item)
                val temp : WebView = mainForm.findViewById(com.example.rubikcubecheatsheet.R.id.shortStat)
                temp.loadDataWithBaseURL(null, controller.getShortStatistics().toString(), "text/html", "utf-8", null)
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {}
        }
    }
}
