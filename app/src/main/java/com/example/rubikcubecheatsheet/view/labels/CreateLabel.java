package com.example.rubikcubecheatsheet.view.labels;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.rubikcubecheatsheet.MainActivity;

class CreateLabel
{
    MainActivity myForm;

    public CreateLabel (MainActivity myForm)
    {
        this.myForm = myForm;
    }

    TextView Run(LabelType labelType)
    {
        TextView output = new TextView(myForm);
        TableRow.LayoutParams params = new TableRow.LayoutParams();

        params.setMargins(15,15,0,0);

        output.setLayoutParams(params);
        //output.setPadding(10, 10, 10, 10);


        GradientDrawable shape = new GradientDrawable ();
        shape.setShape(GradientDrawable.RECTANGLE);
        shape.setCornerRadii(new float[] { 20, 20, 20, 20, 20, 20, 20, 20 });
        shape.setColor(Color.GRAY);
        shape.setStroke(8, Color.BLACK);

        //output.setText("X");

        switch (labelType) {
            case LARGESQUARE:
                output.setHeight (300);
                output.setWidth (300);
                output.setBackground(shape);
                break;
            case SMALLSQUARE:
                output.setHeight(100);
                output.setWidth(100);
                output.setBackground(shape);
                break;
            case LYINGRECTANGLE:
                output.setHeight(100);
                output.setWidth(300);
                output.setBackground(shape);
                break;
            case STANDINGRECTANGLE:
                output.setHeight(300);
                output.setWidth(100);
                output.setBackground(shape);
                break;
        }

        return output;
    }
}
