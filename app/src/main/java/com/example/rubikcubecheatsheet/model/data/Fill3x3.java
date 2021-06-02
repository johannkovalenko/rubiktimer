package com.example.rubikcubecheatsheet.model.data;

public class Fill3x3 extends Fill
{
    public void Run(DB db, String rawText)
    {
        String[] split = rawText.split(";");

        int iterations = 3 * 3;

        for (int i=0; i<iterations; i++)
        {
            int x = (int) i / 3;
            int y = i % 3;

            db.constellation[x+1+2][y+1] = super.GetColor(split[i]);
        }
    }
}
