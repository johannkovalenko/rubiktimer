package com.example.rubikcubecheatsheet.controller;

import com.example.rubikcubecheatsheet.model.data.DB;
import com.example.rubikcubecheatsheet.view.labels.Labels;

import java.util.List;

abstract class Fill
{
    protected List<DB> data;
    protected Labels labels;

    public Fill(List<DB> data, Labels labels)
    {
        this.data = data;
        this.labels = labels;
    }

    public abstract void Run(int index);

    protected void FillFirstTwoRows(int index)
    {
        for (int i=0; i<2; i++)
            for (int j=0; j<5; j++)
            {
                int color = data.get(index).constellation[i][j];

                labels.FillLabels(color, i, j);
            }
    }
}
