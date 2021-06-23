package com.example.rubikcubecheatsheet.view

import android.view.View
import android.webkit.WebView
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.rubikcubecheatsheet.R

class WebViews (private val mainForm : AppCompatActivity) {
    private val shortStat = mainForm.findViewById<WebView>(R.id.shortStat)
    private val webView = mainForm.findViewById<WebView>(R.id.textViewStatistics)

    public fun updateShortStat(sb : StringBuilder) {
        shortStat.loadDataWithBaseURL(null, sb.toString(), "text/html", "utf-8", null)
    }

    public fun updateStat(sb : StringBuilder) {

        if (webView.visibility == View.VISIBLE) {
            webView.visibility = View.GONE
        }
        else {
            webView.bringToFront()
            webView.loadDataWithBaseURL(null, sb.toString(), "text/html", "utf-8", null)
            webView.visibility = View.VISIBLE
        }

        mainForm.findViewById<Button>(R.id.generate).visibility = if
                (mainForm.findViewById<Button>(R.id.generate).visibility == View.INVISIBLE) View.VISIBLE else View.INVISIBLE
        mainForm.findViewById<Button>(R.id.startRecord).visibility = if
                (mainForm.findViewById<Button>(R.id.startRecord).visibility == View.INVISIBLE) View.VISIBLE else View.INVISIBLE

    }
}