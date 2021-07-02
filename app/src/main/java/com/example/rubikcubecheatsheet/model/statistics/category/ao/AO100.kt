package com.example.rubikcubecheatsheet.model.statistics.category.ao

import com.example.rubikcubecheatsheet.model.Entry
import com.example.rubikcubecheatsheet.model.statistics.Top100
import com.example.rubikcubecheatsheet.model.statistics.category.Data

class AO100(data : Data, top100: Top100) : AO(data, top100){
    public override fun add(entry: Entry) {
        super.arriving(entry)
        super.add(entry, percentiles)
        if (super.isBelowThreshold(entry))
            return
        super.leaving()
        super.remove(data, percentiles)
        super.setEver(entry, true)
        super.topPerDay(entry)
        super.setDay(entry, true)
    }

    public override fun colorScheme(dnf : Int) : String {
        when (dnf){
            in 0..1 -> return "rgb(0, 204, 0)"
            in 2..3 -> return "yellow"
            in 4..5 -> return "orange"
        }
        return "rgb(255, 77, 77)"
    }
}