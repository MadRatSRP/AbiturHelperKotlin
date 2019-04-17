package com.madrat.abiturhelper.model.faculties

import com.madrat.abiturhelper.model.Student

data class Unti(
        val specialtiesATP: List<Student>,
        val specialtiesKTO: List<Student>,
        val specialtiesMASH: List<Student>,
        val specialtiesMiTM: List<Student>,
        val specialtiesMHT: List<Student>,
        val specialtiesPTMK: List<Student>,
        val specialtiesTMO: List<Student>,
        val specialtiesUTS: List<Student>
)