package com.example.rubikcubecheatsheet.model

import com.example.rubikcubecheatsheet.model.enumerations.Mode

class CubeMode (var mode : Mode) {
    fun set(mode: String?) {
        this.mode = Mode.valueOf(mode!!)
    }
}