package com.madrat.abiturhelper.model.faculties.unit

import com.madrat.abiturhelper.model.Student

data class ETTK(
        val aiahOchnBudg: ArrayList<Student>,
        val aiahOchnLgot: ArrayList<Student>,
        val aiahOchnPlat: ArrayList<Student>,
        val aiahOchnCelevoe: ArrayList<Student>,
        val psjdOchnBudg: ArrayList<Student>,
        val psjdOchnLgot: ArrayList<Student>,
        val psjdOchnPlat: ArrayList<Student>
)