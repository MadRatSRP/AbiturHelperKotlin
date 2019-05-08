package com.madrat.abiturhelper.model.faculties.fit

import com.madrat.abiturhelper.model.Student

data class PRI(
        val ochnBudg: ArrayList<Student>,
        val ochnLgot: ArrayList<Student>,
        val ochnPlat: ArrayList<Student>,
        val ochnCelevoe: ArrayList<Student>
)