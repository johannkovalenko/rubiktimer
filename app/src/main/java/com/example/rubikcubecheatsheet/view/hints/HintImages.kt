package com.example.rubikcubecheatsheet.view.hints

import android.util.Log
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.res.ResourcesCompat
import com.example.rubikcubecheatsheet.MainActivity
import com.example.rubikcubecheatsheet.R
import com.example.rubikcubecheatsheet.R.drawable
import java.util.*

class HintImages(var myForm: MainActivity) {
    var images: MutableMap<String, ImageView> = HashMap()
    fun FillImages() {
        val files = drawable::class.java.fields
        for (field in files) {
            try {
                val drawable = ResourcesCompat.getDrawable(myForm.resources, field.getInt(null), null)
                val imageView = ImageView(myForm)
                imageView.setImageDrawable(drawable)
                images[field.name] = imageView
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun FillPictureBox(pictures: List<String?>) {
        val layoutHints = myForm.findViewById<LinearLayout>(R.id.layouthints)
        layoutHints!!.removeAllViews()

        //LayoutInflater inflator = (LayoutInflater)myForm.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //LinearLayout inflatedView;
        for (picture in pictures) {
            try {
                //ImageView temp = new ImageView(myForm);
                //temp = images.get(picture);
                //temp.setId(counter);
                val temp = myForm.resources.getIdentifier(picture, "drawable", myForm.packageName)
                val drawable = myForm.resources.getDrawable(temp)
                val imageView = ImageView(myForm)
                imageView.setImageDrawable(drawable)
                layoutHints!!.addView(imageView)
            } catch (ex: Exception) {
                Log.i("JK", ex.toString())
            }
        }
    }
}