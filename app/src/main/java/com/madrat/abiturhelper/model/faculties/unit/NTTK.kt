package com.madrat.abiturhelper.model.faculties.unit

import com.madrat.abiturhelper.model.Student

data class NTTK(
        val zaochnBudg: ArrayList<Student>,
        val zaochnLgot: ArrayList<Student>,
        val zaochnPlat: ArrayList<Student>
)