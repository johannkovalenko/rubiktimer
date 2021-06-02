package com.example.rubikcubecheatsheet.model.data;

public class FillSides extends Fill
{
    public void Run(DB db, String rawText)
    {
        String[] split = rawText.split(";");

        for (int i=0; i<12; i++)
        {
            int mod = i % 3;
            int quot = (int) i / 3;

            int color = GetColor(split[i]);

            switch (quot)
            {
                case 0:
                    db.constellation[0+2][mod+1] = color;
                    break;
                case 1:
                    db.constellation[4+2][mod+1] = color;
                    break;
                case 2:
                    db.constellation[mod+1+2][0] = color;
                    break;
                case 3:
                    db.constellation[mod+1+2][4] = color;
                    break;
            }
        }
    }
}
