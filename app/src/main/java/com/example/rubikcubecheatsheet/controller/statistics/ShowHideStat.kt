package com.example.rubikcubecheatsheet.controller.statistics

import android.view.View
import android.webkit.WebView
import com.example.rubikcubecheatsheet.model.CubeMode
import com.example.rubikcubecheatsheet.model.statistics.Statistics

object ShowHideStat {
    fun Run(webView: WebView, statistics: Statistics?, cubeMode: CubeMode?) {
        if (webView.visibility == View.VISIBLE) {
            webView.visibility = View.GONE
            //textViewStatistics.text = ""
        } else {
            val sb = StringBuilder()
            sb.append("<html>")
            sb.append("<style>")
            sb.append("td {background-color:#f7f6df; font-size:16px}")
            sb.append(".withborder {border: solid 1pt black; background-color:#80ffbd}")
            sb.append(".red {border: solid 1pt black; background-color:red}")
            sb.append("</style>")
            sb.append("<body>")

            statistics?.print(sb)

            sb.append("</body>")
            sb.append("</html>")
            webView.bringToFront()
            //webView.setBackgroundColor(Color.WHITE)
            webView.loadDataWithBaseURL(null, sb.toString(), "text/html", "utf-8", null)
            webView.visibility = View.VISIBLE
        }
    }
}