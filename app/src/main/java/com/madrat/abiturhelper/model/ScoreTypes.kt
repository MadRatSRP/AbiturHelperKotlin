package com.madrat.abiturhelper.model

data class ScoreTypes(
    var physicsStudents: ArrayList<Student>,
    var computerScienceStudents: ArrayList<Student>,
    var socialScienceStudents: ArrayList<Student>,
    var noOrNotEnoughDataStudents: ArrayList<Student>,
    var partAndAllDataStudents: ArrayList<Student>
)
