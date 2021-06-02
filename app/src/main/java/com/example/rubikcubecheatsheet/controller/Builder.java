package com.example.rubikcubecheatsheet.controller;

import com.example.rubikcubecheatsheet.model.data.DB;
import com.example.rubikcubecheatsheet.view.labels.Labels;
import java.util.*;

class Builder
{
    private ArrayList<Fill> fills = new ArrayList<>();

    public Builder (List<DB> data, Labels labels)
    {
        fills.add(new Fill0R(data, labels));
        fills.add(new Fill90R(data, labels));
        fills.add(new Fill270R(data, labels));
        fills.add(new Fill180R(data, labels));
        fills.add(new Fill0L(data, labels));
        fills.add(new Fill180L(data, labels));
        fills.add(new Fill90L(data, labels));
        fills.add(new Fill270L(data, labels));
    }
    public Fill Get(int position)
    {
        return fills.get(position);
    }

    public int NumberOfPositions()
    {
        return fills.size();
    }
}
