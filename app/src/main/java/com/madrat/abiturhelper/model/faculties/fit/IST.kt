package com.madrat.abiturhelper.model.faculties.fit

import com.madrat.abiturhelper.model.Student

data class IST(
        val isitdOchnBudg: ArrayList<Student>,
        val isitdOchnLgot: ArrayList<Student>,
        val isitdOchnPlat: ArrayList<Student>,
        val itipkOchnBudg: ArrayList<Student>,
        val itipkOchnLgot: ArrayList<Student>,
        val itipkOchnPlat: ArrayList<Student>,
        val zaochnPlat: ArrayList<Student>
)