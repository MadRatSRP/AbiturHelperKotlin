package com.madrat.abiturhelper.model.faculties.unit

import com.madrat.abiturhelper.model.Student

data class PM(
        val bmOchnBudg: ArrayList<Student>,
        val bmOchnLgot: ArrayList<Student>,
        val bmOchnPlat: ArrayList<Student>,
        val dpmOchnBudg: ArrayList<Student>,
        val dpmOchnLgot: ArrayList<Student>,
        val dpmOchnPlat: ArrayList<Student>
)