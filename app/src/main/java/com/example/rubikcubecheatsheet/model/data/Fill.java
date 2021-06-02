package com.example.rubikcubecheatsheet.model.data;

import android.graphics.Color;

public abstract class Fill
{
    public abstract void Run(DB db, String text);

    protected int GetColor(String letter)
    {
        switch (letter.toUpperCase())
        {
            case "Y":
                return Color.YELLOW;
            case "R":
                return Color.RED;
            case "G":
                return Color.GREEN;
            case "O":
                return Color.rgb(255, 165, 0); //orange
            case "B":
                return Color.BLUE;
            case "W":
                return Color.WHITE;
            case "X":
                return Color.GRAY;
            //return Color.TRANSPARENT;
            case "Z":
                return Color.BLACK;
            default:
                return Color.GRAY;
        }
    }
}
