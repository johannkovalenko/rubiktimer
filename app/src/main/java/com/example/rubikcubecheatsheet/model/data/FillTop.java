package com.example.rubikcubecheatsheet.model.data;

public class FillTop extends Fill
{
    public void Run(DB db, String rawText)
    {
        String[] split = rawText.split(";");

        for (int i=0; i<10; i++)
        {
            int mod = i % 5;
            int quot = (int) i / 5;

            int color = super.GetColor(split[i]);

            db.constellation[quot][mod] = color;
        }

        db.constellation[2][0] = super.GetColor(split[10]);
        db.constellation[2][4] = super.GetColor(split[11]);
    }
}
