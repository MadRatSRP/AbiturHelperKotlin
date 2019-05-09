package com.madrat.abiturhelper.model.faculties.fee

import com.madrat.abiturhelper.model.Student

data class EM(
        val dvsZaochnBudg: ArrayList<Student>,
        val dvsZaochnLgot: ArrayList<Student>,
        val dvsZaochnPlat: ArrayList<Student>,
        val dvsOchnBudg: ArrayList<Student>,
        val dvsOchnLgot: ArrayList<Student>,
        val dvsOchnPlat: ArrayList<Student>,
        val tOchnBudg: ArrayList<Student>,
        val tOchnLgot: ArrayList<Student>,
        val tOchnPlat: ArrayList<Student>,
        val tOchnCelevoe: ArrayList<Student>,
        val emksZaochnPlat: ArrayList<Student>
)