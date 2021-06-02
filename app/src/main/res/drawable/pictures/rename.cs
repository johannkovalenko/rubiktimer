using System;
using System.IO;

class MainClass
{
    static void Main()
    {
        Replace();
    }

    static void Rename()
    {
        string[] files = Directory.GetFiles(@"C:\Users\johan\AndroidStudioProjects\RubikCubeCheatSheet\app\src\main\res\drawable", "*.png");

        foreach (string file in files)
        {
            string newFile = file.Replace("'", "x").ToLower();

            File.Move(file, newFile);
        }
    }

    static void Replace()
    {
        string file = @"C:\Users\johan\AndroidStudioProjects\RubikCubeCheatSheet\app\src\main\res\raw\input.txt";
        string rawText = File.ReadAllText(file);
        rawText = rawText.Replace("'", "x").ToLower();
        File.WriteAllText(file, rawText);
    }
}