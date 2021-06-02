package com.example.rubikcubecheatsheet.model

import com.example.rubikcubecheatsheet.model.enumerations.Mode
import java.time.LocalDateTime

data class Entry(
        public val dateAndTime: LocalDateTime,
        public val whenDate: LocalDateTime,
        public val seconds : Float,
        public val mode : Mode,
        public val dnf : Boolean,
        public val groupedDate : LocalDateTime)
