package com.madrat.abiturhelper.model.faculties.unti

import com.madrat.abiturhelper.model.Student

data class ATP(
        val zaochnBudg: ArrayList<Student>,
        val zaochnLgot: ArrayList<Student>,
        val zaochnPlat: ArrayList<Student>,
        val ochnBudg: ArrayList<Student>,
        val ochnLgot: ArrayList<Student>,
        val ochnPlat: ArrayList<Student>,
        val ochnCelevoe: ArrayList<Student>
)