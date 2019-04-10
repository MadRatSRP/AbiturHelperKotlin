package com.madrat.abiturhelper.interfaces

import android.os.Bundle
import com.madrat.abiturhelper.model.Faculty
import com.madrat.abiturhelper.model.Specialty
import com.madrat.abiturhelper.model.Student

interface PickUpSpecialtiesMVP {
    interface View {
        fun grabSpecialties(path: String): ArrayList<Specialty>
        fun divideSpecialtiesByFaculty(list: ArrayList<Specialty>)
        fun grabStudents(path: String): ArrayList<Student>
        fun divideStudentsListByAdmissions(list: ArrayList<Student>)
        fun divideStudentsByScoreType()
        fun calculateAvailableFacultyPlaces(name: String, list: ArrayList<Specialty>)
        fun showFaculties(faculties: List<Faculty>)
        fun toSpecialties(bundle: Bundle)
        fun onItemClicked(faculty: Faculty, position: Int)
    }

}
