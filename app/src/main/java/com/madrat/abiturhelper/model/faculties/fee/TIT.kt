package com.madrat.abiturhelper.model.faculties.fee

import com.madrat.abiturhelper.model.Student

data class TIT(
        val iskZaochnPlat: ArrayList<Student>,
        val ochnBudg: ArrayList<Student>,
        val ochnLgot: ArrayList<Student>,
        val ochnPlat: ArrayList<Student>
)