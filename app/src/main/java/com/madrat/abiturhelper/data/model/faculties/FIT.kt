package com.madrat.abiturhelper.data.model.faculties

import com.madrat.abiturhelper.data.model.Student

data class FIT(
    val iasb: ArrayList<Student>,
    val ib: ArrayList<Student>,
    val ibas: ArrayList<Student>,
    val ivt: ArrayList<Student>,
    val inn: ArrayList<Student>,
    val ist: ArrayList<Student>,
    val moa: ArrayList<Student>,
    val pri: ArrayList<Student>,
    val pro: ArrayList<Student>
)