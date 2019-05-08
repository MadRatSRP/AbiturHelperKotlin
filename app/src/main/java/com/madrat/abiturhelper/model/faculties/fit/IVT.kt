package com.madrat.abiturhelper.model.faculties.fit

import com.madrat.abiturhelper.model.Student

data class IVT(
        val poZaochnPlat: ArrayList<Student>,
        val poOchnBudg: ArrayList<Student>,
        val poOchnLgot: ArrayList<Student>,
        val poOchnPlat: ArrayList<Student>,
        val poOchnCelevoe: ArrayList<Student>,
        val saprOchnBudg: ArrayList<Student>,
        val saprOchnLgot: ArrayList<Student>,
        val saprOchnPlat: ArrayList<Student>
)