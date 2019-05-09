package com.madrat.abiturhelper.model.faculties.fee

import com.madrat.abiturhelper.model.Student

data class EIN(
        val mteOchnBudg: ArrayList<Student>,
        val mteOchnLgot: ArrayList<Student>,
        val mteOchnPlat: ArrayList<Student>,
        val peOchnBudg: ArrayList<Student>,
        val peOchnLgot: ArrayList<Student>,
        val peOchnPlat: ArrayList<Student>
)