package com.madrat.abiturhelper.model.faculties.fee

import com.madrat.abiturhelper.model.Student

data class EIE(
        val zaochnPlat: ArrayList<Student>,
        val ochnBudg: ArrayList<Student>,
        val ochnLgot: ArrayList<Student>,
        val ochnPlat: ArrayList<Student>,
        val ochnCelevoe: ArrayList<Student>
)