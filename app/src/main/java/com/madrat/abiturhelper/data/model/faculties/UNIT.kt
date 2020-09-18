package com.madrat.abiturhelper.data.model.faculties

import com.madrat.abiturhelper.data.model.Student

data class UNIT(
    val nttk: ArrayList<Student>,
    val ntts: ArrayList<Student>,
    val pm: ArrayList<Student>,
    val psjd: ArrayList<Student>,
    val ttp: ArrayList<Student>,
    val ettk: ArrayList<Student>
)