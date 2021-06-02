package com.example.rubikcubecheatsheet.view.labels;

class CreateLabelTypes
{
    public LabelType[][] Run()
    {
        LabelType[] type2 = {LabelType.STANDINGRECTANGLE, LabelType.LARGESQUARE, LabelType.LARGESQUARE, LabelType.LARGESQUARE, LabelType.STANDINGRECTANGLE};
        LabelType[] type1 = {LabelType.SMALLSQUARE, LabelType.LYINGRECTANGLE, LabelType.LYINGRECTANGLE, LabelType.LYINGRECTANGLE, LabelType.SMALLSQUARE};
        return new LabelType[][] {type1, type1, type1, type2, type2, type2, type1};
    }
}
