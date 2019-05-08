package com.madrat.abiturhelper.model.faculties.fit

import com.madrat.abiturhelper.model.Student

data class PRO(
        val gdOchnBudg: ArrayList<Student>,
        val gdOchnLgot: ArrayList<Student>,
        val gdOchnPlat: ArrayList<Student>,
        val ivtZaochnPlat: ArrayList<Student>,
        val ekZaochnPlat: ArrayList<Student>
)