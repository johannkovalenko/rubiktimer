package com.example.rubikcubecheatsheet.view

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.res.ResourcesCompat
import com.example.rubikcubecheatsheet.MainActivity
import com.example.rubikcubecheatsheet.R
import com.example.rubikcubecheatsheet.R.drawable
import java.util.*

class HintImages(var mainForm: MainActivity) {
    private var images: MutableMap<String, ImageView> = HashMap()

    public fun FillImages() {
        val files = drawable::class.java.fields
        for (field in files) {
            try {
                val drawable = ResourcesCompat.getDrawable(mainForm.resources, field.getInt(null), null)
                val imageView = ImageView(mainForm)
                imageView.setImageDrawable(drawable)
                images[field.name] = imageView
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    public fun FillPictureBox(pictures: List<String?>) {
        val layoutHints = mainForm.findViewById<LinearLayout>(R.id.layouthints)
        layoutHints!!.removeAllViews()

        for (picture in pictures) {
            try {
                val temp = mainForm.resources.getIdentifier(picture, "drawable", mainForm.packageName)
                val drawable = mainForm.resources.getDrawable(temp)
                val imageView = ImageView(mainForm)
                imageView.setImageDrawable(drawable)
                layoutHints!!.addView(imageView)
            } catch (ex: Exception) {
                Log.i("JK", ex.toString())
            }
        }
    }

    public fun showHide() {
        val layoutHints = mainForm.findViewById<LinearLayout>(R.id.layouthints)
        when (layoutHints.visibility) {
            View.VISIBLE -> layoutHints.visibility = View.INVISIBLE
            View.INVISIBLE, View.GONE -> layoutHints.visibility = View.VISIBLE
        }
    }
}