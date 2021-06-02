package com.example.rubikcubecheatsheet.controller.file;

import java.io.*;

public class Append
{
    public void Run(File folder, String fileName, String text)
    {
        File file = new File(folder, fileName);

        try {
            FileWriter writer = new FileWriter(file, true);

            // Writes the content to the file
            writer.append(text + "\r\n");
            writer.flush();
            writer.close();
        }
        catch (Exception ex)
        {

        }
    }
}
