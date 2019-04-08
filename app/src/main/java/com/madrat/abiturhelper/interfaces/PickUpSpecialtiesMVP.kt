package com.madrat.abiturhelper.interfaces

import com.madrat.abiturhelper.model.Specialty
import com.madrat.abiturhelper.model.Student

interface PickUpSpecialtiesMVP {
    interface View {
        fun grabSpecialties(path: String): ArrayList<Specialty>
        fun divideSpecialtiesByFaculty(list: ArrayList<Specialty>)
        fun grabStudents(path: String): ArrayList<Student>
        fun divideStudentsListByAdmissions(list: ArrayList<Student>)
    }

}
