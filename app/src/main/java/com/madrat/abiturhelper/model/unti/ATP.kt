package com.madrat.abiturhelper.model.unti

import com.madrat.abiturhelper.model.Student

data class ATP(
        val atpZaochnBudg: List<Student>,
        val atpZaochnLgot: List<Student>,
        val atpZaochnPlat: List<Student>,
        val atpOchnBudg: List<Student>,
        val atpOchnLgot: List<Student>,
        val atpOchnPlat: List<Student>,
        val atpOchnCelevoe: List<Student>
)