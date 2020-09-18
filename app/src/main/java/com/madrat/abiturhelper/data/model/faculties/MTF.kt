package com.madrat.abiturhelper.data.model.faculties

import com.madrat.abiturhelper.data.model.Student

data class MTF(
    val mash: ArrayList<Student>,
    val sim: ArrayList<Student>,
    val tb: ArrayList<Student>,
    val uk: ArrayList<Student>
)