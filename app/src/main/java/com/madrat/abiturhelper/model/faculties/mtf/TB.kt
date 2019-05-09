package com.madrat.abiturhelper.model.faculties.mtf

import com.madrat.abiturhelper.model.Student

data class TB(
        val btpipZaochnPlat: ArrayList<Student>,
        val btpipOchnBudg: ArrayList<Student>,
        val btpipOchnLgot: ArrayList<Student>,
        val btpipOchnPlat: ArrayList<Student>
)