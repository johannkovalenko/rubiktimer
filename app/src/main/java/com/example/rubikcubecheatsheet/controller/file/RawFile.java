package com.example.rubikcubecheatsheet.controller.file;

import android.content.res.Resources;
import android.util.Log;

import java.io.*;
import java.nio.charset.*;
import java.util.*;

public class RawFile
{
    public List<String> Read(Resources resources, int fileID)
    {
        List<String> output = new ArrayList<>();

        try
        {
            InputStream is = resources.openRawResource(fileID);

            InputStreamReader inputStreamReader = new InputStreamReader(is, StandardCharsets.UTF_8);

            BufferedReader reader = new BufferedReader(inputStreamReader);

            String line = reader.readLine();
            while (line != null)
            {
                output.add(line);
                line = reader.readLine();
            }
        }
        catch (IOException e)
        {
            Log.d("READ_FILE", e.toString());
        }

        return output;
    }
    public List<String> Read2(File folder, String fileName)
    {
        List<String> output = new ArrayList<>();

        try
        {
            File file = new File(folder, fileName);
            FileReader fr=new FileReader(file);
            BufferedReader br=new BufferedReader(fr);

            String line;
            while((line=br.readLine())!=null)
            {
                output.add(line);
            }
            fr.close();
        }
        catch(IOException e)
        {
            Log.i("JKCheck", e.toString());
        }

        return output;
    }
}
