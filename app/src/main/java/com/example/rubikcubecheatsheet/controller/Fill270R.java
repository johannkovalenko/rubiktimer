package com.example.rubikcubecheatsheet.controller;

import com.example.rubikcubecheatsheet.model.data.DB;
import com.example.rubikcubecheatsheet.view.labels.Labels;

import java.util.List;

class Fill270R extends Fill
{
    public Fill270R(List<DB> data, Labels labels)
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

                labels.FillLabels(color, 6- j, i -2);
            }
    }
}
