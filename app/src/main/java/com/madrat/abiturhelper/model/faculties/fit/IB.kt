package com.madrat.abiturhelper.model.faculties.fit

import com.madrat.abiturhelper.model.Student

data class IB(
        val vechPlat: ArrayList<Student>,
        val ochnBudg: ArrayList<Student>,
        val ochnLgot: ArrayList<Student>,
        val ochnPlat: ArrayList<Student>
)