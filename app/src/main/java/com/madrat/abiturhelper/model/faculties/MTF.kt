package com.madrat.abiturhelper.model.faculties

import com.madrat.abiturhelper.model.faculties.mtf.MASH
import com.madrat.abiturhelper.model.faculties.mtf.SIM
import com.madrat.abiturhelper.model.faculties.mtf.TB
import com.madrat.abiturhelper.model.faculties.mtf.UK

data class MTF(
        val mash: MASH,
        val sim: SIM,
        val tb: TB,
        val uk: UK
)