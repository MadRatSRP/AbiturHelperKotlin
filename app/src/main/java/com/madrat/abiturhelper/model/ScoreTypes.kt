package com.madrat.abiturhelper.model

data class ScoreTypes(
    // С баллами только по физике
    var physicsStudents: ArrayList<Student>,
    // С баллами только по информатике
    var computerScienceStudents: ArrayList<Student>,
    // С баллами только по обществознанию
    var socialScienceStudents: ArrayList<Student>,
    // С баллами по двум и более предметам
    var partAndAllDataStudents: ArrayList<Student>
)
