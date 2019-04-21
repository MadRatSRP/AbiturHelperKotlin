package com.madrat.abiturhelper.model.faculties.unti

import com.madrat.abiturhelper.model.Student

data class TMO(
        val oipmZaochnBudg: List<Student>,
        val oipmZaochnLgot: List<Student>,
        val oipmZaochnPlat: List<Student>,
        val ochnBudg: List<Student>,
        val ochnLgot: List<Student>,
        val ochnPlat: List<Student>,
        val ochnCelevoe: List<Student>
)