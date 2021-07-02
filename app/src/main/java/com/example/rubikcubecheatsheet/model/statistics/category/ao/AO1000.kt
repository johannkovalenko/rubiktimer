package com.example.rubikcubecheatsheet.model.statistics.category.ao

import com.example.rubikcubecheatsheet.model.Entry
import com.example.rubikcubecheatsheet.model.statistics.Top100
import com.example.rubikcubecheatsheet.model.statistics.category.Data

class AO1000(data : Data, top100: Top100) : AO(data, top100){
    public override fun add(entry: Entry) {
//        super.arriving(entry)
//        if (super.isBelowThreshold(entry))
//            return
//        super.leaving()
//        super.setEver(entry, false)
//        super.topPerDay(entry)
//        super.setDay(entry, false)

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
            in 0..10 -> return "rgb(0, 204, 0)"
            in 11..30 -> return "yellow"
            in 31..50 -> return "orange"
        }
        return "rgb(255, 77, 77)"
    }
}