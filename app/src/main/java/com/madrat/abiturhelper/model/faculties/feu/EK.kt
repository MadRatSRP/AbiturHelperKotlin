package com.madrat.abiturhelper.model.faculties.feu

import com.madrat.abiturhelper.model.Student

data class EK(
        val buaZaochnPlat: ArrayList<Student>,
        val buaOchnPlat: ArrayList<Student>,
        val logOchnPlat: ArrayList<Student>,
        val ocOchnPlat: ArrayList<Student>,
        val fZaochnPlat: ArrayList<Student>,
        val fOchnPlat: ArrayList<Student>,
        val epoOchnPlat: ArrayList<Student>
)