package com.madrat.abiturhelper.data.interfaces.fragments

import com.madrat.abiturhelper.data.model.Student

interface ShowStudentsMVP {
    interface View {
        fun setupMVP()
        fun showStudents(students: ArrayList<Student>)
    }
    interface Presenter {
        fun returnCurrentListOfStudents(): ArrayList<Student>?
    }
}