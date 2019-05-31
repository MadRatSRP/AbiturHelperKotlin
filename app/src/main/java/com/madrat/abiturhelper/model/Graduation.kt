package com.madrat.abiturhelper.model

data class Graduation(
        val specialtyName: String,
        val facultyName: String,
        val amountOfStudents: Int,
        val position: Int,
        val entriesTotal: Int,
        val oldMinimalScore: Int,
        val newMinimalScore: Int,
        val currentStudentsList: ArrayList<Student>
)
