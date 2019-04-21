package com.madrat.abiturhelper.model.faculties.unti

import com.madrat.abiturhelper.model.Student

data class ATP(
        val zaochnBudg: List<Student>,
        val zaochnLgot: List<Student>,
        val zaochnPlat: List<Student>,
        val ochnBudg: List<Student>,
        val ochnLgot: List<Student>,
        val ochnPlat: List<Student>,
        val ochnCelevoe: List<Student>
)