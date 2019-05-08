package com.madrat.abiturhelper.model.faculties.feu

import com.madrat.abiturhelper.model.Student

data class PI(
        val kisOchnBudg: ArrayList<Student>,
        val kisOchnLgot: ArrayList<Student>,
        val kisOchnPlat: ArrayList<Student>,
        val ceOchnBudg: ArrayList<Student>,
        val ceOchnLgot: ArrayList<Student>,
        val ceOchnPlat: ArrayList<Student>
)