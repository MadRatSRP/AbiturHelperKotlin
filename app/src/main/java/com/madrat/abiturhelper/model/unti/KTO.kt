package com.madrat.abiturhelper.model.unti

import com.madrat.abiturhelper.model.Student

data class KTO(
        val atkmOchnBudg: List<Student>,
        val atkmOchnLgot: List<Student>,
        val atkmOchnPlat: List<Student>,
        val tmOchnBudg: List<Student>,
        val tmOchnLgot: List<Student>,
        val tmOchnPlat: List<Student>,
        val tmOchnCelevoe: List<Student>,
        val vechBudg: List<Student>,
        val vechLgot: List<Student>,
        val vechPlat: List<Student>
)