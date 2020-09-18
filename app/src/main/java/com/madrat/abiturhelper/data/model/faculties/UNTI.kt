package com.madrat.abiturhelper.data.model.faculties

import com.madrat.abiturhelper.data.model.Student

data class UNTI(
    val atp: ArrayList<Student>,
    val kto: ArrayList<Student>,
    val mash: ArrayList<Student>,
    val mitm: ArrayList<Student>,
    val mht: ArrayList<Student>,
    val ptmk: ArrayList<Student>,
    val tmo: ArrayList<Student>,
    val uts: ArrayList<Student>
)