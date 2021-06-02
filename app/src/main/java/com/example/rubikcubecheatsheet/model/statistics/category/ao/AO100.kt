package com.example.rubikcubecheatsheet.model.statistics.category.ao

import com.example.rubikcubecheatsheet.model.Entry
import com.example.rubikcubecheatsheet.model.statistics.category.Data

class AO100(data : Data) : AO(data){
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
            0, 1, 2 -> return "rgb(0, 204, 0)"
            3, 4, 5, 6 -> return "yellow"
            7 -> return "orange"
        }
        return "rgb(255, 77, 77)"
    }
}