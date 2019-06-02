package com.madrat.abiturhelper.interfaces.fragments

import com.madrat.abiturhelper.model.Student

interface ShowStudentsMVP {
    interface View {
        fun setupMVP()
        fun showStudents(students: ArrayList<Student>)
    }
    interface Presenter {
        fun returnCurrentListOfStudents(): ArrayList<Student>?
    }
}