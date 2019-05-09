package com.madrat.abiturhelper.model.faculties.unit

import com.madrat.abiturhelper.model.Student

data class NTTS(
        val ochnBudg: ArrayList<Student>,
        val ochnLgot: ArrayList<Student>,
        val ochnPlat: ArrayList<Student>
)