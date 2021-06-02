package com.example.rubikcubecheatsheet.model.data;

import android.content.res.Resources;
import com.example.rubikcubecheatsheet.R;
import com.example.rubikcubecheatsheet.controller.file.RawFile;

import java.util.List;
import java.util.Map;

public class Data
{
    public void Prepare(Resources resources, List<DB> data, Map<String, DB> data_dict)
    {
        Board board = new Board();

        List<String> allLines = new RawFile().Read(resources, R.raw.input);

        for (String line : allLines)
        {
            String[] split = line.split("/");

            DB db = new DB();

            board.fill3x3.Run   (db, split[0]);
            board.fillSides.Run	(db, split[1]);


            if (split.length >= 4)
                FillPictures			(db, split[3]);

            if (split.length >=5)
                board.fillTop.Run(db, split[4]);


            data.add(db);
            data_dict.put(split[2], db);
        }
    }

    void FillPictures(DB db, String rawText)
    {
        String[] split = rawText.split(";");

        for (String picture : split)
                db.pictures.add(picture.toLowerCase());

    }
}
