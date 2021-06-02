package com.example.rubikcubecheatsheet.controller;

import com.example.rubikcubecheatsheet.model.data.DB;
import com.example.rubikcubecheatsheet.view.labels.Labels;

import java.util.List;

class Fill180R extends Fill
{
    public Fill180R(List<DB> data, Labels labels)
    {
        super(data, labels);
    }

    public void Run(int index)
    {
        super.FillFirstTwoRows(index);

        for (int i=2; i<7; i++)
            for (int j=0; j<5; j++)
            {
                int color = data.get(index).constellation[i][j];

                labels.FillLabels(color, 8 - i, 4 - j);
            }
    }
}
