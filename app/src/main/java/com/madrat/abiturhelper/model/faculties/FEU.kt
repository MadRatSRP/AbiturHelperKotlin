package com.madrat.abiturhelper.model.faculties

import com.madrat.abiturhelper.model.Student

data class FEU(
        val bi: ArrayList<Student>,
        val pi: ArrayList<Student>,
        val sc: ArrayList<Student>,
        val td: ArrayList<Student>,
        val eb: ArrayList<Student>,
        val ek: ArrayList<Student>
)