package com.example.rubikcubecheatsheet.model.statistics.category.ao

import com.example.rubikcubecheatsheet.model.Entry
import com.example.rubikcubecheatsheet.model.statistics.category.Data

class AO5(data : Data) : AO(data){
    public override fun add(entry: Entry) {
        super.arriving(entry)
        if (super.isBelowThreshold(entry))
            return
        super.leaving()
        super.setEver(entry, false)
        super.topPerDay(entry)
        super.setDay(entry, false)
    }

    public override fun colorScheme(dnf : Int) : String {
        when (dnf){
            0-> return "rgb(0, 204, 0)"
        }
        return "rgb(255, 77, 77)"
    }
}