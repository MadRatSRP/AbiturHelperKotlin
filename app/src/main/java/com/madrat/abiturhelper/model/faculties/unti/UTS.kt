package com.madrat.abiturhelper.model.faculties.unti

import com.madrat.abiturhelper.model.Student

data class UTS(
        val ochnBudg: List<Student>,
        val ochnLgot: List<Student>,
        val ochnPlat: List<Student>,
        val ochnCelevoe: List<Student>
)