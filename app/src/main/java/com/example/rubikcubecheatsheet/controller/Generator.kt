package com.example.rubikcubecheatsheet.controller;

import com.example.rubikcubecheatsheet.model.data.DB;
import com.example.rubikcubecheatsheet.view.hints.HintImages;
import com.example.rubikcubecheatsheet.view.labels.Labels;

import java.util.*;

public class Generator
{
    Builder builder;
    List<DB> data;
    HintImages hintImages;
    int numberOfPositions;

    public Generator(List<DB> data, Labels labels, HintImages hintImages)
    {
        this.data = data;
        builder = new Builder(data, labels);
        numberOfPositions = builder.NumberOfPositions();
        this.hintImages = hintImages;
    }

    public void Run()
    {
        int index = new Random().nextInt(data.size());
        int position = new Random().nextInt(numberOfPositions);

        Fill fill = builder.Get(position);
        fill.Run(index);

        hintImages.FillPictureBox(data.get(index).pictures);
    }

}
