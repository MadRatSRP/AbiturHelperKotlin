package com.madrat.abiturhelper.model.faculties.unti

import com.madrat.abiturhelper.model.Student

data class KTO(
        val atkmOchnBudg: ArrayList<Student>,
        val atkmOchnLgot: ArrayList<Student>,
        val atkmOchnPlat: ArrayList<Student>,
        val tmOchnBudg: ArrayList<Student>,
        val tmOchnLgot: ArrayList<Student>,
        val tmOchnPlat: ArrayList<Student>,
        val tmOchnCelevoe: ArrayList<Student>,
        val vechBudg: ArrayList<Student>,
        val vechLgot: ArrayList<Student>,
        val vechPlat: ArrayList<Student>
)