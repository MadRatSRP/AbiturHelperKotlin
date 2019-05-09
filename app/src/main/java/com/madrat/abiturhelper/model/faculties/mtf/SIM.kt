package com.madrat.abiturhelper.model.faculties.mtf

import com.madrat.abiturhelper.model.Student

data class SIM(
        val zaochnPlat: ArrayList<Student>,
        val ochnBudg: ArrayList<Student>,
        val ochnLgot: ArrayList<Student>,
        val ochnPlat: ArrayList<Student>
)