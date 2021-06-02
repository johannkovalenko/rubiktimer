package com.example.rubikcubecheatsheet.controller.statistics

import android.webkit.WebView
import com.example.rubikcubecheatsheet.model.scramble.Scramble
import com.example.rubikcubecheatsheet.model.statistics.Statistics
import kotlin.random.Random

class ShortStatistics(var webView: WebView, var statistics: Statistics?) {
    val scramble = Scramble(20)

    public fun write() {
        val sb = StringBuilder()
        sb.append("<html>")
        sb.append("<style>")
        sb.append("td {background-color:#f7f6df; font-size:16px}")
        sb.append(".withborder {border: solid 1pt black; background-color:#80ffbd}")
        sb.append("</style>")
        sb.append("<body>")

        statistics?.printShort(sb)

        print (sb, scramble.run())

        sb.append("</body>")
        sb.append("</html>")

        webView.loadDataWithBaseURL(null, sb.toString(), "text/html", "utf-8", null)
    }


    private fun print(sb : java.lang.StringBuilder, scrambleList : List<String>) {
        sb.append("<table>")
        val half : Int = scrambleList.size / 2 - 1

        sb.append("<tr>")
        for (i in scrambleList.indices) {
            sb.append("<td style='width:20px'>${scrambleList[i]}</td>")
            if (i == half)
                sb.append("</tr><tr>")
        }

        sb.append("</tr>")
        sb.append("</table>")
    }
}