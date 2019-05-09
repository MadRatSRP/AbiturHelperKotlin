package com.madrat.abiturhelper.model.faculties.unit

import com.madrat.abiturhelper.model.Student

data class PSJD(
        val vOchnPlat: ArrayList<Student>,
        val lOchnPlat: ArrayList<Student>,
        val zaochnPlat: ArrayList<Student>
)