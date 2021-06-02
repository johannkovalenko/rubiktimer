package com.example.rubikcubecheatsheet.view.labels;

import android.view.View;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.rubikcubecheatsheet.MainActivity;

class Row
{
    MainActivity myForm;
    CreateLabel createLabel;
    TextView[][]    labels;

    public Row (MainActivity myForm, CreateLabel createLabel, TextView[][] labels)
    {
        this.myForm = myForm;
        this.createLabel = createLabel;
        this.labels = labels;
    }

    TableRow Create(int rowIndex, LabelType[] labelType)
    {
        TableRow row = new TableRow(myForm);

        for (int j=0; j<labelType.length; j++)
        {
            labels[rowIndex][j] = createLabel.Run(labelType[j]);
            row.addView(labels[rowIndex][j]);
            labels[rowIndex][j].setVisibility(View.INVISIBLE);
        }

        return row;
    }
}
