package com.example.rubikcubecheatsheet.controller;

import com.example.rubikcubecheatsheet.view.hints.HintImages;
import com.example.rubikcubecheatsheet.model.data.DB;
import com.example.rubikcubecheatsheet.view.labels.Labels;
import java.util.*;

public class Search
{
    Map<String, DB> data_dict;
    Labels labels;
    HintImages hintImages;

    public Search(Map<String, DB> data_dict, Labels labels, HintImages hintImages)
    {
        this.data_dict = data_dict;
        this.labels = labels;
        this.hintImages = hintImages;
    }

    public void Run(String item)
    {
        Fill(item);
        hintImages.FillPictureBox(data_dict.get(item).pictures);
    }

    void Fill(String item)
    {
        for (int i=0; i<7; i++)
            for (int j=0; j<5; j++)
            {
                int color = data_dict.get(item).constellation[i][j];
                //android.util.Log.i("JKCheck", i + "  " + j + "   " + Integer.toString(color));

                labels.FillLabels(color, i, j);
            }
    }
}
