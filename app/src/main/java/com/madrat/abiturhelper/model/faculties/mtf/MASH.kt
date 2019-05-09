package com.madrat.abiturhelper.model.faculties.mtf

import com.madrat.abiturhelper.model.Student

data class MASH(
        val lZaochnBudg: ArrayList<Student>,
        val lZaochnLgot: ArrayList<Student>,
        val lZaochnPlat: ArrayList<Student>,
        val lOchnBudg: ArrayList<Student>,
        val lOchnLgot: ArrayList<Student>,
        val lOchnPlat: ArrayList<Student>,
        val sZaochnBudg: ArrayList<Student>,
        val sZaochnLgot: ArrayList<Student>,
        val sZaochnPlat: ArrayList<Student>,
        val sOchnBudg: ArrayList<Student>,
        val sOchnLgot: ArrayList<Student>,
        val sOchnPlat: ArrayList<Student>,
        val sOchnCelevoe: ArrayList<Student>
)