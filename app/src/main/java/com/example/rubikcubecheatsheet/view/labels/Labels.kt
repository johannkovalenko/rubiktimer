package com.example.rubikcubecheatsheet.view.labels;

import android.view.View;
import android.widget.*;
import android.graphics.drawable.*;
import android.graphics.*;

import com.example.rubikcubecheatsheet.MainActivity;

public class Labels
{
    TextView[][]    labels          = new TextView[7][5];
    Row row;
    CreateLabel createLabel;

    MainActivity myForm;

    public Labels(MainActivity myForm)
    {
        this.myForm = myForm;

        createLabel = new CreateLabel(myForm);
        row = new Row(myForm, createLabel, labels);
    }

    public void Create(TableLayout board)
    {
        LabelType[][] types = new CreateLabelTypes().Run();

        for(int i=0; i<types.length;i++)
            board.addView(row.Create(i, types[i]));
    }

    public void FillLabels(int color, int i, int j)
    {
        TextView label = labels[i][j];

        if (color == Color.GRAY || color == 0)
            label.setVisibility(View.INVISIBLE);
        else
            label.setVisibility(View.VISIBLE);

        Drawable drawable = label.getBackground();
        GradientDrawable shape = (GradientDrawable) drawable;

        shape.setColor(color);
    }
}
